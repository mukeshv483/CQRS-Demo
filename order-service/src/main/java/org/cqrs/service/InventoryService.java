package org.cqrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.dto.InventoryDto;
import org.cqrs.event.UpdateAvailabilityEvent;
import org.cqrs.model.InventoryEntity;
import org.cqrs.model.OrderEntity;
import org.cqrs.repository.InventoryRepo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Mukesh Verma
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {
    private  String outboundTopic = "inventory-outbound-updates";
    private final InventoryRepo inventoryRepo;
    private final KafkaTemplate<String, UpdateAvailabilityEvent> kafkaTemplate;
    public InventoryEntity getInventory(String itemNumber){
          return  inventoryRepo.findByItemNumber(itemNumber);
    }

    public boolean checkInventoryAvailability(String itemNumber, long reqQuantity){

        InventoryEntity inventory=getInventory(itemNumber);
        return inventory.getQuantity() > 0 && inventory.getQuantity() > reqQuantity;

    }


    public void save(InventoryEntity entity) {
        inventoryRepo.save(entity);
    }


    public Mono<InventoryEntity> updateInventory(InventoryDto inventoryDto) {
        InventoryEntity entity=InventoryEntity.builder()
                .status(inventoryDto.getStatus())
                .itemNumber(inventoryDto.getItemNumber())
                .quantity(inventoryDto.getQuantity()).build();
        UpdateAvailabilityEvent updateAvailabilityEvent=UpdateAvailabilityEvent.builder()
                .status(entity.getStatus())
                .itemNumber(entity.getItemNumber())
                .id(UUID.randomUUID().toString())
                .quantity(entity.getQuantity()).build();
       return Mono.just(inventoryRepo.save(entity))
               .map(dbEntity-> {
                   log.info("publishing inventory update {}", updateAvailabilityEvent);
                   kafkaTemplate.send(outboundTopic, String.valueOf(inventoryDto.getItemNumber()), updateAvailabilityEvent);
                  return dbEntity;
               } );
    }
}
