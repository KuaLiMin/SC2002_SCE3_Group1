package foms.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private Boolean isTakeAway;
    private HashMap<MenuItem, Integer> items;
    private String status;
    private Customer customer; 
    // Constructor
    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new HashMap<MenuItem, Integer>();
        this.status = "Pending"; // Default status
    }

    public String getOrderId() {
        return orderId;
    }

    public void setIsTakeAway(Boolean isTakeAway) {
        this.isTakeAway = isTakeAway;
    }

    public Boolean getIsTakeAway() {
        return isTakeAway;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public HashMap<MenuItem, Integer> getItems() {
        return items;
    }

    public void setItems( HashMap<MenuItem, Integer> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}