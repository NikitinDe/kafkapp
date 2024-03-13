package projectp.kafkapp.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.service.ClientsService;
import projectp.kafkapp.service.Discount;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientsController {

    private final ClientsService clientService;
    private final Discount discountService;

    public ClientsController(ClientsService clientService, Discount discountService) {
        this.clientService = clientService;
        this.discountService = discountService;
    }


    @GetMapping("/getClient")
    @Operation(summary = "Getting information about all clients",
    description = "The service contacts the client service every hour and receives information about the clients in " +
            "the clientsinfo format whose birthday is in this month and the number ends at 7")
    public void  fetchClients() {

       clientService.fetchClientInfo();
    }


    @PostMapping("/{clientId}")
    @Operation(summary = "Getting information about a specific client by id",
    description = "Getting information about a specific client by id in the clientsinfo format")
    public ClientsModel fetchClientById(@PathVariable Long clientId) {
        return clientService.fetchClientById(clientId);
    }


    @PostMapping("/sendDiscounts")
    @Operation(summary = "Sending SMS and customer discount" ,
    description = " Sending a message to clients using kafka before 19.00 every day saving information about those clients to whom SMS has already been sent")
    public void sendDiscounts() {
        discountService.sendNotifications();
    }
}