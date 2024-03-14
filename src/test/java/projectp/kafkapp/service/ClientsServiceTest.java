package projectp.kafkapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.kafka.core.KafkaTemplate;
import projectp.kafkapp.Config.AppConfigProperties;
import projectp.kafkapp.httpClient.FeignClients;
import projectp.kafkapp.mapper.MapperConfig;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;
import projectp.kafkapp.smsFormat.SmsMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
    public class ClientsServiceTest {

}