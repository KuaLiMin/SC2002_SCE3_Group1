package foms.view;
import foms.models.Branch;
import foms.models.Employee;

import static foms.controller.BranchController.editMenuItem;
import static foms.controller.BranchController.removeItemFromMenu;
import static foms.controller.BranchController.removeItemFromMenuItemList;
import static foms.controller.BranchController.printMenuListTable;

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
                    ManagerMenu.displayStaffMenu(manager);
                    break;
                case 2:
                    EmployeeController.displayStaffList(manager.getBranch());
                    break;
                case 3:
                    int selection;
                    String itemName;
                    String branch  = manager.getBranch();
                    printMenuListTable(branch);

                    do {
                        System.out.println("\n1. Add item to menu");
                        System.out.println("2. Remove item from menu");
                        System.out.println("3. Edit item in menu");   
                        System.out.println("4. Quit");  
                        selection = ScannerCheck.verifySelection(1, 4);
                        switch (selection) {
                            case 1:
                                String category;
                                System.out.println("\nEnter item name: ");
                                itemName = ScannerCheck.verifyString();
                                System.out.println("\nEnter item price: ");
                                Double itemPrice = ScannerCheck.verifyDouble();
                                System.out.println("\nEnter item category");
                                System.out.println("1. Burger");
                                System.out.println("2. Side");
                                System.out.println("3. Set meal");
                                System.out.println("4. Drink");
                                int choice1 = ScannerCheck.verifySelection(1,4);
                                if (choice1 == 1){
                                    category = "Burger";
                                } else if (choice1 == 2){
                                    category = "Side";
                                } else if (choice1 == 3){
                                    category = "Set meal";
                                } else category = "Drink";

                                if (BranchController.addItemToMenuList(itemName, itemPrice, branch, category) == true){
                                    System.out.println("Item " + itemName + "added to "+ branch +" menu successfully");
                                } else System.out.println("\nItem failed to add to menu");
                                continue;
                            case 2:
                                removeItemFromMenuItemList(manager.getBranch());
                                continue;
                            case 3:
                                editMenuItem(manager.getBranch());
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