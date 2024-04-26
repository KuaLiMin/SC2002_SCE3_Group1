package foms.controller;

import foms.models.Branch;
import foms.models.MenuItem;
import foms.fileio.FileIO;
import foms.tools.ScannerCheck;

import java.util.ArrayList;

/**
 * The MenuController class manages menu items within different branches of a food management system.
 * It allows adding, removing, and editing menu items for each branch.
 * Used by manager to edit the menu for the branch that he belongs to
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */

public class MenuController {
    /**
     * A list of branches loaded from a data source.
     */
    protected static  ArrayList<Branch> branchList = FileIO.getBranchList();

    /**
     * Adds a new item to the menu of a specified branch.
     *
     * @param itemName     The name of the menu item.
     * @param itemPrice    The price of the menu item.
     * @param branchName   The name of the branch to add the menu item to.
     * @param category     The category of the menu item.
     * @param description  The description of the menu item.
     * @param availability The availability of the menu item.
     * @return true if the item was added successfully, false if the item already exists or the branch was not found.
     */
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
        else{
            System.err.println("Branch not found");
        }
        return false; // Branch not found
    }

    /**
     * Initiates the process to remove a menu item from a branch's menu.
     * It prints the menu and prompts the user to select an item to remove.
     * It utilise removeItemFromMenu to remove an item 
     * @param branchName The name of the branch from which to remove a menu item.
     */
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

    /**
     * Adds a new item to the menu after collecting item details from the user.
     * Details include: name, price, category, description and availability 
     * @param branch The name of the branch to which the menu item will be added.
     */
    public static void addItemToMenu(String branch){
        String category;
        String itemName;
        String description;
        boolean availability;
        Branch inbranch = branchList.stream()
                .filter(b -> b.getName().equals(branch))
                .findFirst()
                .orElse(null);

        System.out.println("\nEnter item name: ");
        itemName = ScannerCheck.verifyString();
        for (MenuItem item : inbranch.getMenuItemsList()) {
            if (item.getName().equals(itemName)) {
                System.out.println("\nItem is already in the menu");
                return; // Item already exists
            }
        }
        System.out.println("\nEnter item price: ");
        Double itemPrice = ScannerCheck.verifyDouble();

        System.out.println("\nEnter item category");
        System.out.println("1. Burger");
        System.out.println("2. Side");
        System.out.println("3. Set meal");
        System.out.println("4. Drink");
        System.out.println("5. New category");
        int choice1 = ScannerCheck.verifySelection(1,5);
        if (choice1 == 1){
            category = "Burger";
        } else if (choice1 == 2){
            category = "Side";
        } else if (choice1 == 3){
            category = "Set meal";
        } else if(choice1 ==4){
            category = "Drink";
        } else {
            System.out.println("Enter new category:");
            category = ScannerCheck.verifyString();
        }
        
        System.out.println("\nEnter desciption:");
        description = ScannerCheck.verifyString();

        System.out.println("\nIs item available now?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        choice1 = ScannerCheck.verifySelection(1,2);
        if (choice1 == 1){
            availability = true;
        } else availability = false;

        if (addItemToMenuList(itemName, itemPrice, branch, category, description, availability) == true){
            System.out.println("Item " + itemName + "added to "+ branch +" menu successfully");
        } else System.out.println("\nItem failed to add to menu");
    }

    /**
     * Removes a menu item from a branch's menu based on the item name.
     *
     * @param itemName   The name of the menu item to be removed.
     * @param branchName The name of the branch from which the menu item will be removed.
     * @return true if the item was removed successfully, false if the item was not found or the branch was not found.
     */
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

    /**
     * Prints a tabular list of menu items for a specific branch.
     *
     * @param branchName The name of the branch whose menu items will be printed.
     */
    public static void printMenuListTable(String branchName) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);
    
        if (branch != null) {
            System.out.println("\nExisting menu items in " + branchName + ":");
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-10s %-15s %-30s %-10s%n", "Index", "Name", "Price", "Category", "Availability", "Description");
            System.out.println("------------------------------------------------------------------------------------------------------");
    
            int index = 0;
            for (MenuItem item : branch.getMenuItemsList()) {
                String availability = item.getAvailability() ? "Available" : "Unavailable";
                System.out.printf("%-5s %-20s $%-10.2f %-15s %-30s %-10s%n", index + 1, item.getName(), item.getPrice(), item.getCategory(), availability, item.getDescription());
                index++;
            }
        } else {
            System.out.println("Branch " + branchName + " not found.");
        }
    }
    /**
     * Initiates the process to edit a menu item from a branch's menu.
     * It prints the menu and prompts the user to select an item to edit and then the aspect of the item to edit.
     *
     * @param branchName The name of the branch where the menu item will be edited.
     */
    public static void editMenuItem(String branchName) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);

        if (branch != null) {
            printMenuListTable(branchName);
            int selection;
            selection = ScannerCheck.verifySelection(1, branch.getMenuItemsList().size());

            MenuItem selectedMenuItem = branch.getMenuItemsList().get(selection - 1);

            System.out.println("\nChoose an option to edit:");
            System.out.println("1. Edit item name");
            System.out.println("2. Edit item price");
            System.out.println("3. Edit item category");
            System.out.println("4. Edit item availability");
            System.out.println("5. Edit item description");
            System.out.println("6. Quit");

            int editOption = ScannerCheck.verifySelection(1, 6);

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

    /**
     * The default constructor for the MenuController class.
     * This constructor initializes the class with default values.
     * 
     * Note: This constructor is provided implicitly by Java when no other constructors are defined explicitly.
     */
    public MenuController() {}
}
