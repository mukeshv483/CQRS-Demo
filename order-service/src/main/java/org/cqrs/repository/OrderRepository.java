package org.cqrs.repository;

/**
 * @author Mukesh Verma
 */

import org.cqrs.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}