package projectp.kafkapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data

public class ClientsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "message_send", nullable = false)
    private boolean messageSend;

    public ClientsModel(long l, String johnDoe, String number, LocalDate now) {
        this.id = l;
        this.fullName = johnDoe;
        this.phone = number;
        this.birthday = now;
        this.messageSend = false;
    }

    public ClientsModel() {

    }
}


