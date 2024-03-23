package foms.view;

import foms.tools.ScannerCheck;

public class EmployeeMenu {
        public static void displayEmployeeMenu() {

            // Employee Login
            System.out.println("Employee Login");
            System.out.println("Enter your Employee ID: ");
            int empID = ScannerCheck.verifyInt();
            System.out.println("Enter your password: ");
            String password = ScannerCheck.verifyString();

        }
}
