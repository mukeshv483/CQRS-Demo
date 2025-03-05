package org.cqrs.repository;

/**
 * @author Mukesh Verma
 */

import org.cqrs.model.InventoryAvailability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAvailabilityRepo extends MongoRepository<InventoryAvailability, String> {
    InventoryAvailability findByItemNumber(String itemNumber);
}