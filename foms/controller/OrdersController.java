package foms.controller;

import foms.fileio.FileIO;
import foms.models.Order;
import foms.view.MakeOrderMenu;
import foms.models.Branch;
import foms.models.Payment;
import foms.tools.ScannerCheck;
import foms.models.Customer;
import foms.models.MenuItem;
import foms.view.PaymentMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static foms.controller.BranchController.branchList;

public class OrdersController {
    //arraylist of all orders
    private static final ArrayList<Order> orderList = FileIO.getOrderList();
   
<<<<<<< Updated upstream
    private static ArrayList<Order> orderList = new ArrayList<>();
    private static ArrayList<Payment> paymentList = new ArrayList<>();


    // get order list
    /* public static ArrayList<Order> getOrderList() {
        return new ArrayList<Order>();
    } */

=======
    // private static ArrayList<Order> orderList = new ArrayList<>();
    // get order list
    // public static ArrayList<Order> getOrderList() {
    //     return new ArrayList<Order>();
    // public static ArrayList<Order> getOrderList() {
    //     return new ArrayList<Order>();
    // }
>>>>>>> Stashed changes

    public static double calculateTotal(Order newOrder) {
        if (newOrder == null || newOrder.getItems().isEmpty()) {
            throw new UnsupportedOperationException("Unimplemented method 'calculateTotal'");
        }

        double total = 0.0;
        for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
            for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                MenuItem item = entry.getKey();
                Integer quantity = entry.getValue();

                total += item.getPrice() * quantity;
            }
        }

        return total;
    }

<<<<<<< Updated upstream
    public static String getOrderStatus(String orderId) {
        if (orderId == null || !orderId.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'getOrderStatus'");
        }
        
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order.getStatus();
            }
        }

        return "Order not found";
    }

    public static Order[] getAllOrders() {
        if () {
            throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
        }
        
    }

    public static void viewOrderDetails(String orderID) {
        if () {
            throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
        }
        
    }

    public static void setOrderReadyToPickup(String orderID) {
        if () {
            throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
        }
        
=======
    public static Order[] getAllOrders() {
        // TODO Auto-generated method stub 
        return orderList.toArray(new Order[0]);

        // throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    // public static String getOrderDetails(String orderId) {
    //     for (Order order : orderList){
    //         if (order.getOrderId() == orderId){
                
    //         }
    //     }        
    //     throw new UnsupportedOperationException("Unimplemented method 'getOrderStatus'");

    // }

    public static void viewOrderStatus(String orderID) {
        for (Order order : orderList) {
            if (order.getOrderId() == orderID) {
                System.out.println("Order Details:");
                System.out.println("OrderId: " + order.getOrderId());
                System.out.println("Status: " + order.getStatus());
                return;
            }
        }
        System.out.println("Order with ID " + orderID + " not found.");
        throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
    }

    public static void setOrderReadyToPickup(String orderID) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                order.setStatus("Ready to pickup");
                System.out.println(orderID + " is ready to pickup!");
                break;
            }
        }        
        throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
>>>>>>> Stashed changes
    }

    public static boolean makeNewOrder(Customer customer) {
        Branch branchSelected = BranchController.selectBranch(branchList);
        Order newOrder = new Order(MakeOrderMenu.createOrderId());
        orderList.add(newOrder);

        boolean isOrderPlaced = MakeOrderMenu.placeOrder(branchSelected, newOrder);

        if (!isOrderPlaced) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        newOrder.setTotal(calculateTotal(newOrder));

        boolean isPaymentSuccessful = PaymentMenu.checkOut(branchSelected, newOrder);

        if (!isPaymentSuccessful) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        if (isOrderPlaced && isPaymentSuccessful) {
            customer.placeOrder(newOrder);
            MakeOrderMenu.printReceipt(newOrder);
        }

        return isOrderPlaced;
    }


    public boolean addItem(String orderId, MenuItem item, Integer quantity) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                HashMap<MenuItem, Integer> orderItem = new HashMap<>();
                orderItem.put(item, quantity);
                order.getItems().add(orderItem);
                return true;
            }
        }

        return false;
    }

    // Need to check if the food is ready? If ready, cannot remove
    public boolean removeItem(String orderId, MenuItem item) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                ArrayList<HashMap<MenuItem, Integer>> items = order.getItems();
                for (int j = 0; j < items.size(); j++) {
                    HashMap<MenuItem, Integer> orderItem = items.get(j);
                    if (orderItem.containsKey(item)) {
                        items.remove(j);
                        return true;
                    }
                }
                break;
            }
        }

        return false;
    }

    public static void addPaymentMethod(Payment newPaymentMethod){
        paymentList.add(newPaymentMethod);
    }

    public static void removePaymentMethod(Payment PaymentMethod){
        paymentList.removeIf(a -> a.equals(PaymentMethod));
    }

    
}
