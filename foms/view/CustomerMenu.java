package foms.view;

import foms.controller.OrdersController;
import foms.tools.ScannerCheck;
import foms.models.Customer;
import foms.models.Order;

public class CustomerMenu {
    public static void displayCustomerMenu(Customer customer) {
        System.out.println("Customer Menu");
        System.out.println("1. Place New Order");
        System.out.println("2. Track Order Status");
        System.out.println("3. Collect Food");
        System.out.println("4. Exit");
        int selection = ScannerCheck.verifySelection(1, 4);

        do {
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
                    // track order
                case 3:
                    // collect food
                case 4:
                    return;
                default:
                    break;
            }
        } while (selection<=0 || selection>4);
    }
    
}
