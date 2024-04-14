package foms.view;

import foms.models.Customer;
import foms.tools.ScannerCheck;

/**
 * The MainMenu class represents the main menu of the Food Ordering Management System (FOMS).
 * It provides options for customers and employees to interact with the system.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class MainMenu {

    /**
     * Displays the main menu and handles user selection.
     */
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
