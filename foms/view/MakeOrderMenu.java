package foms.view;

import foms.controller.OrdersController;
import foms.models.Branch;
import foms.models.MenuItem;
import foms.models.Order;
import foms.tools.ScannerCheck;

import java.util.ArrayList;

public class MakeOrderMenu {
    public static boolean displayDiningPreference(Order newOrder) {
        int selection;
        
        do {
            System.out.println("\n--- Place Order ---");
            System.out.println("Select Dine In / Take Away: ");
            System.out.println("1. Dine In");
            System.out.println("2. Take Away (+ 0.5)");
            System.out.println("3. Exit");

            selection = ScannerCheck.verifySelection(1, 3);

            switch (selection) {
                case 1:
                    newOrder.setIsTakeAway(false);
                    return true;
                case 2:
                    newOrder.setIsTakeAway(true);
                    return true;
                case 3:
                    break;
            }
        } while (selection < 1 || selection > 3);

        return false;
    }

    public static boolean displayMakeOrderMenu(Branch branchSelected, Order newOrder) {
        ArrayList<MenuItem> menuItemsList = branchSelected.getMenuItemsList();
        int selection;
        int maxQuantityOfMenuitem = 100;

        do {
            System.out.println("\n--- Order Menu ---");
            System.out.printf("%-5s %-20s %-10s %-15s %-20s%n", "Index", "Name", "Price", "Category", "Description");
            System.out.println("-------------------------------------------------------------------------------------------");
            
            for (int i = 0; i < menuItemsList.size(); i++) {
                MenuItem menuItem = menuItemsList.get(i);
                System.out.printf("%-5s %-20s $%-10.2f %-15s %-20s%n", (i + 1), menuItem.getName(), menuItem.getPrice(), menuItem.getCategory(), menuItem.getDescription());

            }

            System.out.println((menuItemsList.size() + 1) + ". Place Order");
            System.out.println((menuItemsList.size() + 2) + ". Edit Order");
            System.out.println((menuItemsList.size() + 3) + ". Change Dining Preference");
            System.out.println((menuItemsList.size() + 4) + ". Special request");
            System.out.println((menuItemsList.size() + 5) + ". Cancel Order");
            System.out.println("\nSelect your choice: ");

            selection = ScannerCheck.verifySelection(1, (menuItemsList.size() + 5));
            
            //if no order placed then do not go for place order, edit orders etc
            if (selection>menuItemsList.size() && selection<=menuItemsList.size() + 5){
                if (newOrder.getItems().isEmpty()) {
                    System.out.println("No orders have been placed. Please make an order first.");
                    return false;
                
                }
            }   

            if (selection == menuItemsList.size() + 1) {
                return true;
            }

            if (selection == menuItemsList.size() + 2) {
                boolean continueOrdering = EditOrderMenu.displayEditOrderMenu(branchSelected, newOrder);
                if (continueOrdering) {
                    continue;
                }
                else {
                    return true;
                }
            }

            if (selection == menuItemsList.size() + 3) {
                boolean isContinueOrder = displayDiningPreference(newOrder);
                if (isContinueOrder) {
                    continue;
                }
                else {
                    return false;
                }
            }

            if (selection == menuItemsList.size() + 4) {
                // Check if there are existing special requests
                if (!newOrder.getRequest().isEmpty()) {
                    System.out.println("Existing special request: " + newOrder.getRequest());
                    System.out.println("Do you want to overwrite the existing special request? (Y/N)");
                    String overwriteRequest = ScannerCheck.verifyString().toUpperCase();
                    
                    if (overwriteRequest.equals("N")) {
                        System.out.println("Special request not changed.");
                        continue;
                    }
                }
            
                boolean isRequestMade = EditOrderMenu.makeSpecialRequest(branchSelected, newOrder);
                if (isRequestMade) {
                    System.out.println("Special request added successfully.");
                } else {
                    System.out.println("Failed to add special request.");
                }
            
                continue;
            }

            if (selection == menuItemsList.size() + 5) {
                return false;
            }

            System.out.println("\nInsert the quantity: ");
            int quantity = ScannerCheck.verifySelection(1, maxQuantityOfMenuitem);

            MenuItem selectedItem = menuItemsList.get(selection - 1);

            if (OrdersController.addItemToCart(selectedItem, quantity, newOrder)) {
                System.out.println("\nItem is successfully added. ");
            }
            else {
                System.out.println("\nFailed to add item. ");
            }
            
        } while (selection > 0 && selection <= (menuItemsList.size() + 5));

        return false;
    }
}
