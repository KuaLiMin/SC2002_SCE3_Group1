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

public class OrdersController {
    //arraylist of all orders
    //private static final ArrayList<Order> orderList = FileIO.getOrderList();
   
    private static ArrayList<Order> orderList = new ArrayList<>();



    // get order list
    public static ArrayList<Order> getOrderList() {
        return new ArrayList<Order>();
    public static ArrayList<Order> getOrderList() {
        return new ArrayList<Order>();
    }

    public static double calculateTotal(Order newOrder) {
        if (/* some condition */) {
            throw new UnsupportedOperationException("Unimplemented method 'calculateTotal'");
        }

        double total = 0.0;
        for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
            for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                MenuItem menuItem = entry.getKey();
                Integer quantity = entry.getValue();

                total += menuItem.getPrice() * quantity;
            }
        }

        return total;
    }

    public static Order[] getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    public static String getOrderStatus(String orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderStatus'");
    }

    public static void viewOrderDetails(String orderID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
    }

    public static void setOrderReadyToPickup(String orderID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
    }

    public static boolean makeNewOrder(Customer customer) {
        Branch branchSelected = MakeOrderMenu.selectBranch(branchList);
        Order newOrder = new Order(MakeOrderMenu.createOrderId());
        orderList.add(newOrder);

        boolean isOrderPlaced = MakeOrderMenu.placeOrder(branchSelected, newOrder);

        if (!isOrderPlaced) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        boolean isPaymentSuccessful = PaymentMenu.checkOut(branchSelected, newOrder);

        if (!isPaymentSuccessful) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        if (isOrderPlaced && isPaymentSuccessful) {
            customer.placeOrder(newOrder);
        }

        return isOrderPlaced;
    }

    // What was in my mind is that this method is used to add thing after order
    // placement
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

<<<<<<< Updated upstream
=======
    public static double calculateTotal(Order newOrder) {
        double total = 0.0;
        for (MenuItem item : newOrder.getItems()) {
            total += item.getPrice();
        }

        return total;
    }
    public void addPaymentMethod(Payment payment_method){
        paymentList.add(payment_method);
    }
    public void removePaymentMethod(String payment_method){
        paymentList.removeIf(payment -> payment.getName().equals(payment_method));
    }

>>>>>>> Stashed changes
}
