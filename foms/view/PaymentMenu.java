package foms.view;

import foms.controller.OrdersController;
// import foms.controller.BranchController;
import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.view.EditOrderMenu;

import java.util.HashMap;
import java.util.Map;

import foms.models.Branch;
import foms.models.MenuItem;

public class PaymentMenu {
    public static boolean displayPaymentMenu(Branch branchSelected, Order newOrder) {
        int selection;

        do {
            System.out.println("\n--- Proceed to Check Out ---");
            System.out.println("Select your payment method:");
            for (int i=0; i<branchSelected.getPaymentList().size(); i++) {
                System.out.println((i+1) + ". " + branchSelected.getPaymentList().get(i).getName());
            }
            
            System.out.println((branchSelected.getPaymentList().size()+1) + ". Edit Order");
            System.out.println((branchSelected.getPaymentList().size()+2) + ". Change Dining Preference");
            System.out.println((branchSelected.getPaymentList().size()+3) + ". Cancel Order");

            selection = ScannerCheck.verifySelection(1, branchSelected.getPaymentList().size()+3);

            if (selection == (branchSelected.getPaymentList().size()+1)) {
                boolean continueOrdering = EditOrderMenu.displayEditOrderMenu(branchSelected, newOrder);
                if (continueOrdering) {
                    if (MakeOrderMenu.displayMakeOrderMenu(branchSelected, newOrder)) {
                        continue;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    continue;
                }
            }
            else if (selection == (branchSelected.getPaymentList().size()+2)) {
                boolean isContinuePayment = MakeOrderMenu.displayDiningPreference(newOrder);

                if (isContinuePayment) {
                    continue;
                }
                else {
                    return false;
                }
            }
            else if (selection == (branchSelected.getPaymentList().size()+3)) {
                System.out.println("\nAre you sure you want to cancel your order?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int isCancelOrder = ScannerCheck.verifyInt();
                if (isCancelOrder == 1) {
                    System.out.println("Order has been cancelled. ");
                    return false;
                }
                else {
                    continue;
                }
            }
            else {
                System.out.printf("%nTotal: %.2f%n", OrdersController.calculateTotal(newOrder));
                System.out.println("Enter 'CONTINUE' after payment: ");
                String verifyPaymentSuccessful = ScannerCheck.verifyString().toUpperCase();
                if (verifyPaymentSuccessful.equals("CONTINUE")) {
                    System.out.println("Payment Successful!");
                    return true;
                }
            }

            System.out.println("Payment Failed");

        } while (true);
    }

    public static void printReceipt(Order newOrder) {
        newOrder.setTotal(OrdersController.calculateTotal(newOrder));

        System.out.println("\n--- Recipt ---");
        System.out.println("Branch: "+ newOrder.getBranch());
        System.out.println("Order ID: " + newOrder.getOrderId());
        System.out.println("============================================");
        System.out.printf("%-20s %-10s %-10s%n", "Name", "Qty", "Price");
        for (HashMap<MenuItem, Integer> itemMap : newOrder.getItems()) {
            for (Map.Entry<MenuItem, Integer> entry : itemMap.entrySet()) {
                MenuItem item = entry.getKey();
                Integer quantity = entry.getValue();

                System.out.printf("%-20s %-10d %-10.2f%n", item.getName(), quantity, (item.getPrice()*quantity));
            }
        }
        System.out.println("============================================");
        if (newOrder.getIsTakeAway()) {
            System.out.printf("%-30s %-20s%n", "Take Away Fee", "$0.5");
        }
        System.out.printf("%-30s $%-20.2f%n", "Total", newOrder.getTotal());
    }
}
