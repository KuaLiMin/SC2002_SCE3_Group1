package foms.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.Employee;
import foms.models.Staff;
import foms.models.Manager;
import foms.models.MenuItem;
import foms.tools.ScannerCheck;


/**
 * The EmployeeController class is responsible for
 * Controling the operations related to branches within the system.
 * This includes opening and closing branches, adding or removing payment methods, and editing menu items.
 * 
 * @author JIANG LI-KAI
 * @version 1.0
 * @since 2024-04-20
 */
public class BranchController {
    protected static ArrayList<Branch> branchList = FileIO.getBranchList();
    protected static ArrayList<Employee> employeeList = FileIO.getEmployeeList();
/**
     * Closes a branch based on the branch name.
     * 
     * @param branch_name The name of the branch to be closed.
     * @return true if the branch was successfully removed, false otherwise.
     */
    public static boolean closeBranches(String branch_name) {
        // 先检查分支是否存在
        boolean branchExists = branchList.stream().anyMatch(branch -> branch.getName().equals(branch_name));
        if (!branchExists) {
            System.out.println("Branch not found.");
            return false;
        }
    
        // 移除所有该分支的员工
        employeeList.removeIf(employee -> Optional.ofNullable(employee.getBranch()).orElse("").equals(branch_name));
    
        // 移除分支
        boolean removed = branchList.removeIf(branch -> branch.getName().equals(branch_name));
        if (removed) {
            System.out.println("Branch closed and all associated employees have been removed.");
        } else {
            System.out.println("Failed to close the branch.");
        }
        return removed;
    }
    
    
/**
     * Opens a new branch with the specified details.
     * 
     * @param name The name of the new branch.
     * @param location The location of the new branch.
     * @param menuItemsList List of menu items for the new branch.
     * @param staffQuota The staff quota for the new branch.
     * @param staffCount Initial count of staff.
     * @param managerCount Initial count of managers.
     * @param managerQuota The manager quota for the new branch.
     * @return true if the branch was successfully added, false if a branch with the same name already exists.
     */
    public static boolean openBranches(String name, String location, ArrayList<MenuItem> menuItemsList, int staffQuota,
            int staffCount, int managerCount, int managerQuota) {
        Branch branch = new Branch(name, location, staffQuota/* , menuItemsList, staffQuota, staffCount, managerCount, managerQuota*/);

        boolean exists1 = branchList.stream().anyMatch(e -> e.getName().equals(branch.getName()));
        if (!exists1) {
            branchList.add(branch);
            // 数据不持久化到文件
            return true; // 添加成功
        }
        return false;
    }
/**
     * Selects a branch from a list of branches by user selection.
     * 
     * @param branchList The list of branches to choose from.
     * @return The selected branch.
     */
    public static Branch selectBranch(ArrayList<Branch> branchList) {
        int selection;

        do {
            System.out.println("\nPlease select your current branch: ");
            for (int i = 0; i < branchList.size(); i++) {
                System.out.println((i+1) + ". " + branchList.get(i).getName());
            }
            selection = ScannerCheck.verifySelection(1, branchList.size());
        } while (selection <= 0 || selection > branchList.size());

        return branchList.get(selection-1);
    }
/**
     * Adds a payment method to a specific branch.
     * 
     * @param newPaymentMethodName The name of the payment method to add.
     * @return true if the payment method was successfully added, false otherwise.
     */
    public static boolean addPaymentMethod(String newPaymentMethodName) {
        
        if (Branch.addPaymentMethod(newPaymentMethodName)) {
            return true;
        }
        return false;
    }
/**
     * Displays the payment methods available at a specific branch.
     * 
     * @param branchName The name of the branch whose payment methods are to be displayed.
     */
    public static void displayPaymentMethods(String branchName) {
        System.err.println("Payment methods:");
        Branch.paymentList.forEach(payment -> System.out.println(payment.getName()));
    }
/**
     * Removes a payment method from a branch.
     * 
     * @param paymentMethod The name of the payment method to remove.
     * @return true if the payment method was successfully removed, false otherwise.
     */
    public static boolean removePaymentMethod (String paymentMethod) {
        if (Branch.removePaymentMethod(paymentMethod)) {
            return true;
        }
        return false;
    }
 /**
     * Removes an item from the menu of a specific branch.
     * 
     * @param itemName The name of the item to remove.
     * @param branchName The name of the branch from which the item is to be removed.
     * @return true if the item was successfully removed, false if the branch was not found or the item does not exist.
     */
    public static boolean removeItemFromMenu(String itemName, String branchName) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);
        if (branch != null) {
            return branch.getMenuItemsList().removeIf(item -> item.getName().equals(itemName));
        }
        return false; // Branch not found
    }
 /**
     * Edits an item in the menu of a specific branch.
     * 
     * @param branchName The name of the branch where the item is located.
     * @param itemName The name of the item to edit.
     * @param newPrice The new price of the item (optional).
     * @param newCategory The new category of the item (optional).
     * @param Availability The new availability status of the item.
     * @return true if the item was successfully updated, false if the branch or item was not found.
     */
    public static boolean editItem(String branchName, String itemName, Double newPrice, String newCategory, Boolean Availability) {
        Branch branch = branchList.stream()
                .filter(b -> b.getName().equals(branchName))
                .findFirst()
                .orElse(null);

        if (branch != null) {
            for (MenuItem item : branch.getMenuItemsList()) {
                if (item.getName().equals(itemName)) {
                    if (newPrice != null) {
                        item.setPrice(newPrice);
                    }
                    if (newCategory != null) {
                        item.setCategory(newCategory);
                    }
                    Availability=true;
                    return true; // Item found and updated
                }
            }
            // Item not found
            return false;
        }
        return false;
    }
    /**
     * Checks if a branch exists by name.
     * 
     * @param Branch The name of the branch to check.
     * @return true if the branch exists, false otherwise.
     */
    public static Boolean BranchExist(String Branch){
            Optional<Branch> BranchOptional = branchList.stream()
                    .filter(emp -> emp.getName().equals(Branch))
                    .findFirst();
            if (BranchOptional.isPresent())
                return true;
            return false;
    }
