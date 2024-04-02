package foms.controller;

import foms.enums.OrderStatus;
import foms.fileio.FileIO;
import foms.models.Order;
import foms.view.MakeOrderMenu;
import foms.models.Branch;
import foms.models.Payment;
import foms.models.Customer;
import foms.models.MenuItem;
import foms.view.PaymentMenu;

// import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// import java.util.Iterator;
import static foms.controller.BranchController.branchList;

public class OrdersController {
    //arraylist of all orders
    private static final ArrayList<Order> orderList = FileIO.getOrderList();
   
    private static ArrayList<Payment> paymentList = Branch.paymentList;

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

    public static String PrintOrderStatus(String orderId) {
        if (!checkOrderExistence(orderId)){
            System.out.println("Order does not exist");
            return null;
        }
        // if (orderId == null || !orderId.matches("[A-Za-z0-9]{3}")) {
        //     throw new UnsupportedOperationException("Unimplemented method 'PrintOrderStatus'");
        // }
        
        OrderStatus STATUS = getOrderStatus(orderId);
        if (STATUS == OrderStatus.COLLECTED){
            System.out.println("OrderID " + orderId + " is collected. ");
        } else if (STATUS == OrderStatus.NEW){
            System.out.println("Order " + orderId + " is new. ");
        } else if (STATUS == OrderStatus.PROCESSING){
            System.out.println("Order " + orderId + " is processing. ");
        } else if (STATUS == OrderStatus.READY_TO_PICKUP){
            System.out.println("Order " + orderId + " is ready for pickup. ");
        } else if (STATUS == OrderStatus.UNKNOWN){
            System.out.println("Order " + orderId + " is unknown. ");
        }
    
        return "Order not found. ";
    }
    
    

    public static OrderStatus getOrderStatus(String OrderID){
        for (Order order : orderList){
            if (order.getOrderId().equals(OrderID)){
                return order.getStatus();
            }
        }

        return OrderStatus.UNKNOWN;
        
    }

    public static Order[] getAllOrders() {
        // if () {
        //     throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
        // }
        return orderList.toArray(new Order[0]);
    }

    public static void displayItemsInOrder(String orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                ArrayList<HashMap<MenuItem, Integer>> items = order.getItems();
                if (items.isEmpty()) {
                    System.out.println("The order has no items.");
                    return;
                }
                System.out.println("Order ID: " + orderId);
                System.out.println("============================================");
                System.out.printf("%-20s %-10s %-10s%n", "Item", "Quantity", "Price");
                for (HashMap<MenuItem, Integer> itemMap : items) {
                    for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                        MenuItem menuItem = entry.getKey();
                        Integer quantity = entry.getValue();
                        System.out.printf("%-20s %-10d %-10.2f%n", menuItem.getName(), quantity, (menuItem.getPrice() * quantity));
                    }
                }
                return;
            }
        }
        System.out.println("Order with ID " + orderId + " not found.");
    }

    public static void printOrderDetails(String orderID) {
        if (orderID == null || !orderID.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
        }

        if (checkOrderExistence(orderID) == false){
            System.out.println("Order does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                System.out.println("------Order Details------");
                PrintOrderStatus(orderID);
                displayItemsInOrder(orderID);
                System.out.println("============================================");
                if (order.getIsTakeAway()) {
                    System.out.printf("%-30s %-20s%n", "Take Away Fee", "$0.5");
                }
                System.out.printf("%-30s $%-20.2f%n", "Total", order.getTotal());
                return;
            }
        }
        System.out.println("Order with ID " + orderID + " not found.");
    }

    public static boolean checkOrderExistence(String OrderId){
        for (Order order : orderList) {
            if(order.getOrderId().equals(OrderId)){
                return true;
            } 
        }
        return false;
    }

    public static void setOrderReadyToPickup(String orderID) {
        if (orderID == null || !orderID.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
        }
        
        if (checkOrderExistence(orderID) == false){
            System.out.println("Order does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                if (order.getStatus().equals(OrderStatus.READY_TO_PICKUP)) {
                    System.out.println(orderID + " is already ready to pickup. ");
                    break;
                }
                if (order.getStatus().equals(OrderStatus.COLLECTED)) {
                    System.out.println(orderID + " is already collected. ");
                    break;
                }
                order.setStatus(OrderStatus.READY_TO_PICKUP);
                System.out.println(orderID + " is ready to pickup! ");
                break;
            }
        }    
    }

    public static void setOrderCollected(String orderId) {
        if (orderId == null || !orderId.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(OrderStatus.COLLECTED);
                break;
            }
        }  
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

        newOrder.setTotal(newOrder.getTotal() + calculateTotal(newOrder));

        boolean isPaymentSuccessful = PaymentMenu.checkOut(branchSelected, newOrder);

        if (!isPaymentSuccessful) {
            orderList.remove(newOrder);
            return isPaymentSuccessful;
        }

        if (isOrderPlaced && isPaymentSuccessful) {
            newOrder.setStatus(OrderStatus.NEW);
            customer.placeOrder(newOrder);
            MakeOrderMenu.printReceipt(newOrder);
        }

        return isOrderPlaced;
    }

    public static void addPaymentMethod(Payment newPaymentMethod){
        paymentList.add(newPaymentMethod);
    }

    public static void removePaymentMethod(Payment PaymentMethod){
        paymentList.removeIf(a -> a.equals(PaymentMethod));
    }

    
}
