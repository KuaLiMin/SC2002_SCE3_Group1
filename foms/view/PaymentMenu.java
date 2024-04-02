package foms.view;

// import foms.controller.OrdersController;
// import foms.controller.BranchController;
import foms.models.Order;
import foms.tools.ScannerCheck;

import java.util.HashMap;
import java.util.Map;

import foms.models.Branch;
import foms.models.MenuItem;

public class PaymentMenu {
    public static boolean checkOut(Branch branchSelected, Order newOrder) {
        int selection;

        do {
            System.out.println("--- Proceed to Check Out ---");
            System.out.println("Select your payment method:");
            for (int i=0; i<branchSelected.getPaymentList().size(); i++) {
                System.out.println((i+1) + ". " + branchSelected.getPaymentList().get(i).getName());
            }
            
            System.out.println((branchSelected.getPaymentList().size()+1) + ". Cancel Order\n");

            selection = ScannerCheck.verifySelection(1, branchSelected.getPaymentList().size()+1);

            if (selection == (branchSelected.getPaymentList().size()+1)) {
                System.out.println("Are you sure you want to cancel your order?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int isCancelOrder = ScannerCheck.verifyInt();
                if (isCancelOrder == 1) {
                    System.out.println("Order has been cancelled. \n");
                    return false;
                }
            }
            else {
                System.out.printf("Total: %.2f%n", newOrder.getTotal());
                System.out.println("Enter 'CONTINUE' after payment: ");
                String verifyPaymentSuccessful = ScannerCheck.verifyString().toUpperCase();
                if (verifyPaymentSuccessful.equals("CONTINUE")) {
                    System.out.println("Payment Successful!\n");
                    return true;
                }
            }

            System.out.println("Payment Failed\n");

        } while (true);
    }

    public static void printReceipt(Order newOrder) {
        System.out.println("--- Recipt ---");
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
