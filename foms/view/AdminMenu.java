package foms.view;

import foms.controller.OrdersController;
import foms.tools.ScannerCheck;

public class AdminMenu {
    final static int MANAGE_STUFF_ACCOUNT=1;
    final static int DISPLAY_STAFF_LIST=2;
    final static int ASSIGN_MANAGERS=3;
    final static int PROMOTE_STAFF_TO__MANAGER=4;
    final static int TRANSFER_STAFF_MANAGER=5;
    final static int  MANAGE_PAYMENT_METHOD=6;
    final static int OPEN_CLOSE_BRANCH=7;

    public static void displayAdminMenu() {
        int selection = ScannerCheck.verifySelection(1, 7);
        System.out.println("Admin menu:");
        System.out.println("1.Add, edit, or remove Staff accounts");
        System.out.println("2.Display staff list (filter: branch, role, gender, age)");
        System.out.println("3.Assign managers to each branch within the quota constraint.");
        System.out.println("4.Promote a staff to a Branch manager.");
        System.out.println("5.Transfer a staff/manager among branches.");
        System.out.println("6.Add/remove payment method.");
        System.out.println("7.Open/close branch.");
        do {
            switch (selection)
            {
                case MANAGE_STUFF_ACCOUNT:

                    break;
                case DISPLAY_STAFF_LIST:
                    break;
                case ASSIGN_MANAGERS:
                    break;
                case PROMOTE_STAFF_TO__MANAGER:
                    break;
                case TRANSFER_STAFF_MANAGER:
                    break;
                case MANAGE_PAYMENT_METHOD:
                    break;
                case OPEN_CLOSE_BRANCH:
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (selection<=0 || selection>7);
    }
        }
        


