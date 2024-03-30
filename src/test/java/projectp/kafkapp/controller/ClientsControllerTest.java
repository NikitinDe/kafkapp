package projectp.kafkapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.service.ClientsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ClientsControllerTest {

    @Mock
    private ClientsService clientsService;
    @InjectMocks
    private ClientsController clientsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessClientsAndSendSMS() {


        clientsController.processClientsAndSendSMS();


    }

    @Test
    public void testFetchClientById() {

        Long clientId = 1L;
        ClientsModel expectedClient = new ClientsModel();

        expectedClient.setId(clientId);


        when(clientsService.fetchClientById(clientId)).thenReturn(expectedClient);

        ClientsModel actualClient = clientsController.fetchClientById(clientId);


        assertEquals(expectedClient, actualClient);

    }
}