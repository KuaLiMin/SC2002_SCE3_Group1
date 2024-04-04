package foms.view;
import foms.models.Employee;
import foms.controller.MenuController;
import foms.controller.EmployeeController;
import foms.tools.ScannerCheck;

public class ManagerMenu extends StaffMenu {

    public static void displayManagerMenu(Employee manager) {
        int choice;
        do {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Display staff menu");
            System.out.println("2. Display Staff List");
            System.out.println("3. Manage Menu Items");
            System.out.println("4. Quit to previous menu");
            System.out.print("Enter choice: ");
            choice = ScannerCheck.verifySelection(1,4);

            switch (choice) {
                case 1:
                    ManagerMenu.displayStaffMenu(manager);
                    break;
                case 2:
                    EmployeeController.displayStaffList(manager.getBranch());
                    break;
                case 3:
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
                        

                case 4:
                    System.out.println("\nQuitting to previous menu");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}