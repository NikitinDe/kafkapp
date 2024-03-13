package projectp.kafkapp.service;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class Discount {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ClientsRepository clientRepository;

     @Value("${discount}")
    private String discount;



    public Discount(RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate, ClientsRepository clientRepository) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.clientRepository = clientRepository;
    }
    @Value("${kafka.topics.sms}")
    private String smsTopic;


    public void sendNotifications() {
        List<ClientsModel> clients = clientRepository.findByMessageSendFalse();

        for (ClientsModel client : clients) {
            if (LocalTime.now(ZoneId.of("Europe/Moscow")).isBefore(LocalTime.of(19, 0))) {
                String message = String.format("%s, в этом месяце для вас действует скидка %d", client.getFullName(), discount);
                kafkaTemplate.send(smsTopic, message);
                client.setMessageSend(true);
            }
        }


        clientRepository.saveAll(clients);
    }
}