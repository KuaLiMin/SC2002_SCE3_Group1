package foms.view;
import foms.models.Employee;
import foms.controller.BranchController;
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
            choice = ScannerCheck.verifyInt();

            switch (choice) {
                case 1:
                    ManagerMenu.displayStaffMenu();
                    break;
                case 2:
                    EmployeeController.displayStaffList(manager.getBranch());
                    break;
                case 3:
                    int selection;
                    String itemName;
                    String branch  = manager.getBranch();
                    do {
                        System.out.println("1. Add item to menu");
                        System.out.println("2. Remove item from menu");
                        System.out.println("3. Edit item in menu");     
                        selection = ScannerCheck.verifyInt();
                        switch (selection) {
                            case 1:
                                System.err.println("Enter item name: ");
                                itemName = ScannerCheck.verifyString();
                                System.out.println("Enter item price: ");
                                Double itemPrice = ScannerCheck.verifyDouble();
                                System.out.println("Enter item category");
                                String category = ScannerCheck.verifyString();
                                if (BranchController.addItemToMenuList(itemName, itemPrice, branch, category) == true){
                                    System.out.println("Item " + itemName + "added to "+ branch +" menu successfully");
                                } else System.out.println("Item failed to add to menu");
                                continue;
                            case 2:
                                System.err.println("Enter item name: ");
                                itemName = ScannerCheck.verifyString();
                                if (BranchController.removeItemFromMenu(itemName, branch) == true){
                                    System.out.println("Item "+ itemName +" removed from "+ branch + " menulist");
                                } else System.out.println("Item failed to remove");
                                continue;
                            case 3:
                                System.err.println("Enter item name: ");
                                itemName = ScannerCheck.verifyString();
                                if (BranchController.editItem(itemName, branch) == true){
                                    System.out.println("Item "+ itemName +" edited in "+ branch + " menulist");
                                } else System.out.println("Item failed to edit");
                                continue;
                            default:
                                System.out.println("Invalid choice");
                                continue;
                        }
                    } while (selection<0 || selection>3);
                        

                case 4:
                    System.out.println("Quitting to previous menu");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }
}