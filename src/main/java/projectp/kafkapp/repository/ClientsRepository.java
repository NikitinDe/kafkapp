package projectp.kafkapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectp.kafkapp.model.ClientsModel;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsModel, Long> {
    @Query("SELECT client FROM ClientsModel client WHERE client.messageSend = false")
    List<ClientsModel> findClientsWithMessageSendFalse();



}