package org.cqrs.dto;

/**
 * @author Mukesh Verma
 */
public class InventoryAvailabilityInboundDto {
    private Long quantity;
    private String itemNumber;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }






}
