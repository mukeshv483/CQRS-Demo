package  org.cqrs.controller;
import org.cqrs.dto.InventoryDto;
import org.cqrs.dto.OrderDto;
import org.cqrs.model.InventoryEntity;
import org.cqrs.model.OrderEntity;
import org.cqrs.service.InventoryService;
import org.cqrs.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;
     @Autowired
    InventoryService inventoryService;

    @PostMapping("/orders")
    public Mono<OrderEntity> createOrder(@RequestBody OrderDto order) {
        return orderService.createOrder(order);
    }
    @PostMapping("update/inventory")
    public Mono<InventoryEntity> updateInventory(@RequestBody InventoryDto inventoryDto) {
        return inventoryService.updateInventory(inventoryDto);
    }

}
