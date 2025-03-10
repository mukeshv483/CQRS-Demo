package org.cqrs.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.dto.OrderDto;
import org.cqrs.event.UpdateAvailabilityEvent;
import org.cqrs.exception.InventoryNotAvailableException;
import org.cqrs.model.InventoryEntity;
import org.cqrs.model.OrderEntity;
import org.cqrs.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private  String outboundTopic = "inventory-outbound-updates";
    private final OrderRepository orderRepository;

    private final  KafkaTemplate<String, UpdateAvailabilityEvent> kafkaTemplate;
    private final InventoryService inventoryService;

    public Mono<OrderEntity> createOrder(OrderDto order)  {
        if(inventoryService.checkInventoryAvailability(order.getItemNumber(),order.getQuantity())){
          InventoryEntity entity= inventoryService.getInventory(order.getItemNumber());
            entity.setQuantity(entity.getQuantity()-order.getQuantity());
            inventoryService.save(entity);
            UpdateAvailabilityEvent updateAvailabilityEvent=UpdateAvailabilityEvent.builder()
                    .status(entity.getStatus())
                    .itemNumber(entity.getItemNumber())
                    .id(UUID.randomUUID().toString())
                    .quantity(entity.getQuantity()).build();

            return Mono.just(saveTODB(order))
                    .map(savedOrder ->{
                        kafkaTemplate.send(outboundTopic,String.valueOf(order.getItemNumber()), updateAvailabilityEvent);
                    return savedOrder;
                    }).doOnSuccess(publishOrder->{
                        log.info("publish order inventory successfully update {}",updateAvailabilityEvent);
                    });

        }

        throw new InventoryNotAvailableException("inventory not available");
    }

    private OrderEntity saveTODB(OrderDto order){
        OrderEntity entity=OrderEntity.builder().orderId(order.getOrderId())
                .itemNumber(order.getOrderNumber())
                .quantity(order.getQuantity())
                .itemNumber(order.getItemNumber())
                .product(order.getProduct()).build();
        return  orderRepository.save(entity);
    }
}


//orders-topic