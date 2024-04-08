package foms.tools;

import foms.models.Employee;
import foms.fileio.FileIO;
import java.util.ArrayList;

public class EmployeeCredCheck {
    public static Employee checkCredentials(String username, String password) {
        ArrayList<Employee> employeeList = FileIO.getEmployeeList(); // Retrieve the list of employees

        for (Employee employee : employeeList) {
            // print employee attr
            if (employee.getUserId().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null; 
    }

    public Employee login() {
        Employee authenticatedEmployee = null;
        while (true) {
            System.out.print("Enter username: ");
            String username = ScannerCheck.verifyString();
            System.out.print("Enter password: ");
            String password = ScannerCheck.verifyString();
            authenticatedEmployee = EmployeeCredCheck.checkCredentials(username, password);
            
            if (authenticatedEmployee != null) {
                System.out.println("Login successful.");
                return authenticatedEmployee;

            } else {
                System.out.println("Invalid credentials.");
                int choice;
                do {
                    System.out.println("To try again press 1, to exit press 2:");
                    choice = ScannerCheck.verifySelection(1,2);
                    if (choice == 1) {
                        break; 
                    } else if (choice == 2) {
                        System.out.println("Exiting Login process.");
                        return null ; 
                    } else {
                        System.out.println("Invalid choice. Please choose between 1 and 2.");
                    }
                } while (choice != 1 && choice != 2);
            }
            
        }
    }


    public static void changePassword() {
        Employee employee = null;
        while (true) {
            System.out.print("Enter username: ");
            String username = ScannerCheck.verifyString();
            System.out.print("Enter current password: ");
            String currentPassword = ScannerCheck.verifyString();
            employee = EmployeeCredCheck.checkCredentials(username, currentPassword);
    
            if (employee != null) {
                String newPassword = null;
                String confirmPassword = null;
    
                do {
                    System.out.print("Enter new password: ");
                    newPassword = ScannerCheck.verifyString();
                    System.out.print("Confirm new password: ");
                    confirmPassword = ScannerCheck.verifyString();
    
                    if (!newPassword.equals(confirmPassword)) {
                        System.out.println("Passwords do not match.");
                    } else {
                        employee.setPassword(newPassword);
                        // FileIO.saveEmployeeList(FileIO.getEmployeeList());
                        System.out.println("Password successfully changed.");
                        return; 
                    }
                } while (!newPassword.equals(confirmPassword));
            } else {
                System.out.println("Invalid credentials.");
                int choice;
                do {
                    System.out.println("To try again press 1, to exit press 2:");
                    choice = ScannerCheck.verifySelection(1,2);
                    if (choice == 1) {
                        break; 
                    } else if (choice == 2) {
                        System.out.println("Exiting change password process.");
                        return; 
                    } else {
                        System.out.println("Invalid choice. Please choose between 1 and 2.");
                    }
                } while (choice != 1 && choice != 2);
            }
        }
    }
}

