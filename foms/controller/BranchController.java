package foms.controller;

import java.util.ArrayList;
import java.util.Iterator;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.MenuItem;
import foms.tools.ScannerCheck;

public class BranchController {
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();
    private static ArrayList<MenuItem> menuItemsList = Branch.menuItemsList;

    public static void closeBranches(String branch_name) {
        branchList.removeIf(branch -> branch.getName().equals(branch_name));

    }

    public static void openBranches(String name, String location, ArrayList<MenuItem> menuItemsList, int staffQuota,
            int staffCount, int managerCount, int managerQuota) {
        Branch branch = new Branch(name, location /* , menuItemsList, staffQuota, staffCount, managerCount, managerQuota*/);
        branchList.add(branch);
    }

    public static Branch selectBranch(ArrayList<Branch> branchList) {
        int selection;

        do {
            System.out.println("Please select your current branch: ");
            for (int i = 0; i < branchList.size(); i++) {
                System.out.println(i + ". " + branchList.get(i));
            }
            selection = ScannerCheck.verifySelection(1, branchList.size());
        } while (selection <= 0 || selection > branchList.size());

        return branchList.get(selection);
    }

    public static boolean addItemToMenuList(String itemName, double itemPrice, String branch, String category /* , availability*/) {
        // Check if the item already exists in the menu
        for (MenuItem item : menuItemsList) {
            if (item.getName().equals(itemName) && item.getBranch().equals(branch)) {
                return false;
            }
        }
        // Item does not exist, so add it to the menu
        MenuItem newItem = new MenuItem(itemName, itemPrice, branch, category /* , availability*/);
        menuItemsList.add(newItem);
        return true;
    }
    
    public static boolean removeItemFromMenu(String itemName, String branch) {
        // Iterate through the menu items list to find the item to remove
        Iterator<MenuItem> iterator = menuItemsList.iterator();
        while (iterator.hasNext()) {
            MenuItem item = iterator.next();
            if (item.getName().equals(itemName) && item.getBranch().equals(branch)) {
                iterator.remove();
                return true;
            }
        }
        // Item was not found in the menu
        return false;
    }
    
    public static boolean editItem(String name, String branch) {
        
        for (MenuItem item : menuItemsList) {
            if (item.getName().equals(name) && item.getBranch().equals(branch)) {
                int choice;
                System.out.println("Item name: "+ item.getName() + ", price: " + item.getPrice() + "is "+ item.isAvailable());
                do {
                    System.out.println("1. Edit item price");
                    System.out.println("2. Edit item availability");
                    System.out.println("3. Exit");
                    choice = ScannerCheck.verifyInt();
                    switch (choice) {
                        case 1:
                            System.out.print("Enter new price: ");
                            double newPrice = ScannerCheck.verifyDouble();
                            item.setPrice(newPrice);
                            return true;
                        case 2:
                            System.out.print("Enter new availability (true/false): ");
                            boolean newAvailability = ScannerCheck.verifyBool();
                            item.setAvailability(newAvailability);
                            return true;
                        case 3:
                            System.out.println("Exiting...");
                            return false;
                        default:
                            System.out.println("Invalid choice. Please choose between 1, 2, and 3.");
                            break;
                    }
                } while (choice < 1 || choice > 3);
            }
        }
        // Item was not found in the menu
        return false;
    }
}
