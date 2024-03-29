package foms.controller;

import foms.models.Employee;
import foms.models.Employee;
// import foms.models.Employee;
import foms.fileio.FileIO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeController {
    private static final ArrayList<Employee> employeeList = FileIO.getEmployeeList();

    public EmployeeController() {
        this.employeeList = FileIO.getEmployeeList();
    }


    public void addEmployee(Employee employee) {
        // need to ensure the employee does not already exist
        // and that the employee details are valid before adding
        employeeList.add(employee);
    }

    public void editEmployee(String userId, Employee updatedEmployee) {

        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getUserid().equals(userId)) {
                employeeList.set(i, updatedEmployee);
                break;
            }
        }
    }

    public void removeEmployee(String userId) {
        employeeList.removeIf(employee -> employee.getUserid().equals(userId));
    }

    public List<Employee> getEmployeeList(String branch, String role, String gender, Integer age) {
        return employeeList.stream()
                .filter(employee -> (branch == null || employee.getBranch().equals(branch)) &&
                                    (role == null || employee.getRole().equals(role)) &&
                                    (gender == null || employee.getGender().equals(gender)) &&
                                    (age == null || employee.getAge() == age))
                .collect(Collectors.toList());
    }

    public void assignManager(String userId, String branch) {
        // assign the employee with userId as a manager to the branch
        // need to chekc the quota/ratio constraint 
    }

    public void promoteToBranchManager(String userId) {
        // promote the employee with userId to a branch manager
    }

    public void transferEmployee(String userId, String newBranch) {
        // transfer the employee with userId to a new branch
    }


    public static void displayEmployeeList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayEmployeeList'");
    }
}