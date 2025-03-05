package org.cqrs.repository;

/**
 * @author Mukesh Verma
 */

import org.cqrs.model.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryEntity, Long> {
    InventoryEntity findByItemNumber(String itemNumber);
}