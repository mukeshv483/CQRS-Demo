package org.cqrs.exception;

/**
 * @author Mukesh Verma
 */
public class InventoryNotAvailableException extends RuntimeException {

    public InventoryNotAvailableException(String message){
        super(message);
    }
}
