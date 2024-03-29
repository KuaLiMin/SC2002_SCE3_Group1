package foms.controller;

import foms.fileio.FileIO;
import foms.models.Order;
import foms.view.MakeOrderMenu;
import foms.models.Branch;
import foms.models.Payment;
import foms.tools.ScannerCheck;
import foms.models.Customer;
import foms.models.MenuItem;

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

    public static boolean makeNewOrder(Customer customer) {
        Branch branchSelected = MakeOrderMenu.selectBranch(branchList);
        Order newOrder = new Order(MakeOrderMenu.createOrderId(), customer);
        orderList.add(newOrder);

        boolean isOrderPlaced = MakeOrderMenu.placeOrder(branchSelected, newOrder);
        
        if (!isOrderPlaced) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        boolean isPaymentSuccessful = MakeOrderMenu.checkOut(newOrder);
        
        if (!isPaymentSuccessful) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        return isOrderPlaced;
    }

    // What was in my mind is that this method is used to add thing after order placement
    public void addItem(String orderId, MenuItem item) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId().equals(orderId)) {
                orderList.get(i).getItems().add(item);
                break;
            }
        }
    }

    // Need to check if the food is ready? If ready, cannot remove
    public void removeItem(String orderId, MenuItem item) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId().equals(orderId)) {
                orderList.get(i).getItems().remove(item);
                break;
            }
        }
    }

    public static double calculateTotal(Order newOrder) {
        double total = 0.0;
        for (MenuItem item : newOrder.getItems()) {
            total += item.getPrice();
        }

        return total;
    }

}
