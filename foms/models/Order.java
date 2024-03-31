package foms.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private Boolean isTakeAway;
    private ArrayList<HashMap<MenuItem, Integer>> items;
    private String status;
    private double total;
    
    public Order(String orderId) {
        this.orderId = orderId;
        this.total = 0;
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

    public ArrayList<HashMap<MenuItem, Integer>> getItems() {
        return items;
    }

    public void setItems( ArrayList<HashMap<MenuItem, Integer>> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}