/**
     * Prompts the user to select a branch from a list of available branches and returns the name of the selected branch.
     * 
     * @return The name of the branch selected by the user.
     */
    public static String selectBranch() {
        System.out.println("Select a branch:");
        for (int i = 0; i < branchList.size(); i++) {
            System.out.println((i + 1) + ". " + branchList.get(i).getName());
        }

        int branchIndex = ScannerCheck.verifySelection(1, branchList.size()) - 1;
        return branchList.get(branchIndex).getName();
    }
    /**
     * Selects a branch by its name and returns it.
     * 
     * @param branchName The name of the branch to select.
     * @return The selected branch, or null if no branch with that name exists.
     */
    public static Branch selectBranchByName(String branchName) {
        for (Branch branch : branchList) {
            if (branch.getName().equalsIgnoreCase(branchName)) {
                return branch;
            }
        }
        return null; 
    }

/**
     * Prints a list of branches with detailed information.
     * 
     * @param branchListToPrint The list of branches to print.
     */
    public static void printBranchList(List<Branch> branchListToPrint){
        System.out.printf("%-5s |%-20s | %-20s | %-15s | %-15s\n", "Index", "Name", "Location", "Staff Vacancy", "Manager Vacancy");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        int counter = 1;
        for (Branch branch : branchListToPrint){
            int staffVacancy = branch.getStaffQuota() - getStaffCount(branch.getName());
            int managerVacancy = branch.getManagerQuota() - getManagerCount(branch.getName());
            System.out.printf("%-5s |%-20s | %-20s | %-15s | %-15s\n",
                    counter++, branch.getName(), branch.getLocation(), staffVacancy, managerVacancy);
        }
    }
 /**
     * Retrieves a list of all branches.
     * 
     * @return A list of all branches currently managed by the system.
     */
    public static List<Branch> getBranchList(){
        return branchList;
    }
/**
     * Counts the number of staff members at a specific branch.
     * 
     * @param branchName The name of the branch for which the staff count is to be determined.
     * @return The number of staff members currently assigned to the specified branch.
     */
    public static int getStaffCount(String branchName){
        int count = 0;
        for (Employee employee : employeeList){
            if(employee instanceof Staff && ((Staff) employee).getBranch().equals(branchName)){
                count++;
            }
        }
        return count;
    }
/**
     * Counts the number of managers at a specific branch.
     * 
     * @param branchName The name of the branch for which the manager count is to be determined.
     * @return The number of managers currently assigned to the specified branch.
     */
    public static int getManagerCount(String branchName){
        int count = 0;
        for (Employee employee : employeeList){
            if(employee instanceof Manager && ((Manager) employee).getBranch().equals(branchName)){
                count++;
            }
        }
        return count;
    }

}

