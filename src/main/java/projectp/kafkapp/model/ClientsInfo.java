package projectp.kafkapp.model;

import lombok.Data;

import java.time.LocalDate;


@Data
public class ClientsInfo {

    private Long clientId;
    private String name;
    private String middleName;
    private String surname;
    private Long age;
    private LocalDate birthday;
    private String phone;


}