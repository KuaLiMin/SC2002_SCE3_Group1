package foms.view;

import foms.controller.OrdersController;
import foms.enums.OrderStatus;
import foms.tools.ScannerCheck;
import foms.models.Customer;

public class CustomerMenu {
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
                            OrdersController.printOrderStatus(OrderID);
                            OrdersController.printOrderDetails(OrderID);
                        } else if (choice == 2) {
                            OrderStatus STATUS = OrdersController.getOrderStatus(OrderID);
                            if(STATUS == OrderStatus.READY_TO_PICKUP){
                                OrdersController.setOrderCollected(OrderID);
                                System.out.println("\nEnjoy your food! ");
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
    
}
