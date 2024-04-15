package foms.enums;

/**
 * Represents the various statuses that an order can have within the Food Order Management System (FOMS).
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public enum OrderStatus {    
    /**
    * Indicates that an order has been newly created and is in the initial stage of processing.
    */
    NEW,
    
    /**
     * Indicates that an order is ready to be picked up by the customer.
     */
    READY_TO_PICKUP,
        
    /**
     * Indicates that an order has been picked up by the customer and is considered complete.
     */
    COMPLETED,
        
    /**
     * Indicates that an order has been canceled and will not be processed or completed.
     */
    CANCELED,
        
    /**
     * Indicates that the order's status is unknown or has not been set to one of the other defined statuses.
     */
    UNKNOWN
}
