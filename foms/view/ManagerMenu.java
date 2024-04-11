package foms.view;
// import foms.models.Employee;
import foms.controller.MenuController;
import foms.controller.EmployeeController;
import foms.tools.ScannerCheck;
import foms.enums.*;
import foms.models.*;
import foms.controller.*;

import static foms.controller.BranchController.printBranchList;

import java.util.List;
import java.util.Map;


public class ManagerMenu extends StaffMenu {

    public static void displayManagerMenu(Manager manager) {
        int choice;
        do {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Display New Orders");
            System.out.println("2. View Order Details");
            System.out.println("3. Update Order Status to ready to pickup");
            System.out.println("4. Display Staff List");
            System.out.println("5. Manage Menu Items");
            System.out.println("6. Quit to previous menu");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifySelection(1,6);
            
            Map<Integer, Order> ordersMap = OrdersController.getOrderMap(manager);
            int numberOfOrders = ordersMap.size();

            switch (choice) {
                case 1:
                    System.out.println("Displaying new orders:");
                    int counter1 = 0;
                    for (Order order : OrdersController.getAllOrders()) {
                        OrderStatus status = order.getStatus();
                        String branch = order.getBranch();
                        if (status == OrderStatus.NEW && manager.getBranch().equals(branch)) {
                            counter1++;
                            OrdersController.printOrderDetails(order.getOrderId());
                        }
                    } if (counter1 == 0){
                        System.out.println("No new orders");
                        break;
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
                    System.out.println("Staff in " + manager.getBranch() + ":\n");
                    List<Staff> managerBranch = EmployeeController.getStaffListByAttribute(manager.getBranch(), null, null, 0);
                    EmployeeController.printStaffList(managerBranch);
                    break;
                case 5:
                    int selection;
                    String branch  = manager.getBranch();

                    MenuController.printMenuListTable(branch);

                    do {
                        System.out.println("\n1. Add item to menu");
                        System.out.println("2. Remove item from menu");
                        System.out.println("3. Edit item in menu");   
                        System.out.println("4. Quit");  
                        selection = ScannerCheck.verifySelection(1, 4);
                        switch (selection) {
                            case 1:
                                MenuController.addItemToMenu(manager.getBranch());
                                continue;
                            case 2:
                                MenuController.removeItemFromMenuItemList(manager.getBranch());
                                continue;
                            case 3:
                                MenuController.editMenuItem(manager.getBranch());
                                continue;
                            case 4: 
                                break;
                            default:
                                System.out.println("\nInvalid choice");
                                continue;
                        }
                    } while (selection<0 || selection>4);
                        

                case 6:
                    System.out.println("\nQuitting to previous menu");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice!=6);
    }
}