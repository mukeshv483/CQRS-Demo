package org.cqrs.service;


import lombok.RequiredArgsConstructor;
import org.cqrs.model.InventoryAvailability;
import org.cqrs.repository.InventoryAvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final InventoryAvailabilityRepo inventoryAvailabilityRepo;

    public List<InventoryAvailability> getAllOrders() {
        return inventoryAvailabilityRepo.findAll();
    }
    public InventoryAvailability getOrders(String orderId) {
        return inventoryAvailabilityRepo.findByItemNumber(orderId);
    }

}
