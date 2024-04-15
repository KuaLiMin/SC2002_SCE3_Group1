package foms.models;

import foms.enums.OrderStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;

/**
 * Represents an order within the Food Order Management System.
 * The class encapsulates details such as order ID, status, associated items, and costs.
 * It also tracks the order's progress through timestamps for pickup and collection.
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */

public class Order implements Serializable {
    /**
     * The unique identifier for the order.
     */
    private String orderId;
    
    /**
     * Flag indicating whether the order is for take away.
     */
    private Boolean isTakeAway;
    
    /**
     * A list of menu items with their corresponding quantities.
     */
    private ArrayList<HashMap<MenuItem, Integer>> items;
    
    /**
     * The current status of the order.
     */ 
    private OrderStatus status;
    
    /**
     * The total cost of the order.
     */
    private double total;
    
    /**
     * The time at which the order will be ready for pickup.
     */
    private LocalDateTime readyForPickupTime;
    
    /**
     * The time at which the order was collected.
     */
    private LocalDateTime collectedTime;
    
    /**
     * The branch where the order was placed.
     */
    private String branch;
    
    /**
     * Any special requests associated with the order.
     */
    private String request;
    
    /**
     * The maximum allowed quantity for any single menu item in an order.
     */
    public static final int MAX_QUANTITY_OF_MENUITEM = 100;
    
    /**
     * Constructs a new Order with the specified order ID and branch name.
     * Initializes the order with default values for other properties.
     *
     * @param orderId    the unique identifier for the order
     * @param branchname the name of the branch from which the order is placed
     */
    public Order(String orderId, String branchname) {
        this.orderId = orderId;
        this.isTakeAway = false;
        this.items = new ArrayList<HashMap<MenuItem, Integer>>();
        this.total = 0;
        this.branch = branchname;
        this.request = "";
    }
    
    /**
     * Sets a special request for the order.
     *
     * @param request the special request to be associated with this order
     */
    public void setRequest(String request){
        this.request = request;
    }

    /**
     * Retrieves the special request associated with this order.
     *
     * @return the special request of the order
     */
    public String getRequest(){
        return this.request;
    }

    /**
     * Retrieves the order id associated with this order.
     *
     * @return the order id of the order
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Retrieves the branch name associated with this order.
     *
     * @return the branch name where the order was placed
     */
    public String getBranch(){
        return branch;

    }

    /**
     * Sets a branch for the order.
     *
     * @param branch the branch to be associated with this order
     */
    public void setBranch(String branch){
        this.branch = branch;
    }

    /**
     * Sets the take away status for the order.
     *
     * @param isTakeAway the takeaway status to set for this order; true if the order is to be taken away, false otherwise.
     */
    public void setIsTakeAway(Boolean isTakeAway) {
        this.isTakeAway = isTakeAway;
    }

    /**
     * Retrieves the take away status of the order.
     *
     * @return a Boolean indicating the takeaway status; true if the order is for take away, false if for dine-in.
     */
    public Boolean getIsTakeAway() {
        return isTakeAway;
    }
    /**
     * Sets the unique order ID for this order.
     *
     * @param orderId the unique identifier to be assigned to this order
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    /**
     * Retrieves the list of items and their quantities in this order.
     * Each item in the list is a map entry where the key is a MenuItem and the value is the quantity ordered.
     *
     * @return an ArrayList of HashMap entries, each representing a MenuItem and its quantity
     */
    public ArrayList<HashMap<MenuItem, Integer>> getItems() {
        return items;
    }
        
    /**
     * Sets the list of items and their quantities for this order.
     * The provided list should contain map entries where the key is a MenuItem and the value is the quantity ordered.
     *
     * @param items the list of items and quantities to set for this order
     */
    public void setItems( ArrayList<HashMap<MenuItem, Integer>> items) {
        this.items = items;
    }

    /**
     * Retrieves the current status of this order.
     *
     * @return the status of the order as an instance of the OrderStatus enum
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of this order.
     *
     * @param status the new status to be assigned to this order, as an instance of the OrderStatus enum
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }    

    /**
     * Retrieves the total cost of this order.
     *
     * @return the total cost as a double
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost for this order.
     *
     * @param total the total cost to be set for this order
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Retrieves the time when the order is ready for pickup.
     *
     * @return the LocalDateTime representing the time when the order will be ready for pickup
     */
    public LocalDateTime getReadyForPickupTime() {
        return readyForPickupTime;
    }

    /**
     * Sets the time when the order should be ready for pickup.
     *
     * @param readyForPickupTime the LocalDateTime representing the time to set for when the order will be ready for pickup
     */
    public void setReadyForPickupTime(LocalDateTime readyForPickupTime) {
        this.readyForPickupTime = readyForPickupTime;
    }

    /**
     * Sets the time when the order was collected by the customer.
     *
     * @param collectedTime the LocalDateTime representing the time when the order was picked up by the customer
     */
    public void setCollectedTime(LocalDateTime collectedTime) {
        this.collectedTime = collectedTime;
    }

    /**
     * Retrieves the time when the order was collected by the customer.
     *
     * @return the LocalDateTime representing the time when the order was collected
     */
    public LocalDateTime getCollectedTime() {
        return collectedTime;
    }
}