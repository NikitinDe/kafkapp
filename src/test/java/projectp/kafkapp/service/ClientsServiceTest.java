package projectp.kafkapp.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import projectp.kafkapp.httpClient.FeignClients;
import projectp.kafkapp.mapper.MapperConfig;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;
import projectp.kafkapp.smsFormat.SmsMessage;

import java.time.LocalDate;

import java.time.LocalTime;

import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientsServiceTest {
    @Mock
    private FeignClients feignClients;

    @Mock
    private ClientsRepository clientsRepository;

    @Mock
    private KafkaTemplate<String, SmsMessage> kafkaTemplate;
    @InjectMocks
    private ClientsService clientsService;


    @Mock
    private MapperConfig mapperConfig;

    @Value("${app.config.discount}")
    private Integer discount;

    @Value("${app.config.zoneId}")
    private String zoneIdString;
    @Value("${app.config.sendTime}")
    private LocalTime sendTime;

    @BeforeEach
    public void setup() {

        discount = 10;
        zoneIdString  = "Europe/Moscow";

        sendTime = LocalTime.of(18, 50, 3);


    }



    @Test
    public void testProcessClientsAndSendSMS() {

        LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        ClientsInfo client1 = new ClientsInfo(1L,"Иван", "Иванов","Иванович",26, LocalDate.of(1990, Month.DECEMBER, 15), "89111234567");
        ClientsInfo client2 = new ClientsInfo(2L,"Петр", "Петров","Петрович", 24,LocalDate.of(1995, Month.DECEMBER, 20), "89117654321");
        List<ClientsInfo> clients = Arrays.asList(client1, client2);

        when(feignClients.getAllClients()).thenReturn(clients);

        ClientsModel model1 = new ClientsModel(1L,"Иванов Иван Иванович", "89111234567",LocalDate.of(1990, Month.DECEMBER, 15),false);
        ClientsModel model2 = new ClientsModel(2L, "Петров Петр Петрович", "89117654321",LocalDate.of(1997, Month.DECEMBER, 16),true);
        when(mapperConfig.toModel(client1)).thenReturn(model1);
        when(mapperConfig.toModel(client2)).thenReturn(model2);

        when(clientsRepository.findClientsWithMessageSendFalse()).thenReturn(Arrays.asList(model1, model2));

        SmsMessage smsMessage1 = new SmsMessage("89111234567", "Текст сообщения для Иван Иванов");
        SmsMessage smsMessage2 = new SmsMessage("89117654321", "Текст сообщения для Петр Петров");
        when(mapperConfig.toSmsMessage(any(), anyInt())).thenReturn(smsMessage1);
        when(mapperConfig.toSmsMessage(any(), anyInt())).thenReturn(smsMessage2);

        // Act
        clientsService.processClientsAndSendSMS();

        // Assert
        verify(feignClients, times(1)).getAllClients();
        verify(clientsRepository, times(1)).saveAll(Arrays.asList(model1, model2)); // Verify the expected list of ClientsModel objects
        verify(kafkaTemplate, times(2)).send("messageSMS", any(SmsMessage.class));
        verify(clientsRepository, times(2)).save(any(ClientsModel.class));
    }

}