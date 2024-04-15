package foms.controller;

import foms.models.Branch;
import foms.models.Employee;
import foms.models.Staff;
import foms.models.Manager;
import foms.models.Admin;
import foms.enums.UserRole;
import foms.fileio.FileIO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The EmployeeController class is responsible for managing all employee-related operations
 * within the Food Ordering Management System (FOMS).
 * It allows adding, editing, removing, and transferring employees among branches.
 * 
 * @author Chen Ziyan
 * @author Kua Li Min
 * @author Charlton Siaw Qi Hen
 * @version 1.0
 * @since 2024-04-15
 */

public class EmployeeController {
    /**
     * A list of employees loaded from a data source.
     */
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();

    /**
     * A list of branches loaded from a data source.
     */
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();

    /**
     * Adds a new staff, manager or admin to a specified branch based on provided role and other attributes.
     * It checks against the branch's quota for managers or staff before adding a new employee to ensure
     * compliance with set limits.
     * 
     * @param role The role of the employee.
     * @param name The name of the employee.
     * @param gender The gender of the employee.
     * @param age The age of the employee.
     * @param userId The userId of the employee.
     * @param branchName The branch of the employee.
     * @return true if the employee is successfully added, false if the quota is reached or the employee already exists.
     */
    public static boolean addStaff(String role, String name, String gender, int age, String userId, String branchName) {
        Branch branch = BranchController.selectBranchByName(branchName);

        if (role.equals(UserRole.M.name())) {
            int managerCount = BranchController.getManagerCount(branchName);
            System.out.println("Manager count before: " + BranchController.getManagerCount(branchName));
            if (managerCount >= branch.getManagerQuota()){
                System.out.println("Manager quota reached for branch " + branchName + ". Cannot add more managers.");
                return false;
            }

            Manager manager = new Manager(role, name, gender, age, userId, branchName);
            manager.setBranch(branchName);
            boolean exists = employeeList.stream().anyMatch(e -> e.getUserId().equals(manager.getUserId()));

            if (!exists) {
                employeeList.add(manager);
                branch.setManagerCount(managerCount + 1); 
                System.out.println("manager count after: " + BranchController.getManagerCount(branchName));
                return true; 
            }

        } else if (role.equals(UserRole.S.name())){
            int staffCount = BranchController.getStaffCount(branchName);
            System.out.println("staff count before: " + BranchController.getStaffCount(branchName));

            if (staffCount >= branch.getStaffQuota()){
                System.out.println("Staff quota reached for branch " + branchName + ". Cannot add more staff.");
                return false;
            }
            Staff staff = new Staff(role, name, gender, age, userId, branchName);
            staff.setBranch(branchName);
            boolean exists = employeeList.stream().anyMatch(e -> e.getUserId().equals(staff.getUserId()));
            if (!exists) {
                employeeList.add(staff);
                branch.setStaffCount(staffCount + 1); 
                System.out.println("staff count after: " + BranchController.getStaffCount(branchName));
                return true;
            }
        } else {
            Admin admin = new Admin(role, name, gender, age, userId, branchName);
            boolean exists = employeeList.stream().anyMatch(e -> e.getUserId().equals(admin.getUserId()));

            if (!exists) {
                employeeList.add(admin);
                return true;
            }
        }

        return false;
    }

