package foms.view;

import foms.controller.OrdersController;
import foms.models.Branch;
import foms.models.Order;
import foms.models.MenuItem;
import foms.tools.ScannerCheck;

import java.util.HashMap;
import java.util.Map;
/**
 * This class provides the user interface for the payment process within the Food Order Management System (FOMS).
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class PaymentMenu {
    /**
     * Displays the payment menu where the user can select a payment method, edit their order,
     * change their dining preference, or cancel the order. It loops until a successful payment is made,
     * the order is edited, the dining preference is changed, or the order is cancelled.
     *
     * @param branchSelected The branch where the order is placed.
     * @param newOrder The order that is being processed for payment.
     * @return boolean indicating if the payment was successful or if the order has been cancelled.
     */
    public static boolean displayPaymentMenu(Branch branchSelected, Order newOrder) {
        int selection;

        do {
            System.out.println("\n--- Proceed to Check Out ---");
            System.out.println("Select your payment method:");
            for (int i=0; i<Branch.getPaymentList().size(); i++) {
                System.out.println((i+1) + ". " + Branch.getPaymentList().get(i).getName());
            }
            
            System.out.println((Branch.getPaymentList().size()+1) + ". Edit Order");
            System.out.println((Branch.getPaymentList().size()+2) + ". Change Dining Preference");
            System.out.println((Branch.getPaymentList().size()+3) + ". Cancel Order");

            selection = ScannerCheck.verifySelection(1, Branch.getPaymentList().size()+3);

            if (selection == (Branch.getPaymentList().size()+1)) {
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
            else if (selection == (Branch.getPaymentList().size()+2)) {
                boolean isContinuePayment = MakeOrderMenu.displayDiningPreference(newOrder);

                if (isContinuePayment) {
                    continue;
                }
                else {
                    return false;
                }
            }
            else if (selection == (Branch.getPaymentList().size()+3)) {
                System.out.println("\nAre you sure you want to cancel your order?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int isCancelOrder = ScannerCheck.verifySelection(1,2);
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

    /**
     * Prints a receipt for the given order. It includes details such as the branch, order ID, items ordered,
     * special requests (if any), and the total amount including any take away fee if applicable.
     *
     * @param newOrder The order for which the receipt is printed.
     */
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
        if (newOrder.getRequest()!=null){
            System.out.println("Special request: "+ newOrder.getRequest());
        } else {
            System.out.println("No special request");
        }
        System.out.println("============================================");
        if (newOrder.getIsTakeAway()) {
            System.out.printf("%-30s %-20s%n", "Take Away Fee", "$0.5");
        }
        System.out.printf("%-30s $%-20.2f%n", "Total", newOrder.getTotal());
    }
}
