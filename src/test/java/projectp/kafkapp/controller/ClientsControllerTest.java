package projectp.kafkapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.service.ClientsService;
import projectp.kafkapp.service.Discount;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientsControllerTest {

    @Mock
    private ClientsService clientService;

    @Mock
    private Discount discountService;

    @InjectMocks
    private ClientsController clientsController;

    @Test
    public void testFetchClients() {
        // Act
        clientsController.fetchClients();

        // Assert
        verify(clientService, times(1)).fetchClientInfo();
    }

    @Test
    public void testFetchClientById() {
        // Arrange
        Long clientId = 1L;
        ClientsModel expectedClient = new ClientsModel(clientId, "John Doe", "1234567890", LocalDate.now());
        when(clientService.fetchClientById(clientId)).thenReturn(expectedClient);

        // Act
        ClientsModel result = clientsController.fetchClientById(clientId);

        // Assert
        assertEquals(expectedClient, result);
        verify(clientService, times(1)).fetchClientById(clientId);
    }

    @Test
    public void testSendDiscounts() {
        // Act
        clientsController.sendDiscounts();

        // Assert
        verify(discountService, times(1)).sendNotifications();
    }
}