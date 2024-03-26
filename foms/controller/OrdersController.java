package foms.controller;

import foms.fileio.FileIO;
import foms.models.Order;

import java.util.ArrayList;


public class OrdersController {
    // private static final String ORDERS_FILE_PATH = "foms/originalfiles/orders_list.csv";
    private static final ArrayList<Order> orderList = new ArrayList<>();

    public void setOrderReadyToPickup(String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus("Ready to pickup");
                break;
            }
        }
        // FileIO.saveOrderList(orderList);
    }

    public void setOrderCompleted(String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus("Completed");
                break;
            }
        }
        // FileIO.saveOrderList(orderList); 
    }

    // Method to get the status of an order using the order ID
    public String getOrderStatus(String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order.getStatus();
            }
        }
        return "Order ID not found"; // Return an error message if the order ID is not found
    }

}
