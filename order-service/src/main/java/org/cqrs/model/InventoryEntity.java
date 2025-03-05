package org.cqrs.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * @author Mukesh Verma
 */
@Entity
@Table(name = "inventory_tbl")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class InventoryEntity {
    @Id
    @Column(name = "item_number", nullable = false, unique = true)
    private String itemNumber;
    private Long quantity;
    private String status;

}
