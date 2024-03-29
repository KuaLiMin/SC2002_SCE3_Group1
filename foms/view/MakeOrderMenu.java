package foms.view;

import foms.controller.OrdersController;
import foms.models.Branch;
import foms.tools.ScannerCheck;
import foms.models.MenuItem;
import foms.models.Order;

import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Random;

public class MakeOrderMenu {
    private static final int LENGTH = 3;
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUZWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static Branch selectBranch(ArrayList<Branch> branchList) {
        int selection;

        do {
            System.out.println("Please select your current branch: ");
            for (int i=0; i<branchList.size(); i++) {
                System.out.println(i + ". " + branchList.get(i));
            }
            selection = ScannerCheck.verifySelection(1, branchList.size());
        } while (selection<=0 || selection>branchList.size());

        return branchList.get(selection);
    }

    public static boolean placeOrder(Branch branch, Order newOrder) {
        ArrayList<MenuItem> menuItemsList = branch.getMenuItemsList();
        int selection;

        do {
            for (int i=0; i<menuItemsList.size(); i++) {
                System.out.println((i+1) + ". " + menuItemsList.get(i).getName());
                System.out.println("   Price: " + menuItemsList.get(i).getPrice());
                System.out.println("   Category: " + menuItemsList.get(i).getCategory());
            }
    
            System.out.println((menuItemsList.size()+1) + ". Place Order");
            System.out.println((menuItemsList.size()+2) + ". Cancel Order");
            
            selection = ScannerCheck.verifySelection(1, (menuItemsList.size()+2));

            if (selection == menuItemsList.size()+1){
                break;
            }
            if (selection == menuItemsList.size()+2){
                return false;
            }

            newOrder.getItems().add(menuItemsList.get(selection-1));
    
        } while (selection>0 && selection<(menuItemsList.size()+2));

        return true;
    }

    public static String createOrderId() {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new SecureRandom();

        for (int i=0; i<LENGTH; i++) {
            int randomIndex = random.nextInt(CHAR_SET.length());
            sb.append(CHAR_SET.charAt(randomIndex));
        }

        return sb.toString();
    }

}
