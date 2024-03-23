package foms.view;
import foms.tools.ScannerCheck;
public class CustomerMenu {
    public static void displayCustomerMenu() {
        System.out.println("Customer Menu");
        System.out.println("1. Please");
        System.out.println("2. cont");
        System.out.println("4. fromhere");
        int selection = ScannerCheck.verifySelection(1, 4);

        if (selection == 1) {
            // 
        } else if (selection == 2) {
            // 
        } else if (selection == 3) {
            // 
        } else {
            // Exit
            return;
        }
    }
    
}
