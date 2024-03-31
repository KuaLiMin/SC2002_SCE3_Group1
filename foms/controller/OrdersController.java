package foms.controller;

import foms.enums.OrderStatus;
import foms.fileio.FileIO;
import foms.models.Order;
import foms.view.MakeOrderMenu;
import foms.models.Branch;
import foms.models.Payment;
// import foms.tools.ScannerCheck;
import foms.models.Customer;
import foms.models.MenuItem;
import foms.view.PaymentMenu;

// import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static foms.controller.BranchController.branchList;

public class OrdersController {
    //arraylist of all orders
    private static final ArrayList<Order> orderList = FileIO.getOrderList();
   
    // private static ArrayList<Order> orderList = new ArrayList<>();
    private static ArrayList<Payment> paymentList = new ArrayList<>();

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
        if (orderId == null || !orderId.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'PrintOrderStatus'");
        }
        
        OrderStatus STATUS = getOrderStatus(orderId);
        if (STATUS == OrderStatus.COMPLETED){
            System.out.println("OrderID " + orderId + " is completed");
        } else if (STATUS == OrderStatus.NEW){
            System.out.println("Order " + orderId + " is new");
        } else if (STATUS == OrderStatus.PROCESSING){
            System.out.println("Order " + orderId + " is processing");
        } else if (STATUS == OrderStatus.READY_TO_PICKUP){
            System.out.println("Order " + orderId + " is ready for pickup");
        } 
    
        return "Order not found";
    }
    
    

    public static OrderStatus getOrderStatus(String OrderID){
        for (Order order : orderList){
            if (order.getOrderId().equals(OrderID)){
                return order.getStatus();
            }
        }

        return null;
        
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
                System.out.println("Items in Order ID " + orderId + ":");
                for (HashMap<MenuItem, Integer> itemMap : items) {
                    for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                        MenuItem menuItem = entry.getKey();
                        Integer quantity = entry.getValue();
                        System.out.println("Item: " + menuItem.getName() + ", Quantity: " + quantity + ", Price: " + menuItem.getPrice());
                    }
                }
                return;
            }
        }
        System.out.println("Order with ID " + orderId + " not found.");
    }

    public static void viewOrderDetails(String orderID) {
        if (orderID == null || !orderID.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
        }
        for (Order order : orderList) {
            if (order.getOrderId() == orderID) {
                System.out.println("Order Details:");
                PrintOrderStatus(orderID);
                displayItemsInOrder(orderID);
                System.out.println("Total sum: " + Double.toString(calculateTotal(order)));
                return;
            }
        }
        System.out.println("Order with ID " + orderID + " not found.");
    }

    public static void setOrderReadyToPickup(String orderID) {
        if (orderID == null || !orderID.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                order.setStatus(OrderStatus.READY_TO_PICKUP);
                System.out.println(orderID + " is ready to pickup!");
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
