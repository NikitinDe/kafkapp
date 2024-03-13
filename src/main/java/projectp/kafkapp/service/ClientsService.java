package projectp.kafkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientsService {

    private final RestTemplate restTemplate;
    private final ClientsRepository clientRepository;

    @Autowired

    public ClientsService(RestTemplate restTemplate, ClientsRepository clientRepository) {
        this.restTemplate = restTemplate;
        this.clientRepository = clientRepository;
    }

    @Value("${rest.clients.url}")
    private String clientsUrl;


    public void fetchClientInfo() {

        ClientsModel[] clients = restTemplate.getForObject(clientsUrl, ClientsModel[].class);

        List<ClientsModel> filteredClients = Optional.ofNullable(clients)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .filter(Objects::nonNull)
                .filter(client -> Objects.nonNull(client.getPhone()) && client.getPhone().endsWith("7"))
                .filter(client -> Objects.nonNull(client.getBirthday()) && client.getBirthday().getMonth() == LocalDate.now().getMonth())
                .collect(Collectors.toList());

        clientRepository.saveAll(filteredClients);

    }

    public ClientsModel fetchClientById(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Client with ID " + clientId + " not found"));
    }
}

