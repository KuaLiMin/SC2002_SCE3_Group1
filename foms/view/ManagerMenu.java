package foms.view;
import foms.models.Employee;
import foms.controller.EmployeeController;
import foms.tools.ScannerCheck;

public class ManagerMenu extends StaffMenu {

    public static void displayManagerMenu(Employee manager) {
        int choice;
        do {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Display New Orders");
            System.out.println("2. View Order Details");
            System.out.println("3. Update Order Status to ready to pickup");
            System.out.println("4. Display Staff List");
            System.out.println("5. Manage Menu Items");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifyInt();

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    ManagerMenu.displayStaffMenu();
                    break;
                case 4:
                    EmployeeController.displayStaffList(manager.getBranch());
                    break;
                case 5:
                    //MenuController.displayMenu();
                    // BranchController.displayMenu();
                    // put displaymenu in branchcontroller

                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }
}