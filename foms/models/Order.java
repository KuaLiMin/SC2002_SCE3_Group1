package foms.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
// import java.util.List;

import foms.enums.OrderStatus;

public class Order implements Serializable {
    private String orderId;
    private Boolean isTakeAway;
    private ArrayList<HashMap<MenuItem, Integer>> items;
    private OrderStatus status;
    private double total;
    private LocalDateTime readyForPickupTime;
    private LocalDateTime collectedTime;
    private String branch;
    public static final int MAX_QUANTITY_OF_MENUITEM = 100;
    
    public Order(String orderId, String branchname) {
        this.orderId = orderId;
        this.isTakeAway = false;
        this.items = new ArrayList<HashMap<MenuItem, Integer>>();
        this.total = 0;
        this.branch = branchname;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBranch(){
        return branch;
    }

    public void setBranch(String branch){
        this.branch = branch;
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

    public ArrayList<HashMap<MenuItem, Integer>> getItems() {
        return items;
    }

    public void setItems( ArrayList<HashMap<MenuItem, Integer>> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }    

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getReadyForPickupTime() {
        return readyForPickupTime;
    }

    public void setReadyForPickupTime(LocalDateTime readyForPickupTime) {
        this.readyForPickupTime = readyForPickupTime;
    }

    public void setCollectedTime(LocalDateTime collectedTime) {
        this.collectedTime = collectedTime;
    }

    public LocalDateTime getCollectedTime() {
        return collectedTime;
    }
}