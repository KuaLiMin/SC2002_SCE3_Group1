package foms.view;

import foms.tools.ScannerCheck;
import foms.models.Customer;

public class MainMenu {

    public static void displayMainMenu() {


        while (true) {
            System.out.println("\nWelcome to Food Ordering Management System (FOMS)");
            System.out.println("1. I am a customer");
            System.out.println("2. I am an Employee");
            System.out.println("3. Exit");
            int selection = ScannerCheck.verifySelection(1, 3);

            if (selection == 1) {
                // Customer
                Customer customer = new Customer();
                CustomerMenu.displayCustomerMenu(customer);
            } else if (selection == 2) {
                // Employee
                EmployeeMenu.displayEmployeeMenu();
            } else {
                // Exit
                break;
            }
        }
    }
}
