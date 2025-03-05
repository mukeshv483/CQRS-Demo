package  org.cqrs.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderEntity {
    @Id
    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;
    private String product;
    private Long quantity;
    private String itemNumber;

}
