package foms.tools;

import foms.models.Employee;
import foms.fileio.FileIO;

import java.util.ArrayList;

/**
 * A utility class for checking employee credentials and handling login and password change functionality.
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class EmployeeCredCheck {
    
    /**
     * Verifies the username and password against the stored list of employees.
     * If the credentials match an existing employee, that employee object is returned.
     *
     * @param username the username to be verified
     * @param password the password to be verified
     * @return Employee object if credentials are valid, otherwise null
     */
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

    /**
     * Handles the process of logging in an employee by prompting for username and password.
     * It continues to prompt until valid credentials are entered or the user chooses to exit.
     *
     * @return the Employee object if authenticated, otherwise null if the user exits the login process
     */
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

    /**
     * Allows an employee to change their password after validating their current credentials.
     * It prompts for the current password and then for the new password, which needs to be entered twice
     * for confirmation. If the new passwords match, the change is processed.
     */
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

    /**
     * The default constructor for the EmployeeCredCheck class.
     * This constructor initializes the class with default values.
     * 
     * Note: This constructor is provided implicitly by Java when no other constructors are defined explicitly.
     */
    public EmployeeCredCheck() {}
}

