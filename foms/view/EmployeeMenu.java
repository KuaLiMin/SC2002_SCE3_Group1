package foms.view;

import foms.models.Employee;
import foms.models.Staff;
import foms.models.Manager;
import foms.models.Admin;
import foms.tools.EmployeeCredCheck;
import foms.tools.ScannerCheck;

/**
 * The EmployeeMenu class provides a central navigation point for different types of employees
 * within the Food Ordering Management System (FOMS). This class handles the login and directs
 * employees to their respective menus based on their roles.
 *
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */

public class EmployeeMenu {

    /**
     * Displays the main menu for employee interactions and handles user inputs for various actions
     * such as login, password change, and exiting to the main menu. Upon successful login, employees
     * are directed to role-specific menus where they can access functionalities tailored to their roles.
     *
     * This method continuously displays the menu and processes user choices in a loop until the user
     * decides to quit to the main menu.
     */
    public static void displayEmployeeMenu() {
        EmployeeCredCheck employeeCredCheck = new EmployeeCredCheck();

        int choice;
        do {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Change password");
            System.out.println("3. Quit to Main Menu");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifySelection(1,3);
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