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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import static foms.controller.BranchController.branchList;


public class OrdersController {
    // arraylist of all orders
    private static ArrayList<Order> orderList = FileIO.getOrderList();
    private static ArrayList<Payment> paymentList = Branch.paymentList;
    private static final int LENGTH = 3;
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUZWXYZabcdefghijklmnopqrstuvwxyz0123456789";

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

        if (newOrder.getIsTakeAway()) {
            total += 0.5;
        }

        return total;
    }

    public static void printOrderStatus(String orderId) {
        if (!checkOrderExistence(orderId)) {
            System.out.println("\nOrder does not exist");
            return;
        }
        // if (orderId == null || !orderId.matches("[A-Za-z0-9]{3}")) {
        // throw new UnsupportedOperationException("Unimplemented method
        // 'PrintOrderStatus'");
        // }

        OrderStatus STATUS = getOrderStatus(orderId);
        if (STATUS == OrderStatus.COMPLETED) {
            System.out.println("\nOrderID " + orderId + " is completed. ");
        } else if (STATUS == OrderStatus.NEW) {
            System.out.println("\nOrder " + orderId + " is new. ");
        } else if (STATUS == OrderStatus.PROCESSING) {
            System.out.println("\nOrder " + orderId + " is processing. ");
        } else if (STATUS == OrderStatus.READY_TO_PICKUP) {
            System.out.println("\nOrder " + orderId + " is ready for pickup. ");
        } else if (STATUS == OrderStatus.UNKNOWN) {
            System.out.println("\nOrder " + orderId + " is unknown. ");
        }
        else {
            System.out.println("\nOrder not found. ");
        }
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
                    System.out.println("\nThe order has no items.");
                    return;
                }
                System.out.println("\nBranch: " + order.getBranch());
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
        System.out.println("\nOrder with ID " + orderId + " not found.");
    }

    public static void printOrderDetails(String orderID) {
        if (orderID == null || !orderID.matches("[A-Za-z0-9]{3}")) {
            throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
        }

        if (checkOrderExistence(orderID) == false) {
            System.out.println("\nOrder does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                System.out.println("\n--- Order Details ---");
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
        System.out.println("\nOrder with ID " + orderID + " not found.");
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

        if (!checkOrderExistence(orderID)) {
            System.out.println("\nOrder does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                if (order.getStatus().equals(OrderStatus.READY_TO_PICKUP)) {
                    System.out.println("\n" + orderID + " is already ready to pickup. ");
                    break;
                }
                if (order.getStatus().equals(OrderStatus.COMPLETED)) {
                    System.out.println("\n" + orderID + " is already collected. ");
                    break;
                }
                order.setStatus(OrderStatus.READY_TO_PICKUP);
                order.setReadyForPickupTime(LocalDateTime.now());
                System.out.println("\n" + orderID + " is ready to pickup! ");
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
                order.setCollectedTime(LocalDateTime.now());
                break;
            }
        }
    }

    public static List<String> removeCompletedOrders() {
        LocalDateTime now = LocalDateTime.now();
        Duration timeframe = Duration.ofMinutes(1);

        List<String> removedOrderIds = new ArrayList<>();
        Iterator<Order> iterator = orderList.iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getStatus().equals(OrderStatus.COMPLETED)) {
                LocalDateTime collectedTime = order.getCollectedTime();
                if (collectedTime != null && Duration.between(collectedTime, now).compareTo(timeframe) > 0) {
                    removedOrderIds.add(order.getOrderId());
                    iterator.remove();
                }
            }
        }

        return removedOrderIds;
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
        Order newOrder = new Order(createOrderId());
        orderList.add(newOrder);

        boolean isDiningPreference = MakeOrderMenu.displayDiningPreference(newOrder);

        if (!isDiningPreference) {
            orderList.remove(newOrder);
            return isDiningPreference;
        }

        boolean isOrderPlaced = MakeOrderMenu.displayMakeOrderMenu(branchSelected, newOrder);

        if (!isOrderPlaced) {
            orderList.remove(newOrder);
            return isOrderPlaced;
        }

        boolean isPaymentSuccessful = PaymentMenu.displayPaymentMenu(branchSelected, newOrder);

        if (!isPaymentSuccessful) {
            orderList.remove(newOrder);
            return isPaymentSuccessful;
        }

        if (isOrderPlaced && isPaymentSuccessful) {
            newOrder.setStatus(OrderStatus.NEW);
            newOrder.setBranch(branchSelected.getName());
            customer.setOrder(newOrder);
            PaymentMenu.printReceipt(newOrder);
        }

        return isOrderPlaced;
    }

    public static boolean editItemInCart(int qty, int itemNumber, Order newOrder) {
        int currentNumber = 0;
        Iterator<HashMap<MenuItem, Integer>> iterator = newOrder.getItems().iterator();

        while (iterator.hasNext()) {
            Map.Entry<MenuItem, Integer> entry = iterator.next().entrySet().iterator().next();
            currentNumber++;
            if (currentNumber == itemNumber) {
                if (qty == 0) {
                    // Remove item
                    iterator.remove();
                } else {
                    // Update item quantity
                    entry.setValue(qty);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean addItemToCart (MenuItem selectedItem, Integer quantity, Order newOrder) {
        if (!selectedItem.getAvailablity()) {
            System.out.println("\nItem is not available. ");
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
                return true;
            }
        }

        // If the item is not found, add it as a new entry
        if (!itemFound) {
            HashMap<MenuItem, Integer> orderItem = new HashMap<>();
            orderItem.put(selectedItem, quantity);
            newOrder.getItems().add(orderItem);
            return true;
        }

        return false;
    }

    public static String createOrderId() {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new SecureRandom();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHAR_SET.length());
            sb.append(CHAR_SET.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static void addPaymentMethod(Payment newPaymentMethod) {
        paymentList.add(newPaymentMethod);
    }

    public static void removePaymentMethod(Payment PaymentMethod) {
        paymentList.removeIf(a -> a.equals(PaymentMethod));
    }

}
