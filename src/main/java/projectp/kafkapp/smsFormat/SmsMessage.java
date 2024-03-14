package projectp.kafkapp.smsFormat;

import lombok.Data;

@Data
public class SmsMessage {
    private String phone;
    private String message;


    public SmsMessage(String phone,Object format) {

    }
}