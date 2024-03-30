package projectp.kafkapp.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import projectp.kafkapp.httpClient.FeignClients;
import projectp.kafkapp.mapper.MapperConfig;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;
import projectp.kafkapp.smsFormat.SmsMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ClientsService {

    private final FeignClients feignClients;
    private final ClientsRepository clientsRepository;
    private final KafkaTemplate<String, SmsMessage> kafkaTemplate;
    private final MapperConfig mapperConfig;

    @Value("${app.config.discount}")
    private   Integer discount;

    @Value("${app.config.sendTime}")
    private   LocalTime sendTime;

    @Value("${app.config.zoneId}")
    private String zoneIdString;




    public void processClientsAndSendSMS() {

        LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        List<ClientsInfo> clients = feignClients.getAllClients();
        clientsRepository.saveAll(clients.stream()
                .filter(client -> client.getBirthday() != null && client.getBirthday().getMonth().equals(currentDate.getMonth()))
                .filter(client -> client.getPhone() != null && client.getPhone().endsWith("7"))
                .map(mapperConfig::toModel)
                .collect(Collectors.toList()));
        // Сохраняем клиентов в базе данных


        LocalDateTime endOfDay = LocalDate.now(ZoneId.of("Europe/Moscow")).
                atTime(19,00,0);

        if (LocalDateTime.now(ZoneId.of("Europe/Moscow")).isBefore(endOfDay)) {
            List<ClientsModel> clientsToSendSMS = clientsRepository.findClientsWithMessageSendFalse();
            for (ClientsModel client : clientsToSendSMS) {
                SmsMessage smsMessage = mapperConfig.toSmsMessage(client, discount);
                kafkaTemplate.send("messageSMS", smsMessage);
                client.setMessageSend(true);
                clientsRepository.save(client);
            }
        }


    }

    public ClientsModel fetchClientById(Long clientId) {
        LocalDate currentDate = LocalDate.now(ZoneId.of(zoneIdString));
        List<ClientsInfo> clients = feignClients.getAllClients();

        // Сохраняем клиентов в базе данных, если их день рождения в этом месяце и оканчивается на 7
        clientsRepository.saveAll(clients.stream()
                .filter(client -> client.getBirthday().getMonth().equals(currentDate.getMonth()) && client.getPhone().endsWith("7"))
                .map(mapperConfig::toModel)
                .collect(Collectors.toList()));

        // Отправляем SMS сообщения, если время отправки пришло

        LocalDateTime endOfDay = LocalDate.now(ZoneId.of(zoneIdString)).atTime(sendTime);

        if (LocalDateTime.now(ZoneId.of(zoneIdString)).isBefore(endOfDay)) {
            List<ClientsModel> clientsToSendSMS = clientsRepository.findClientsWithMessageSendFalse();
            for (ClientsModel client : clientsToSendSMS) {
                SmsMessage smsMessage = mapperConfig.toSmsMessage(client, discount);
                kafkaTemplate.send("messageSMS", smsMessage);
                client.setMessageSend(true);
                clientsRepository.save(client);
            }
        }

        // Возвращаем клиента по идентификатору из базы данных
        return clientsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
    }

}

