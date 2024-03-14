package projectp.kafkapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import projectp.kafkapp.model.ClientsModel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ClientsService clientsService;

  @Scheduled(cron = "${app.config.cronExpression}")
    public void scheduleTask() {
        clientsService.processClientsAndSendSMS();

    }
}

