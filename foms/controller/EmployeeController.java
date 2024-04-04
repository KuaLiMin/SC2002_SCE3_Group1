package foms.controller;
import foms.models.Branch;
import foms.models.Employee;
import foms.fileio.FileIO;
import java.util.ArrayList;


public class EmployeeController {
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();

    public static void displayStaffList(String branchToDisplay){
        System.out.println("Staff in " + branchToDisplay +" :");

        for (Employee employee : employeeList){
            if(employee.getBranch() == null){
                break;
            } else if (employee.getBranch().equals(branchToDisplay)){
                System.out.println("Staff name: " + employee.getName() + ", role: " + employee.getRole());
            }
        }
    }

    public void addEmployee(Employee employee) {
        // need to ensure the employee does not already exist
        // and that the employee details are valid before adding
        employeeList.add(employee);
    }


    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
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

}