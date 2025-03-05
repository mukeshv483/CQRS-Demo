package org.cqrs.consumer;

/**
 * @author Mukesh Verma
 */
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.cqrs.dto.InventoryAvailabilityInboundDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, InventoryAvailabilityInboundDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        JsonDeserializer deserializer = new JsonDeserializer(InventoryAvailabilityInboundDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // Disable auto commit for manual batch commit
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
       // props.put("spring.json.trusted.packages", "*");// Number of records in a batch
        ErrorHandlingDeserializer<InventoryAvailabilityInboundDto> errorHandlingDeserializer
                = new ErrorHandlingDeserializer<>(deserializer);
        ErrorHandlingDeserializer<String> errorHandlingDeserializerKey
                = new ErrorHandlingDeserializer<>(new StringDeserializer());
        return new DefaultKafkaConsumerFactory<>(props, errorHandlingDeserializerKey,
                errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryAvailabilityInboundDto> kafkaBatchListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryAvailabilityInboundDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true); // Enable batch processing
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE); // Manual batch ack/
        return factory;
    }
}

