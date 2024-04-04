package foms.view;

import static foms.controller.MenuController.removeItemFromMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import foms.models.MenuItem;
import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.controller.OrdersController;
import foms.models.Branch;

public class EditOrderMenu {
    public static boolean displayEditOrderMenu(Branch branchSelected, Order newOrder) {
        if (!OrdersController.checkOrderExistence(newOrder.getOrderId())) {
            System.out.println("\nOrder doesn't exist. ");
            return false;
        }

        int i;
        int selection;
        int choice;
        int qty;
        int maxQuantityOfMenuitem = newOrder.MAX_QUANTITY_OF_MENUITEM;
        
        do {
            i = 1;    

            System.out.println("\n--- Edit Menu ---");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "NO.", "Name", "Qty", "Price");
            for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
                for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                    MenuItem item = entry.getKey();
                    Integer quantity = entry.getValue();
                    System.out.printf("%-10d %-20s %-10d %-10.2f%n", i, item.getName(), quantity, (item.getPrice() * quantity));
                    i++;
                }
            }
            System.out.println("==================================================");
            if (newOrder.getIsTakeAway()) {
                System.out.printf("%-40s %-10s%n", "Take Away Fee", "$0.5");
            }
            System.out.printf("%-40s $%-10.2f%n", "Total", OrdersController.calculateTotal(newOrder));

            // edit menuItemList
            System.out.println("\nSelect the item to edit: ");
            selection = ScannerCheck.verifySelection(1, i-1);

            System.out.println("\nEnter the quantity (0 to remove): ");
            qty = ScannerCheck.verifySelection(0, maxQuantityOfMenuitem);

            if(OrdersController.editItemInCart(qty, selection, newOrder)) {
                if (newOrder.getItems().isEmpty()) {
                    System.out.println("\nAll items have been removed from the cart. Your cart is empty. ");
                    return true;
                }
                
                newOrder.setTotal(OrdersController.calculateTotal(newOrder));
                System.out.println("\nSuccessfully edit order. ");
                System.out.println("\nAfter edit:");

                i = 1;

                System.out.printf("%-10s %-20s %-10s %-10s%n", "NO.", "Name", "Qty", "Price");
                for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
                    for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                        MenuItem item = entry.getKey();
                        Integer quantity = entry.getValue();
                        System.out.printf("%-10d %-20s %-10d %-10.2f%n", i, item.getName(), quantity, (item.getPrice() * quantity));
                        i++;
                    }
                }
                System.out.println("==================================================");
                if (newOrder.getIsTakeAway()) {
                    System.out.printf("%-40s %-10s%n", "Take Away Fee", "$0.5");
                }
                System.out.printf("%-40s $%-10.2f%n", "Total", OrdersController.calculateTotal(newOrder));
            }
            else {
                System.out.println("\nFailed edit order. ");
            }

            System.out.println("\nContinue to edit order: ");
            System.out.println("1. Yes");
            System.out.println("2. No");

            choice = ScannerCheck.verifySelection(1, 2);

        } while (choice == 1);

        System.out.println("\nDo you want to: ");
        System.out.println("1. Continue to order");
        System.out.println("2. Proceed to payment");

        choice = ScannerCheck.verifySelection(1, 2);

        if (choice == 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
