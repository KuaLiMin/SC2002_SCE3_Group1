package foms.view;

import foms.controller.OrdersController;
import foms.models.Staff;
import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.enums.OrderStatus;

import java.util.Map;

/**
 * The StaffMenu class provides the user interface for staff interactions within the Food Ordering Management System (FOMS).
 * This class allows staff members to interact with and manage orders through a menu-driven interface.
 * 
 * @author Chen Ziyan
 * @author Charlton Siaw Qi Hen
 * @author Kua Li Min
 * @version 1.0
 * @since 2024-04-15
 */

public class StaffMenu {
    
    /**
     * Displays the menu for staff operations and handles user interactions.
     * Staff can display new orders, view order details, process orders by updating their status,
     * and exit the menu.
     *
     * The menu operates in a loop, continually offering choices until the staff opts to quit.
     * It integrates deeply with the {@link OrdersController} to fetch and manipulate order data.
     *
     * @param staff The staff member logged into the system, used to determine branch-specific interactions.
     */
    public static void displayStaffMenu(Staff staff) {
        int choice;
        do {
            System.out.println("\n--- Staff Menu ---");
            System.out.println("1. Display New Orders");
            System.out.println("2. View Order Details");
            System.out.println("3. Process orders (Change to ready to pick up)");
            System.out.println("4. Quit to previous menu");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifySelection(1,4);
            
            Map<Integer, Order> ordersMap = OrdersController.getOrderMap(staff);
            int numberOfOrders = ordersMap.size();

            switch (choice) {
                case 1:
                    System.out.println("Displaying new orders:");
                    int counter1 = 0;
                    for (Order order : OrdersController.getAllOrders()) {
                        OrderStatus status = order.getStatus();
                        String branch = order.getBranch();
                        if (status == OrderStatus.NEW && staff.getBranch().equals(branch)) {
                            counter1++;
                            OrdersController.printOrderDetails(order.getOrderId());
                        }
                    } if (counter1 == 0){
                        System.out.println("No new orders");
                    } else System.out.println("All new orders has been displayed ");
                    break;

                case 2:
                    if (ordersMap.isEmpty()) {
                        System.out.println("No orders available.");
                        break; // Exit the loop if there are no orders
                    }
                    System.out.println("\nList of orders and their status:");
                    for (Map.Entry<Integer, Order> entry : ordersMap.entrySet()) {
                        Order order = entry.getValue();
                        System.out.println(entry.getKey() + ". " + order.getOrderId() + " ------ " + OrdersController.printOrderStatus(order.getOrderId()));
                    }

                    System.out.println("\nPlease enter the index number of the order you want to inspect: ");
                    int selectedOrderIndex = ScannerCheck.verifySelection(1, numberOfOrders);
                    Order selectedOrder = ordersMap.get(selectedOrderIndex);
                    
                    if (selectedOrder != null) {
                        OrdersController.printOrderDetails(selectedOrder.getOrderId());
                        OrdersController.printOrderStatus(selectedOrder.getOrderId());
                    } else {
                        System.out.println("Invalid order selection. Please try again.");
                    }
                    break;
                    
                case 3:

                    if (ordersMap.isEmpty()) {
                        System.out.println("No orders available.");
                        break; // Exit the loop if there are no orders
                    }
                    System.out.println("\nList of orders and their status:");
                    for (Map.Entry<Integer, Order> entry : ordersMap.entrySet()) {
                        Order order = entry.getValue();
                        System.out.println(entry.getKey() + ". " + order.getOrderId() + " ------ " + OrdersController.printOrderStatus(order.getOrderId()));
                    }
                    System.out.println("\nPlease enter the index number of the order you want to update to ready for pickup: ");
                    selectedOrderIndex = ScannerCheck.verifySelection(1, numberOfOrders);
                    selectedOrder = ordersMap.get(selectedOrderIndex);
                    
                    if (selectedOrder.getStatus() == OrderStatus.NEW) {
                        OrdersController.setOrderReadyToPickup(selectedOrder.getOrderId());
                    } else {
                        System.out.println("This order cannot be set to ready to pickup. Please try again.");
                    }
                    break;
                case 4:
                    System.out.println("Quit to previous menu");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}
