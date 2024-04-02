package foms.view;

import foms.controller.OrdersController;
import foms.enums.OrderStatus;
import foms.tools.ScannerCheck;
import foms.models.Customer;
// import foms.models.Order;

public class CustomerMenu {
    public static void displayCustomerMenu(Customer customer) {
        int selection;
       
        do {
            System.out.println("\n --- Customer Menu --- ");
            System.out.println("1. Place New Order");
            System.out.println("2. Existing Order");
            System.out.println("3. Exit");
            selection = ScannerCheck.verifySelection(1, 4);
            switch (selection)
            {
                case 1:
                    if(OrdersController.makeNewOrder(customer)) {
                        System.out.println("Successfully place order. ");
                    }
                    else {
                        System.out.println("Unsuccessful order. ");
                    }
                    break;
                case 2:
                    int choice;
                    System.out.println("Please enter your Order ID: ");
                    String OrderID = ScannerCheck.verifyString();
                    if(!OrdersController.checkOrderExistence(OrderID)){
                        System.out.println("Order does not exist");
                        break;
                    }

                    do {
                        System.out.println("1. Check order status");
                        System.out.println("2. Collect your food");
                        System.out.println("3. Exit");
                        choice = ScannerCheck.verifyInt();
                        if (choice == 1) {
                            OrdersController.PrintOrderStatus(OrderID);
                        } else if (choice == 2) {
                            OrderStatus STATUS = OrdersController.getOrderStatus(OrderID);
                            if(STATUS == OrderStatus.READY_TO_PICKUP){
                                OrdersController.setOrderCollected(OrderID);
                                System.out.println("Order " + OrderID + "has been picked up");
                            } else {
                                System.out.println("Order is not ready for pick up");
                                OrdersController.PrintOrderStatus(OrderID);
                            }
                        }else if (choice == 3) {
                            System.out.println("Exiting...");
                            return;
                        } else {
                            System.out.println("Invalid choice. Please choose between 1 and 2.");
                        }
                    } while (choice>0 && choice<=3);
                case 3:
                    return;

                default:
                    break;
            }
        } while (selection>0 && selection<=3);
    }
    
}
