package foms.view;

import foms.models.Staff;
import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.controller.OrdersController;
import foms.enums.OrderStatus;
import java.util.Map;

public class StaffMenu {
    
    public static void displayStaffMenu(Staff staff) {
        int choice;
        do {
            System.out.println("\n--- Staff Menu ---");
            System.out.println("1. Display New Orders");
            System.out.println("2. View Order Details");
            System.out.println("3. Update Order Status to ready to pickup");
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
                    
                    if (selectedOrder != null) {
                        OrdersController.setOrderReadyToPickup(selectedOrder.getOrderId());
                    } else {
                        System.out.println("Invalid order selection. Please try again.");
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
