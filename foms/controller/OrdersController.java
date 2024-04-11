package foms.controller;

import foms.view.MakeOrderMenu;
import foms.models.Branch;
import foms.models.Staff;
import foms.models.Payment;
import foms.models.Customer;
import foms.models.Order;
import foms.models.MenuItem;
import foms.view.PaymentMenu;
import foms.fileio.FileIO;
import foms.enums.OrderStatus;

import static foms.models.Branch.paymentList;

import java.time.*;
import java.util.*;
import java.security.SecureRandom;

public class OrdersController {
    // arraylist of all orders
    private static ArrayList<Order> orderList = FileIO.getOrderList(); 
    private static ArrayList<Branch> branchList = FileIO.getBranchList();
    private static ArrayList<Payment> paymentlist = paymentList;
    private static final int LENGTH = 3;
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUZWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static double calculateTotal(Order newOrder) {

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

    public static String printOrderStatus(String orderId) {
        if (!checkOrderExistence(orderId)) {
            return "\nOrder does not exist";
        }

        OrderStatus STATUS = getOrderStatus(orderId);
        if (STATUS == OrderStatus.COMPLETED) {
            return "completed";
        } else if (STATUS == OrderStatus.NEW) {
            return "new";
        } else if (STATUS == OrderStatus.READY_TO_PICKUP) {
            return "ready to pickup";
        } else if (STATUS == OrderStatus.CANCELED) {
            return "canceled";

        }
        else {
            return "\nOrder not found. ";
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
                if (order.getRequest().equals("")){
                    System.out.println("No special request");
                } else {
                    System.out.println("Special request: "+ order.getRequest());
                }

                return;
            }
        }
        System.out.println("\nOrder with ID " + orderId + " not found.");
    }

    public static void printOrderDetails(String orderID) {
        if (!checkOrderExistence(orderID)) {
            System.out.println("\nOrder does not exist");
            return;
        }

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderID)) {
                System.out.println("\n--- Order Details ---");
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

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(OrderStatus.COMPLETED);
                order.setCollectedTime(LocalDateTime.now());
                break;
            }
        }
    }

    public static List<String> cancelExpiredOrders() {
        LocalDateTime now = LocalDateTime.now();
        Duration timeframe = Duration.ofMinutes(1);

        List<String> canceledOrderIds = new ArrayList<>();
        Iterator<Order> iterator = orderList.iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getStatus().equals(OrderStatus.READY_TO_PICKUP) && Duration.between(order.getReadyForPickupTime(), now).compareTo(timeframe) > 0) {
                canceledOrderIds.add(order.getOrderId());
                order.setStatus(OrderStatus.CANCELED);
            }
        }

        return canceledOrderIds;
    }

    public static boolean makeNewOrder(Customer customer) {
        Branch branchSelected = BranchController.selectBranch(branchList);
        Order newOrder = new Order(createOrderId(), branchSelected.getName());
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

    public static Map<Integer,Order> getOrderMap(Staff staff){
        int counter = 1;
        Map<Integer, Order> ordersMap = new HashMap<>();
        for (Order order : OrdersController.getAllOrders()) {
            String branch = order.getBranch();
            if (staff.getBranch().equals(branch)) {
                ordersMap.put(counter, order);
                counter++;
            }
        }
        return ordersMap;
    }
    


    public static boolean addPaymentMethod(String name) {
        Payment payment = new Payment(name);
        boolean exists = paymentlist.stream().anyMatch(e -> e.getName().equals(payment.getName()));
        if (!exists) {
            paymentList.add(payment);
            // 数据不持久化到文件
            return true; // 添加成功
        }
        return false;

    }

    public static boolean removePaymentMethod(String name) {
        boolean removed = paymentList.removeIf(payment -> payment.getName().equals(name));
        // 数据不持久化到文件
        return removed;
    }

}