    /**
     * Edits the details of an existing employee identified by userId. This method assumes that
     * the userId provided corresponds to an existing employee.
     * 
     * @param userId The userId of the employee to be edited.
     * @param role New role to be assigned.
     * @param name New name of the employee.
     * @param gender New gender of the employee.
     * @param age New age of the employee.
     * @param userId1 New userId of the employee.
     * @param branch The branch where the employee is located.
     * @return true if the editing is successful, false otherwise.
     */
    public static boolean editStaff(String userId, String role, String name, String gender, int age, String userId1, String branch) {
        Staff staff = new Staff(role, name, gender, age, userId1, branch);
        staff.setBranch(branch);
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getUserId().equals(userId)) {
                employeeList.set(i, staff);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an employee from the system based on their userId.
     * 
     * @param userId The userId of the employee to be removed.
     * @return true if the employee is successfully removed, false otherwise.
     */
    public static boolean removeStaff(String userId) {
        boolean removed = employeeList.removeIf(staff -> staff.getUserId().equals(userId));
        return removed;
    }

    /**
     * Assigns an existing manager to a specified branch, if possible.
     *
     * @param userId The userId of the manager.
     * @param branchName The branch to assign the manager to.
     * @return true if the assignment is successful, false if not (e.g., quota full).
     */
    public static boolean assignManager(String userId, String branchName) {
        Branch branch = BranchController.selectBranchByName(branchName);
        if (branch != null) {
            int currentManagerCount = BranchController.getManagerCount(branchName);

            if (currentManagerCount < branch.getManagerQuota()) {
                for (Employee employee : employeeList) {
                    if (employee instanceof Manager){
                        Manager manager = (Manager) employee;

                        if (manager.getUserId().equals(userId) && manager.getRole() == UserRole.M) {
                            manager.setBranch(branchName);
                            branch.setManagerCount(currentManagerCount + 1);
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Promotes a staff member to a branch manager, respecting the manager quota.
     *
     * @param userId The userId of the staff member to promote.
     * @return true if the promotion is successful, false if not (e.g., quota reached or employee not found).
     */
    public static boolean promoteToBranchManager(String userId) {
        Optional<Staff> staffOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId) && emp instanceof Staff)
                .map(emp -> (Staff) emp)
                .findFirst();
    
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            int currentManagersCount = BranchController.getManagerCount(staff.getBranch());
            int maxManagersAllowed = BranchController.selectBranchByName(staff.getBranch()).getManagerQuota();
    
            if (currentManagersCount < maxManagersAllowed && staff.getBranch()!= null) {
                // Promote staff to manager
                Manager promotedManager = new Manager("M", staff.getName(), staff.getGender(), staff.getAge(),staff.getUserId(),staff.getBranch());
                employeeList.add(promotedManager); // Add the promoted manager to the employeeList
                employeeList.remove(staff);

                return true; // Promotion successful
            } else {
                System.out.println("Manager quota reached, promotion failed");
                return false;
            }
        } else{
            System.out.println("Staff not found, promotion failed ");
        }
        return false;
    }

    /**
     * Transfers an employee to a different branch.
     *
     * @param userId The userId of the employee to transfer.
     * @param newBranchName The name of the branch to transfer the employee to.
     * @return true if the transfer is successful, false if not (e.g., staff or manager quota reached).
     */
    public static boolean transferEmployee(String userId, String newBranchName) {
        Branch newBranch = BranchController.selectBranchByName(newBranchName);
        if (newBranch == null) {
            System.out.println("Branch not found.");
            return false;
        }

        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId))
                .findFirst();
        
        if (employeeOptional.isPresent()) {
            Employee emp = employeeOptional.get();

            if (emp instanceof Manager) {
                Manager manager = (Manager) emp;
                if (BranchController.getManagerCount(newBranchName) < newBranch.getManagerQuota()) {
                    manager.setBranch(newBranchName);
                    return true;
                } else {
                    System.out.println("Manager quota reached");
                    return false;
                }
            } else if (emp instanceof Staff) {
                Staff staff = (Staff) emp;
                if (BranchController.getStaffCount(newBranchName) < newBranch.getStaffQuota()) {
                    staff.setBranch(newBranchName);
                    return true; 
                }else {
                    System.out.println("Staff quota reached");
                    return false;
                }
                
            }
        }
        return false;
    }

    /**
     * Checks if a user ID already exists in the system.
     *
     * @param userId The user ID to check.
     * @return true if the user ID exists, false otherwise.
     */
    public static boolean userIdExit(String userId){
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId))
                .findFirst();
        if (employeeOptional.isPresent())
            return true;
        return false;
    }

