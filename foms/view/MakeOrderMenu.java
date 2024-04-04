package foms.view;

import foms.controller.OrdersController;
// import foms.controller.OrdersController;
import foms.models.Branch;
import foms.tools.ScannerCheck;
import foms.models.MenuItem;
import foms.models.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Map;

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
        int maxQuantityOfMenuitem = newOrder.MAX_QUANTITY_OF_MENUITEM;

        do {
            System.out.println("\n--- Order Menu ---");
            for (int i = 0; i < menuItemsList.size(); i++) {
                System.out.println((i + 1) + ". " + menuItemsList.get(i).getName());
                System.out.println("   Price: " + menuItemsList.get(i).getPrice());
                System.out.println("   Category: " + menuItemsList.get(i).getCategory());
            }

            System.out.println((menuItemsList.size() + 1) + ". Place Order");
            System.out.println((menuItemsList.size() + 2) + ". Edit Order");
            System.out.println((menuItemsList.size() + 3) + ". Change Dining Preference");
            System.out.println((menuItemsList.size() + 4) + ". Cancel Order");
            System.out.println("\nSelect your choice: ");

            selection = ScannerCheck.verifySelection(1, (menuItemsList.size() + 4));

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
            
        } while (selection > 0 && selection <= (menuItemsList.size() + 3));

        return false;
    }
}
