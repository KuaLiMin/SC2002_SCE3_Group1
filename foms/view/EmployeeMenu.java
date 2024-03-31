package foms.view;


import foms.tools.EmployeeCredCheck;
import foms.tools.ScannerCheck;


public class EmployeeMenu {

    public static void displayEmployeeMenu() {
        
        int choice;
        do {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Change password");
            System.out.println("3. Quit to Main Menu");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifyInt(); //verifyselection doesnt verify int, maybe need to change scannercheck?
            
            switch (choice) {
                case 1:
                    EmployeeCredCheck.login();
                case 2:
                    EmployeeCredCheck.changePassword();
                case 3:
                    System.out.println("Exiting to Main Menu");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice >2 || choice <=0);
    }

}