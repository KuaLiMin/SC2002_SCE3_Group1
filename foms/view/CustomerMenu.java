package foms.view;

import foms.controller.OrdersController;
import foms.enums.OrderStatus;
import foms.tools.ScannerCheck;
import foms.models.Customer;

/**
 * This class provides the user interface for the customer menu.
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class CustomerMenu {
    
    /**
     * Displays the customer menu with options to place a new order, 
     * check on an existing order, or quit to the main menu. This menu will loop until the customer
     * decides to quit to the main menu.
     *
     * @param customer The customer for whom the menu is being displayed.
     */
    public static void displayCustomerMenu(Customer customer) {
        int selection;
       
        do {
            System.out.println("\n --- Customer Menu --- ");
            System.out.println("1. Place New Order");
            System.out.println("2. Existing Order");
            System.out.println("3. Quit to Main Menu");
            selection = ScannerCheck.verifySelection(1, 3);
            switch (selection)
            {
                case 1:
                    if(OrdersController.makeNewOrder(customer)) {
                        System.out.println("\nSuccessfully place order. ");
                    }
                    else {
                        System.out.println("\nUnsuccessful order. ");
                    }
                    break;
                case 2:
                    int choice;
                    System.out.println("\nPlease enter your Order ID: ");
                    String OrderID = ScannerCheck.verifyString();
                    if(!OrdersController.checkOrderExistence(OrderID)){
                        System.out.println("\nOrder does not exist");
                        break;
                    }

                    do {
                        System.out.println("\n--- Existing Order ---");
                        System.out.println("1. Check order status");
                        System.out.println("2. Collect your food");
                        System.out.println("3. Exit");
                        choice = ScannerCheck.verifySelection(1, 3);
                        if (choice == 1) {
                            OrdersController.printOrderDetails(OrderID);
                            System.out.println(OrderID + " is " + OrdersController.printOrderStatus(OrderID));

                        } else if (choice == 2) {
                            OrderStatus STATUS = OrdersController.getOrderStatus(OrderID);
                            if(STATUS == OrderStatus.READY_TO_PICKUP){
                                OrdersController.setOrderCollected(OrderID);
                                System.out.println("\nEnjoy your food! ");
                            } else if (STATUS == OrderStatus.COMPLETED){
                                System.out.println("\nOrder has already been collected");
                            } else if (STATUS == OrderStatus.CANCELED){
                                System.out.println("\nOrder is canceled. ");
                            } else {
                                System.out.println("\nOrder is not ready for pick up");
                            }
                        }else if (choice == 3) {
                            System.out.println("\nExiting...");
                            return;
                        } else {
                            System.out.println("\nInvalid choice. Please choose between 1 and 3.");
                        }
                    } while (choice>0 && choice<=3);
                case 3:
                    System.out.println("\nExiting... ");
                    return;

                default:
                    break;
            }
        } while (selection>0 && selection<=3);
    }
    /**
     * The default constructor for the CustomerMenu class.
     * This constructor initializes the class with default values.
     * 
     * Note: This constructor is provided implicitly by Java when no other constructors are defined explicitly.
     */
    public CustomerMenu() {} 
}
