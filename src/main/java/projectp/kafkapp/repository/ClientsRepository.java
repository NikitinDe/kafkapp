package projectp.kafkapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectp.kafkapp.model.ClientsModel;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsModel, Long> {
    List<ClientsModel> findByMessageSendFalse();
}