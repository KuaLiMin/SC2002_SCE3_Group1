package foms.view;


import foms.enums.UserRole;
import foms.models.*;
import foms.tools.EmployeeCredCheck;
import foms.tools.ScannerCheck;


public class EmployeeMenu {

    public static void displayEmployeeMenu() {
        EmployeeCredCheck employeeCredCheck = new EmployeeCredCheck();

        int choice;
        do {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Change password");
            System.out.println("3. Quit to Main Menu");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifySelection(1,3); //verifyselection doesnt verify int, maybe need to change scannercheck?
            switch (choice) {
                case 1:
                    Employee loggedInEmployee = employeeCredCheck.login();

                    if (loggedInEmployee instanceof Manager) {
                        Manager manager = (Manager) loggedInEmployee; // Cast Employee to Manager
                        ManagerMenu.displayManagerMenu(manager);    

                    } else if (loggedInEmployee instanceof Staff) {
                        Staff staff = (Staff) loggedInEmployee; // Cast Employee to Staff
                        StaffMenu.displayStaffMenu(staff);
                        
                    } else if (loggedInEmployee instanceof Admin) {
                        AdminMenu.displayAdminMenu();

                    }
                    break;
                case 2:
                    EmployeeCredCheck.changePassword();
                    break;
                case 3:
                    System.out.println("Quit to previous menu");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice >3 || choice <=0);
    }

}