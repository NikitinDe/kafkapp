package projectp.kafkapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@EnableKafka
@EnableWebMvc
@EnableFeignClients
public class KafkappApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkappApplication.class, args);
    }

}
