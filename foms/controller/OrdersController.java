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

import java.time.LocalDateTime;
// import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.time.Duration;
import java.util.List;
// import java.util.Iterator;
import static foms.controller.BranchController.branchList;


public class OrdersController {
    // arraylist of all orders
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

    public static String printOrderStatus(String orderId) {
        if (!checkOrderExistence(orderId)) {
            System.out.println("Order does not exist");
            return null;
        }
        // if (orderId == null || !orderId.matches("[A-Za-z0-9]{3}")) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'PrintOrderStatus'");
        // }

        OrderStatus STATUS = getOrderStatus(orderId);
        if (STATUS == OrderStatus.COMPLETED) {
            System.out.println("OrderID " + orderId + " is completed. ");
        } else if (STATUS == OrderStatus.NEW) {
            System.out.println("Order " + orderId + " is new. ");
        } else if (STATUS == OrderStatus.PROCESSING) {
            System.out.println("Order " + orderId + " is processing. ");
        } else if (STATUS == OrderStatus.READY_TO_PICKUP) {
            System.out.println("Order " + orderId + " is ready for pickup. ");
        } else if (STATUS == OrderStatus.UNKNOWN) {
            System.out.println("Order " + orderId + " is unknown. ");
        }

        return "Order not found. ";
    }

    public static OrderStatus getOrderStatus(String OrderID) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(OrderID)) {
                return order.getStatus();
            }
        }

        return OrderStatus.UNKNOWN;

    }

    public static Order[] getAllOrders() {
        // if () {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAllOrders'");
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
                        System.out.printf("%-20s %-10d %-10.2f%n", menuItem.getName(), quantity,
                                (menuItem.getPrice() * quantity));
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

        if (checkOrderExistence(orderID) == false) {
            System.out.println("Order does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                System.out.println("------Order Details------");
                printOrderStatus(orderID);
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

    public static boolean checkOrderExistence(String OrderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(OrderId)) {
                return true;
            }
        }
        return false;
    }

    public static void setOrderReadyToPickup(String orderID) {
        if (orderID == null || !orderID.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
        }

        if (checkOrderExistence(orderID) == false) {
            System.out.println("Order does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                if (order.getStatus().equals(OrderStatus.READY_TO_PICKUP)) {
                    System.out.println(orderID + " is already ready to pickup. ");
                    break;
                }
                if (order.getStatus().equals(OrderStatus.COMPLETED)) {
                    System.out.println(orderID + " is already collected. ");
                    break;
                }
                order.setStatus(OrderStatus.READY_TO_PICKUP);
                order.setReadyForPickupTime(LocalDateTime.now());
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
                order.setStatus(OrderStatus.COMPLETED);
                break;
            }
        }
    }

    public static void removeCompletedOrder() {
        orderList.removeIf(order -> order.getStatus().equals(OrderStatus.COMPLETED));
    }

    public static List<String> removeExpiredOrders() {
        LocalDateTime now = LocalDateTime.now();
        Duration timeframe = Duration.ofMinutes(1);

        List<String> removedOrderIds = new ArrayList<>();
        Iterator<Order> iterator = orderList.iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getStatus().equals(OrderStatus.READY_TO_PICKUP) && Duration.between(order.getReadyForPickupTime(), now).compareTo(timeframe) > 0) {
                removedOrderIds.add(order.getOrderId());
                iterator.remove();
            }
        }

        return removedOrderIds;
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
            PaymentMenu.printReceipt(newOrder);
        }

        return isOrderPlaced;
    }

    public static boolean addItemToCart (MenuItem selectedItem, Integer quantity, Order newOrder) {
        if (!selectedItem.getAvailablity()) {
            System.out.println("Item is not available. ");
            return false;
        }
        
        // Check if the order already contains the selected item
        boolean itemFound = false;
        for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
            if (itemMap.containsKey(selectedItem)) {
                // Update the existing quantity
                int existingQuantity = itemMap.get(selectedItem);
                itemMap.put(selectedItem, existingQuantity + quantity);
                itemFound = true;
                break;
            }
        }

        // If the item is not found, add it as a new entry
        if (!itemFound) {
            HashMap<MenuItem, Integer> orderItem = new HashMap<>();
            orderItem.put(selectedItem, quantity);
            newOrder.getItems().add(orderItem);
        }

        return true;
    }

    public static void addPaymentMethod(Payment newPaymentMethod) {
        paymentList.add(newPaymentMethod);
    }

    public static void removePaymentMethod(Payment PaymentMethod) {
        paymentList.removeIf(a -> a.equals(PaymentMethod));
    }

}
