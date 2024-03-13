package projectp.kafkapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClientsRepository clientRepository;

    @InjectMocks
    private ClientsService clientsService;
    private String clientsUrl = "http://localhost:8081/api/v1/clients";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    // Arrange
    public void fetchClientInfo() {
        // Arrange
        ClientsModel[] clients = restTemplate.getForObject(clientsUrl, ClientsModel[].class);
        System.out.println("Fetched clients: " + Arrays.toString(clients));

        List<ClientsModel> filteredClients = Stream.ofNullable(clients)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(client -> Objects.nonNull(client.getPhone()) && client.getPhone().endsWith("7"))
                .filter(client -> Objects.nonNull(client.getBirthday()) && client.getBirthday().getMonth() == LocalDate.now().getMonth())
                .collect(Collectors.toList());
        System.out.println("Filtered clients: " + filteredClients);

        clientRepository.saveAll(filteredClients);
    }


}

