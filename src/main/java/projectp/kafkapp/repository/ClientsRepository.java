package projectp.kafkapp.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsModel, Long> {
    @Query("SELECT c FROM ClientsModel c WHERE c.messageSend = false")
    List<ClientsModel> findClientsWithMessageSendFalse();



}