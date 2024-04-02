package foms.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import foms.models.MenuItem;
import foms.models.Order;
import foms.tools.ScannerCheck;

public class EditOrderMenu {
    public static boolean displayEditOrderMenu(Order newOrder) {
        int i;
        int selection;
        int choice;
        
        do {
            i = 1;    

            System.out.println("--- Edit Menu ---");
            System.out.printf("%-4d %-20s %-10s%n", "NO.", "Name", "Qty");
            for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
                for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                    MenuItem item = entry.getKey();
                    Integer quantity = entry.getValue();
                    System.out.printf("%-4d %-20s %-10d%n", i, item.getName(), quantity);
                    i++;
                }
            }
            System.out.println("Select the item to edit: ");
            
            selection = ScannerCheck.verifySelection(1, i);

            // edit menuItemList

            System.out.println("1. Continue to edit cart");
            System.out.println("2. Return to payment");

            // if statement to return paymentMenu

            choice = ScannerCheck.verifySelection(1, 2);

        } while (choice == 1);

        return false;
    }
}
