package projectp.kafkapp.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectp.kafkapp.model.ClientsModel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientsRepositoryTest {

    @Mock
    private ClientsRepository clientsRepository;

    @Test
    public void testFindByMessageSendFalse() {

        ClientsModel client1 = new ClientsModel(1L, "John Doe", "1234567890", LocalDate.now());
        ClientsModel client2 = new ClientsModel(2L, "Jane Doe", "09876543217", LocalDate.now());
        List<ClientsModel> expectedClients = Arrays.asList(client1, client2);
        when(clientsRepository.findByMessageSendFalse()).thenReturn(expectedClients);


        List<ClientsModel> actualClients = clientsRepository.findByMessageSendFalse();

        assertEquals(expectedClients, actualClients);
    }
    }