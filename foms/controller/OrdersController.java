package foms.controller;

import foms.fileio.FileIO;
import foms.models.Order;
import foms.view.MakeOrderMenu;
import foms.models.Branch;
import foms.models.Payment;
import foms.tools.ScannerCheck;

import java.util.ArrayList;


public class OrdersController {
    // private static final String ORDERS_FILE_PATH = "foms/originalfiles/orders_list.csv";
    private static final ArrayList<Order> orderList = new ArrayList<>();
    private static ArrayList<Payment> paymentList = new ArrayList<>();
    private static ArrayList<Branch> branchList = new ArrayList<>();

    public void setOrderReadyToPickup(String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus("Ready to pickup");
                System.out.println(orderId + " is ready to pickup!");
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

    
    public ArrayList<Order> getAllOrders() {
        return orderList;
    }

    // Method to get the status of an order using the order ID
    public String getOrderStatus(String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order.getStatus();
            }
        }
        return "Order ID not found"; 
    }

    public void viewOrderDetails(String orderID) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                System.out.println(order); //what details to display?
                return;
            }
        }
        System.out.println("Order ID not found.");
    }

    public static void makeOrder() {
        MakeOrderMenu.displayBranchList(branchList);
        
    }

    
}
