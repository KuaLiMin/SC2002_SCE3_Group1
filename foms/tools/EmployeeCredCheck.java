package foms.tools;

import foms.models.Employee;
import foms.view.StaffMenu;
import foms.view.ManagerMenu;
import foms.view.AdminMenu;
import foms.fileio.FileIO;
import java.util.ArrayList;

public class EmployeeCredCheck {
    public static Employee checkCredentials(String username, String password) {
        ArrayList<Employee> employeeList = FileIO.getEmployeeList(); // Retrieve the list of employees

        for (Employee employee : employeeList) {
            if (employee.getUserid().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null; 
    }

    public static void login() {
        Employee authenticatedEmployee = null;
        while (true) {
            System.out.print("Enter username: ");
            String username = ScannerCheck.verifyString();
            System.out.print("Enter password: ");
            String password = ScannerCheck.verifyString();
            authenticatedEmployee = EmployeeCredCheck.checkCredentials(username, password);
    
            if (authenticatedEmployee != null) {
                System.out.println("Login successful.");
                String role = authenticatedEmployee.getRole(); 
                if ("S".equals(role)){
                    StaffMenu.displayStaffMenu();
                } else if ("M".equals(role)){
                    ManagerMenu.displayManagerMenu(); //how to correct this?
                } else if ("A".equals(role)){
                    AdminMenu.displayAdminMenu();
                }
            } else {
                System.out.println("Invalid credentials. To try again press 1, to exit press 2:");
                int choice = ScannerCheck.verifyInt(); 
                if (choice != 1) {
                    System.out.println("Exiting login process.");
                    break;
                }
            }
        }
    }
}