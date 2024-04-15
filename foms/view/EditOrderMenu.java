package foms.view;

import foms.models.Branch;
import foms.models.MenuItem;
import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.controller.OrdersController;

import java.util.HashMap;
import java.util.Map;
/**
 * This class provides the user interface for editing an order within the Food Order Management System (FOMS).
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class EditOrderMenu {
    
    /**
     * Displays the menu for editing the details of an existing order. The user can change the quantity of the
     * items in their order or remove them. The total cost is recalculated after each edit.
     * The method returns true if the user continues the ordering process or the cart becomes empty,
     * and false if the user chooses to proceed to payment.
     *
     * @param branchSelected The branch where the order is placed.
     * @param newOrder The order that is being edited.
     * @return boolean indicating the outcome of the edit process.
     */
    public static boolean displayEditOrderMenu(Branch branchSelected, Order newOrder) {
        if (!OrdersController.checkOrderExistence(newOrder.getOrderId())) {
            System.out.println("\nOrder doesn't exist. ");
            return false;
        }

        int i;
        int selection;
        int choice;
        int qty;
        
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
            qty = ScannerCheck.verifySelection(0, 100);

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
    
    /**
     * Allows the user to add a special request to their order. The method checks if the branch and order are
     * not null, prompts the user to type out their special request, and updates the order with this request.
     *
     * @param branchSelected The branch where the order is placed.
     * @param newOrder The order to which the special request is added.
     * @return boolean indicating if the special request was successfully added.
     */
    public static boolean makeSpecialRequest(Branch branchSelected, Order newOrder) {
        if (branchSelected != null && newOrder != null) {
            System.out.println("Please type out your special request: ");
            String specialRequest = ScannerCheck.verifyString();
            newOrder.setRequest(specialRequest);
            return true; 
        }
        
        return false;
    }
}
