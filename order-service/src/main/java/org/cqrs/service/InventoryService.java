package org.cqrs.service;

import lombok.RequiredArgsConstructor;
import org.cqrs.dto.InventoryDto;
import org.cqrs.model.InventoryEntity;
import org.cqrs.model.OrderEntity;
import org.cqrs.repository.InventoryRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Mukesh Verma
 */
@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;
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
        return Mono.just(inventoryRepo.save(entity));
    }
}
