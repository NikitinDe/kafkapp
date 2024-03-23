package projectp.kafkapp.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
public class ClientsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;



    @Column(name = "phone")
    private String  phone;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "message_send")
    private boolean messageSend;


}


