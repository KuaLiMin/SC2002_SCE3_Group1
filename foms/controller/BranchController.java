package foms.controller;

import java.util.ArrayList;
import java.util.Iterator;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.MenuItem;
import foms.tools.ScannerCheck;

public class BranchController {
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();

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
            System.out.println("\nPlease select your current branch: ");
            for (int i = 0; i < branchList.size(); i++) {
                System.out.println((i+1) + ". " + branchList.get(i).getName());
            }
            selection = ScannerCheck.verifySelection(1, branchList.size());
        } while (selection <= 0 || selection > branchList.size());

        return branchList.get(selection-1);
    }

    // this should be addItemToMenuList(branch obj/branchname, <all your menuitem attributes>)
    public static boolean addItemToMenuList(String itemName, double itemPrice, String branchName, String category, String description, boolean availability) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);
        if (branch != null) {
            // Check if the item already exists in the menu
            for (MenuItem item : branch.getMenuItemsList()) {
                if (item.getName().equals(itemName)) {
                    return false; // Item already exists
                }
            }
            // Item does not exist, so add it to the menu
            MenuItem newItem = new MenuItem(itemName, itemPrice, branchName, category, description, availability);
            branch.getMenuItemsList().add(newItem);
            return true;
        }
        return false; // Branch not found
    }

    public static void removeItemFromMenuItemList(String branchName) {
        Branch branch = branchList.stream()
            .filter(b -> b.getName().equals(branchName))
            .findFirst()
            .orElse(null);

        printMenuListTable(branchName);
    
        int selection;
        do {
            System.out.println("Select the item to remove (Enter the corresponding number): ");
            selection = ScannerCheck.verifySelection(1, branch.getMenuItemsList().size());
        } while (selection <= 0 || selection > branch.getMenuItemsList().size());
            MenuItem selectedMenuItem = branch.getMenuItemsList().get(selection - 1);
            boolean removed = removeItemFromMenu(selectedMenuItem.getName(), branch.getName());
    
        if (removed) {
            System.out.println(selectedMenuItem.getName() + " has been removed from the menu of " + branch.getName() + ".");
        } else {
            System.out.println("Failed to remove " + selectedMenuItem.getName() + " from the menu of " + branch.getName() + ".");
        }
    }

    public static boolean removeItemFromMenu(String itemName, String branchName) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);
        if (branch != null) {
            return branch.getMenuItemsList().removeIf(item -> item.getName().equals(itemName));
        }
        return false; // Branch not found
    }

    public static void printMenuListTable(String branchName) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);
    
        if (branch != null) {
            System.out.println("\nExisting menu items in " + branchName + ":");
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-10s %-15s %-30s %-10s%n", "Index", "Name", "Price", "Category", "Description", "Availability");
            System.out.println("------------------------------------------------------------------------------------------------------");
    
            int index = 1;
            for (MenuItem item : branch.getMenuItemsList()) {
                String availability = item.getAvailablity() ? "Available" : "Unavailable";
                System.out.printf("%-5s %-20s $%-10.2f %-15s %-30s %-10s%n", index, item.getName(), item.getPrice(), item.getCategory(), item.getDescription(), availability);
                index++;
            }
        } else {
            System.out.println("Branch " + branchName + " not found.");
        }
    }

    public static void editMenuItem(String branchName) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);

        if (branch != null) {
            printMenuListTable(branchName);
            int selection;
            do {
                System.out.println("Select the item to edit (Enter the corresponding number): ");
                selection = ScannerCheck.verifySelection(1, branch.getMenuItemsList().size());
            } while (selection <= 0 || selection > branch.getMenuItemsList().size());

            MenuItem selectedMenuItem = branch.getMenuItemsList().get(selection - 1);

            System.out.println("\nChoose an option to edit:");
            System.out.println("1. Edit item name");
            System.out.println("2. Edit item price");
            System.out.println("3. Edit item category");
            System.out.println("4. Edit item availability");
            System.out.println("5. Edit item description");
            System.out.println("6. Quit");

            int editOption = ScannerCheck.verifySelection(1, 5);

            switch (editOption) {
                case 1:
                    System.out.println("\nEnter new item name: ");
                    String newName = ScannerCheck.verifyString();
                    selectedMenuItem.setName(newName);
                    break;
                case 2:
                    System.out.println("\nEnter new item price");
                    double newPrice = ScannerCheck.verifyDouble();
                    selectedMenuItem.setPrice(newPrice);
                    break;
                case 3:
                    System.out.println("\nEnter new item category");
                    System.out.println("1. Burger");
                    System.out.println("2. Side");
                    System.out.println("3. Set meal");
                    System.out.println("4. Drink");
                    System.out.println("5. Quit");
                    int choice1 = ScannerCheck.verifySelection(1,4);
                    String newCategory;

                    if (choice1 == 1){
                        newCategory = "burger";
                    } else if (choice1 == 2){
                        newCategory = "side";
                    } else if (choice1 == 3){
                        newCategory = "set meal";
                    } else if (choice1 == 4) {
                        newCategory = "drink";
                    } else break;

                    selectedMenuItem.setCategory(newCategory);
                    break;
                case 4:
                    System.out.println("\nSelect item availability");
                    System.out.println("1. Available");
                    System.out.println("2. Unavailable");
                    System.out.println("3. Quit");
                    int choice2 = ScannerCheck.verifySelection(1, 3);
                    boolean newAvailability;
                    if (choice2 == 1){
                        newAvailability = true;
                    } else if (choice2 == 2){
                        newAvailability = false;
                    } else break;
                    
                    selectedMenuItem.setAvailability(newAvailability);
                    break;
                case 5:
                    System.out.println("Enter new description: ");
                    String desciption = ScannerCheck.verifyString();
                    selectedMenuItem.setDescription(desciption);
                    break;

                case 6: 
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println("Item " + selectedMenuItem.getName() + " has been updated.");
        } else {
            System.out.println("Branch " + branchName + " not found.");
        }
    }

}
