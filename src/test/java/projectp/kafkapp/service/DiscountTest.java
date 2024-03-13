package projectp.kafkapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiscountTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ClientsRepository clientRepository;

    @InjectMocks
    private Discount discount;

    @Test
    public void testSendNotifications() {

        ClientsModel client1 = new ClientsModel(1L, "John Doe", "1234567890", LocalDate.now());
        ClientsModel client2 = new ClientsModel(2L, "Jane Doe", "09876543217", LocalDate.now());
        List<ClientsModel> clients = Arrays.asList(client1, client2);
        when(clientRepository.findByMessageSendFalse()).thenReturn(clients);


        discount.sendNotifications();


        for (ClientsModel client : clients) {
            if (LocalTime.now(ZoneId.of("Europe/Moscow")).isBefore(LocalTime.of(19, 0))) {
                verify(kafkaTemplate).send(anyString(), anyString());
                verify(clientRepository).saveAll(anyList());
            }
        }
    }
}