package org.cqrs.consumer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.dto.InventoryAvailabilityInboundDto;
import org.cqrs.model.InventoryAvailability;
import org.cqrs.repository.InventoryAvailabilityRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundEventListner {

    private final InventoryAvailabilityRepo inventoryAvailabilityRepo;

    @KafkaListener(topics = "inventory-outbound-updates", groupId = "inventory-outbound-updates-group",containerFactory = "kafkaBatchListenerContainerFactory")
    public void consume(List<InventoryAvailabilityInboundDto> inputMessage, Acknowledgment acknowledgment) {
       log.info("update recived : "+inputMessage);
        Flux.fromIterable(inputMessage)
                .map(this::mapToInventoryDto)
                .flatMap(inventory -> {
                    inventoryAvailabilityRepo.save(inventory);
                    return Mono.just("success");
                })
                .blockLast();

        acknowledgment.acknowledge();
    }

    private InventoryAvailability mapToInventoryDto(InventoryAvailabilityInboundDto msg) {
        return InventoryAvailability.builder()
                .itemNumber(msg.getItemNumber())
                .quantity(msg.getQuantity())
                .status(msg.getStatus()).build();

    }
}
