package org.cqrs.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * @author Mukesh Verma
 */
@Document(collection = "inventory_availability")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryAvailability {
    @Id
    private String itemNumber;
    private Long quantity;
    private String status;


}
