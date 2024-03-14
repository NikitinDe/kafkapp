package projectp.kafkapp.configurator;


import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import projectp.kafkapp.smsFormat.SmsMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientsConfig {

    @Value("${spring.bootstrap}")
    private String bootstrapServers;


    @Value("${app.config.topic-name}")
    private String topicName;

    @Value("${app.config.partitions-num}")
    private int partitionsNum;

    @Value("${app.config.replication-factor}")
    private short replicationFactor;


    @Bean
    public KafkaTemplate<String, SmsMessage> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic messageSMSTopic() {
        return new NewTopic(topicName, partitionsNum, replicationFactor);
    }

    @Bean
    public ProducerFactory<String, SmsMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }


}