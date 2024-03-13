package projectp.kafkapp.configurator;



import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.repository.ClientsRepository;
import io.swagger.v3.oas.models.OpenAPI;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.awt.SystemColor.info;

@Configuration

@EnableScheduling


public class ClientsConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ClientsRepository clientsRepository() {
        return new ClientsRepository() {
            @Override
            public List<ClientsModel> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<ClientsModel> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends ClientsModel> S save(S entity) {
                return null;
            }

            @Override
            public <S extends ClientsModel> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<ClientsModel> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public List<ClientsModel> findAll() {
                return null;
            }

            @Override
            public List<ClientsModel> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(ClientsModel entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends ClientsModel> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends ClientsModel> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends ClientsModel> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<ClientsModel> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public ClientsModel getOne(Long aLong) {
                return null;
            }

            @Override
            public ClientsModel getById(Long aLong) {
                return null;
            }

            @Override
            public ClientsModel getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends ClientsModel> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends ClientsModel> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends ClientsModel> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends ClientsModel> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends ClientsModel> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends ClientsModel> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends ClientsModel, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }

            @Override
            public List<ClientsModel> findByMessageSendFalse() {
                return null;
            }
        };
    }

    @Bean
    public OpenAPI api() {
        return new OpenAPI().servers(List.of(new Server().url("http://localhost:8081"))).
                info(new Info().title("Service Adapter\n" +
                        "\n" +
                        "The service receives customer information from the Clients service on a scheduled basis.\n" )
                );


    }
}