
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

/**
 * The OrdersController class handles the management of orders within the food management system.
 * It provides functionality to create, modify, display, and check the status of orders.
 * @author Charlton Siaw Qi Hen
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class OrdersController {
    /**
     * A list containing all the orders retrieved from persistent storage.
     * This list is used to perform operations on orders throughout the system.
     */    
    private static ArrayList<Order> orderList = FileIO.getOrderList(); 
    
    /**
     * A list of branches retrieved from persistent storage.
     * This list is used when creating orders to determine the branch from which an order is placed.
     */
    private static ArrayList<Branch> branchList = FileIO.getBranchList();
    
    /**
     * A list of payment methods available in a particular branch.
     * This list is utilized during the payment process of an order.
     */
    private static ArrayList<Payment> paymentlist = paymentList;
        
    /**
     * The fixed length for generating order IDs.
     * This length is used in conjunction with a character set to generate a unique identifier for each order.
     */
    private static final int LENGTH = 3;
        
    /**
     * The set of characters used to generate order IDs.
     * The order ID is constructed by randomly selecting characters from this set to the specified LENGTH.
     */
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUZWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    
    /**
     * Calculates the total cost of an order including extra charges if applicable.
     *
     * @param newOrder The Order object for which the total is to be calculated.
     * @return The total cost of the order.
     */
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

    /**
     * Retrieves the status of a specific order formatted as a String.
     *
     * @param orderId The ID of the order whose status is to be printed.
     * @return A string representing the order's status if found, otherwise a message indicating the order doesn't exist.
     */
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

    /**
     * Gets the status of an order based on its ID.
     *
     * @param OrderID The ID of the order.
     * @return The current status of the order as an OrderStatus enum.
     */
    public static OrderStatus getOrderStatus(String OrderID) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(OrderID)) {
                return order.getStatus();
            }
        }
        return OrderStatus.UNKNOWN;
    }
    
    /**
     * Retrieves all orders in a static array format.
     *
     * @return An array containing all the orders.
     */
    public static Order[] getAllOrders() {
        return orderList.toArray(new Order[0]);
    }

    /**
     * Displays the details of the items in a given order.
     *
     * @param orderId The ID of the order to display items from.
     */
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

    /**
     * Prints the detailed summary of an order including itemized costs and total.
     *
     * @param orderID The ID of the order whose details are to be printed.
     */
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

    /**
     * Checks if an order exists in the system.
     *
     * @param OrderId The ID of the order to check.
     * @return true if the order exists, false otherwise.
     */
    public static boolean checkOrderExistence(String OrderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(OrderId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the status of an order to indicate it is ready for pickup.
     *
     * @param orderID The ID of the order to update.
     */
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

    /**
     * Updates the status of an order to indicate it has been collected.
     *
     * @param orderId The ID of the order to update.
     */
    public static void setOrderCollected(String orderId) {

        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(OrderStatus.COMPLETED);
                order.setCollectedTime(LocalDateTime.now());
                break;
            }
        }
    }

    /**
     * Cancels orders that have been ready for pickup past a certain time frame.
     *
     * @return A list of IDs of orders that have been canceled.
     */
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

    /**
     * Facilitates the process of making a new order including selection of branch, order details and payment.
     *
     * @param customer The customer making the new order.
     * @return true if the order is successfully made and payment is processed, false otherwise.
     */
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
            // customer.setOrder(newOrder);
            PaymentMenu.printReceipt(newOrder);
        }

        return isOrderPlaced;
    }

    /**
     * Edits the quantity of an item in the cart, or removes the item if the quantity is set to zero.
     *
     * @param qty        The new quantity for the item.
     * @param itemNumber The number corresponding to the item in the order.
     * @param newOrder   The order in which the item to be edited is located.
     * @return true if the item was successfully edited or removed, false otherwise.
     */
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

    /**
     * Adds an item to the cart in the specified order.
     *
     * @param selectedItem The menu item to add to the cart.
     * @param quantity     The quantity of the menu item to add.
     * @param newOrder     The order to which the item will be added.
     * @return true if the item is successfully added to the cart, false if the item is not available.
     */
    public static boolean addItemToCart (MenuItem selectedItem, Integer quantity, Order newOrder) {
        if (!selectedItem.getAvailability()) {
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

    /**
     * Generates a random order ID of a specified length using a secure random algorithm.
     *
     * @return A randomly generated order ID.
     */
    public static String createOrderId() {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new SecureRandom();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHAR_SET.length());
            sb.append(CHAR_SET.charAt(randomIndex));
        }

        return sb.toString();
    }

    /**
     * Retrieves a map of all orders associated with a staff member's branch, with an integer key for each order.
     *
     * @param staff The staff member whose branch's orders are to be retrieved.
     * @return A map of orders with integer keys.
     */
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

    /**
     * Adds a payment method to the list of available payment methods if it does not already exist.
     *
     * @param name The name of the payment method to add.
     * @return true if the payment method was added successfully, false if it already exists.
     */
    public static boolean addPaymentMethod(String name) {
        Payment payment = new Payment(name);
        boolean exists = paymentlist.stream().anyMatch(e -> e.getName().equals(payment.getName()));
        if (!exists) {
            paymentList.add(payment);
            return true; 
        }
        return false;

    }

    /**
     * Removes a payment method from the list of available payment methods.
     *
     * @param name The name of the payment method to remove.
     * @return true if the payment method was removed successfully, false otherwise.
     */
    public static boolean removePaymentMethod(String name) {
        boolean removed = paymentList.removeIf(payment -> payment.getName().equals(name));
        return removed;
    }

    /**
     * The default constructor for the OrdersController class.
     * This constructor initializes the class with default values.
     * 
     * Note: This constructor is provided implicitly by Java when no other constructors are defined explicitly.
     */
    public OrdersController() {}

}
