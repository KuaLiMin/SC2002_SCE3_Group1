package foms.view;

import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.controller.OrdersController;
import foms.enums.OrderStatus;

public class StaffMenu {
    
    public static void displayStaffMenu() {
        int choice;
        String orderID;
        do {
            System.out.println("\n--- Staff Menu ---");
            System.out.println("1. Display New Orders");
            System.out.println("2. View Order Details");
            System.out.println("3. Update Order Status to ready to pickup");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifyInt();

            switch (choice) {
                case 1:
                    System.out.println("Displaying new orders:");
                    for (Order order : OrdersController.getAllOrders()) {
                        OrderStatus status = order.getStatus();
                        if (status == OrderStatus.NEW) {
                            System.out.println(order); 
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter order ID: ");
                    orderID = ScannerCheck.verifyString();
                    OrdersController.viewOrderDetails(orderID);
                    break;
                case 3:
                    System.out.println("Enter order ID: ");
                    orderID = ScannerCheck.verifyString();
                    OrdersController.setOrderReadyToPickup(orderID);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}