    /**
     * Retrieves a comprehensive list of all staff members from the system, organized by branch.
     * The method first filters the global employee list to include only those who are instances of Staff.
     * It then groups them by their assigned branch, with a special category for those without an assigned branch.
     * Each group is sorted such that Managers are listed before Staff within their respective branch groups.
     *
     * @return A list of all Staff, sorted first by branch and then by role within each branch.
     */
    public static List<Staff> getAllStaffList() {
        List<Staff> staffList = employeeList.stream()
                .filter(employee -> employee instanceof Staff)
                .map(employee -> (Staff) employee)
                .collect(Collectors.toList());
    
        // Adjust the groupingBy to handle null branch names
        Map<String, List<Staff>> staffByBranchMap = staffList.stream()
                .collect(Collectors.groupingBy(staff -> {
                    String branch = staff.getBranch();
                    return (branch != null) ? branch : "No Branch";
                }));
    
        List<Staff> groupedStaffList = new ArrayList<>();
    
        // Iterate over the entries in the staffByBranchMap
        staffByBranchMap.forEach((branch, staffInBranch) -> {
            List<Staff> sortedStaff = staffInBranch.stream()
                    .sorted(Comparator.comparing(s -> s.getRole() == UserRole.M ? 0 : 1))
                    .collect(Collectors.toList());
    
            groupedStaffList.addAll(sortedStaff);
        });
    
        return groupedStaffList;
    }
    
    /**
     * Retrieves a filtered list of Staff members based on provided attributes.
     * This method allows for filtering the global list of employees to include only those who are instances of Staff,
     * and further narrows down the list based on optional criteria such as branch, role, gender, and age.
     * The method uses conditional checks to ensure each filter is applied only if a non-null (or non-zero for age) value is provided.
     *
     * @param branch the branch to filter by; if null, the branch filter is ignored.
     * @param role the user role to filter by; if null, the role filter is ignored.
     * @param gender the gender to filter by; if null, the gender filter is ignored.
     * @param age the age to filter by; if 0, the age filter is ignored.
     * @return a list of Staff filtered by the specified attributes, if they are provided.
     */
    public static List<Staff> getStaffListByAttribute(String branch, UserRole role, String gender, int age) {
        return employeeList.stream()
                .filter(employee -> employee instanceof Staff)
                .map(employee -> (Staff) employee) 
                .filter(staff -> (branch == null || staff.getBranch().equals(branch)) &&
                                (role == null || staff.getRole() == role) &&
                                (gender == null || staff.getGender().equals(gender)) &&
                                (age == 0 || staff.getAge() == age))
                .collect(Collectors.toList());
    }

    /**
     * Prints a formatted list of staff members.
     *
     * @param staffList The list of staff members to print.
     */
    public static void printStaffList(List<Staff> staffList) {
        System.out.printf("%-5s |%-10s | %-10s | %-20s | %-6s | %-5s | %-10s%n", "Index", "Role", "Branch", "Name", "Gender", "Age", "UserId");
        System.out.println("----------------------------------------------------------------------------------------------");
        int counter = 1;
        String branchName;
        for (Staff staff : staffList) {
            if (staff.getBranch() == null){
                branchName = "No Branch"; 
            } else {
                branchName = staff.getBranch();
            }
            System.out.printf("%-5s |%-10s | %-10s | %-20s | %-6s | %-5s | %-10s%n", counter++ , staff.getRoleInString(), branchName, staff.getName(), staff.getGender(), staff.getAge(), staff.getUserId());
        }
    }

    /**
     * Retrieves a list of all staff sorted by increasing age.
     *
     * @return A sorted list of staff members.
     */
    public static List<Staff> getStaffListInIncreasingAge() {
        List<Staff> staffList = employeeList.stream()
                .filter(employee -> employee instanceof Staff)
                .map(employee -> (Staff) employee)
                .sorted(Comparator.comparingInt(Staff::getAge))
                .collect(Collectors.toList());
        return staffList;
    }
    

}