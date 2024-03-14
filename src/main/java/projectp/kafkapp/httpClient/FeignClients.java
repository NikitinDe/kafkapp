package projectp.kafkapp.httpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;

import java.util.List;

@FeignClient(name = "Clients", url = "${clients.url}")
public interface FeignClients {

    @PostMapping("api/v1/getClient")
    List<ClientsInfo> getAllClients();

    @PostMapping("api/v1/getClient/{clientId}")
    ClientsModel fetchClientById(@PathVariable Long clientId);


}
