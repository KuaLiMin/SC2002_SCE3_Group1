package foms.view;

// import foms.controller.OrdersController;
// import foms.controller.BranchController;
import foms.models.Order;
import foms.tools.ScannerCheck;
import foms.models.Branch;

public class PaymentMenu {
    public static boolean checkOut(Branch branchSelected, Order newOrder) {
        int selection;

        do {
            System.out.println("------Proceed to Check Out------");
            System.out.println("Select your payment method:");
            for (int i=0; i<branchSelected.getPaymentList().size(); i++) {
                System.out.println((i+1) + ". " + branchSelected.getPaymentList().get(i));
            }
            
            System.out.println((branchSelected.getPaymentList().size()+1) + ". Cancel Order");

            selection = ScannerCheck.verifySelection(1, branchSelected.getPaymentList().size()+1);

            if (selection == (branchSelected.getPaymentList().size()+1)) {
                System.out.println("Are you sure you want to cancel your order?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int isCancelOrder = ScannerCheck.verifyInt();
                if (isCancelOrder == 1) {
                    return false;
                }
            }
            else {
                System.out.printf("Total: %.2f%n", newOrder.getTotal());
                System.out.println("Enter 'CONTINUE' after payment: ");
                String verifyPaymentSuccessful = ScannerCheck.verifyString().toUpperCase();
                if (verifyPaymentSuccessful.equals("CONTINUE")) {
                    return true;
                }
            }

        } while (true);
    }
}
