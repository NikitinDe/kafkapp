package projectp.kafkapp.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import projectp.kafkapp.model.ClientsModel;

import java.util.List;

@Service

public class Scheduler {

    private final ClientsService clientsService;
    private final Discount smsService;

    public Scheduler(ClientsService clientsService, Discount smsService) {
        this.clientsService = clientsService;
        this.smsService = smsService;
    }

    @Scheduled(cron = "${scheduler.cron}") //В данном случае запускаем метод scheduleTask который запускает метод
                                     // сервиса и
                                    // получаем клиентов которые по условию и далее отправляем смс
    public void scheduleTask() {
        clientsService.fetchClientInfo();
        smsService.sendNotifications();
    }
}

