package projectp.kafkapp.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.service.ClientsService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClientsController {

    private final ClientsService clientsService;


    @PostMapping("/get_Client")
    @Operation(summary = "Getting information about all clients",
            description = "The service contacts the client service every hour and receives information about the clients in " +
                    "the clientsinfo format whose birthday is in this month and the number ends at 7")
    public void processClientsAndSendSMS() {
        clientsService.processClientsAndSendSMS();

    }

    @PostMapping("/get_Client/{clientId}")
    @Operation(summary = "Getting information about a specific client by id",
            description = "Getting information about a specific client by id in the clientsinfo format")
    public ClientsModel fetchClientById(@PathVariable Long clientId) {
        return clientsService.fetchClientById(clientId);
    }
}


