package projectp.kafkapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsInfo {

    private Long clientId;
    private String name;
    private String middleName;
    private String surname;
    private int age;
    private LocalDate birthday;
    private String phone;



}