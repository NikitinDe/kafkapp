package projectp.kafkapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import projectp.kafkapp.Config.AppConfigProperties;
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
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ClientsService {

    private final FeignClients feignClients;
    private final ClientsRepository clientsRepository;
    private final KafkaTemplate<String, SmsMessage> kafkaTemplate;
    private final MapperConfig mapperConfig;
    private final AppConfigProperties appConfigProperties;


    public void processClientsAndSendSMS() {
        Integer discount = appConfigProperties.getDiscount();
        LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        List<ClientsInfo> clients = feignClients.getAllClients();

        clientsRepository.saveAll(clients.stream()
                .filter(c -> c.getBirthday().getMonth()
                        .equals(currentDate.getMonth()) && c.getPhone().endsWith("7"))
                .map(mapperConfig::toModel)
                .collect(Collectors.toList()));

        // Сохраняем клиентов в базе данных
        LocalTime sendTime = appConfigProperties.getSendTime();

        LocalDateTime endOfDay = LocalDate.now(ZoneId.of("Europe/Moscow")).
                atTime(sendTime);

        if (LocalDateTime.now(ZoneId.of("Europe/Moscow")).isAfter(endOfDay)) {
            List<ClientsModel> clientsToSendSMS = clientsRepository.findAll().stream()
                    .filter(client -> !client.isMessageSend())
                    .toList();

            for (ClientsModel client : clientsToSendSMS) {
                SmsMessage smsMessage = mapperConfig.toSmsMessage(client, discount);
                kafkaTemplate.send("messageSMS", smsMessage);
                client.setMessageSend(true);
                clientsRepository.save(client);
            }
        }
    }

    public ClientsModel fetchClientById(Long clientId) {
        Integer discount = appConfigProperties.getDiscount();
        LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/Moscow"));
        List<ClientsInfo> clients = feignClients.getAllClients();

        // Сохраняем клиентов в базе данных, если их день рождения в этом месяце и оканчивается на 7
        clientsRepository.saveAll(clients.stream()
                .filter(c -> c.getBirthday().getMonth().equals(currentDate.getMonth()) && c.getPhone().endsWith("7"))
                .map(mapperConfig::toModel)
                .collect(Collectors.toList()));

        // Отправляем SMS сообщения, если время отправки прошло
        LocalTime sendTime = appConfigProperties.getSendTime();
        LocalDateTime endOfDay = LocalDate.now(ZoneId.of("Europe/Moscow")).atTime(sendTime);

        if (LocalDateTime.now(ZoneId.of("Europe/Moscow")).isAfter(endOfDay)) {
            List<ClientsModel> clientsToSendSMS = clientsRepository.findAll().stream()
                    .filter(client -> !client.isMessageSend())
                    .collect(Collectors.toList()); // Используем collect вместо toList

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

