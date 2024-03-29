import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import foms.models.Order;
import foms.models.Staff;
import foms.tools.ScannerCheck;

public class Main {

    private static Staff[] staffMembers = {
            new Staff("staff1", "password", 'S'),
            new Staff("manager1", "password", 'M'),
            new Staff("admin1", "password", 'A')
    };
    private static Order[] orders = {
            new Order(1, "Item1", "New"),
            new Order(2, "Item2", "New"),
            new Order(3, "Item3", "Processing")
    };
    public static void main(String[] args) {
        Staff loggedInStaff = new Staff("", "", 'S');
        // Staff actions menu
        char choice;
        do {
            System.out.println("\nStaff actions:");
            System.out.println("1. Display new orders");
            System.out.println("2. Process order");
            System.out.println("Enter your choice:");
            choice = (char) ScannerCheck.verifyInt();

            switch (choice) {
                case '1':
                    loggedInStaff.displayNewOrders(orders);
                    break;
                case '2':
                    System.out.println("Enter order ID to process:");
                    int orderIDToProcess = ScannerCheck.verifyInt();
                    for (Order order : orders) {
                        if (order.getOrderID() == orderIDToProcess) {
                            loggedInStaff.processOrder(order);
                            break;
                        }
                    }
                    break;
                default:
            }
        } while (true);
    }
}
