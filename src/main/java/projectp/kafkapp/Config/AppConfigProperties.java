package projectp.kafkapp.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.config")
public class AppConfigProperties {

    private Integer   discount;
    private String cronExpression;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime sendTime;
}
