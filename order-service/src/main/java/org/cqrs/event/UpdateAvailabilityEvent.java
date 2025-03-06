package org.cqrs.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@Data
@Builder
@ToString
public class UpdateAvailabilityEvent {

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String status;
    private String itemNumber;
    private Long quantity;


    public void setStatus(String status) {
        this.status = status;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }





}
