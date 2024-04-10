package foms.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import foms.fileio.FileIO;
import foms.models.*;
import foms.models.Employee;
import foms.models.MenuItem;
import foms.models.Payment;
import foms.tools.ScannerCheck;

public class BranchController {
    protected static ArrayList<Branch> branchList = FileIO.getBranchList();
    protected static ArrayList<Employee> employeeList = FileIO.getEmployeeList();

    public static boolean closeBranches(String branch_name) {
        boolean removed =branchList.removeIf(branch -> branch.getName().equals(branch_name));
        return removed;
    }

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

    public static boolean addPaymentMethod(String newPaymentMethodName) {
        
        if (Branch.addPaymentMethod(newPaymentMethodName)) {
            return true;
        }
        return false;
    }

    public static void displayPaymentMethods(String branchName) {
        System.err.println("Payment methods:");
        Branch.paymentList.forEach(payment -> System.out.println(payment.getName()));
    }

    public static boolean removePaymentMethod (String paymentMethod) {
        if (Branch.removePaymentMethod(paymentMethod)) {
            return true;
        }
        return false;
    }

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
    
    public static Boolean BranchExist(String Branch){
            Optional<Branch> BranchOptional = branchList.stream()
                    .filter(emp -> emp.getName().equals(Branch))
                    .findFirst();
            if (BranchOptional.isPresent())
                return true;
            return false;
    }

    public static String selectBranch() {
        System.out.println("Select a branch:");
        for (int i = 0; i < branchList.size(); i++) {
            System.out.println((i + 1) + ". " + branchList.get(i).getName());
        }

        int branchIndex = ScannerCheck.verifySelection(1, branchList.size()) - 1;
        return branchList.get(branchIndex).getName();
    }
    
    public static Branch selectBranchByName(String branchName) {
        for (Branch branch : branchList) {
            if (branch.getName().equalsIgnoreCase(branchName)) {
                return branch;
            }
        }
        return null; 
    }


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

    public static List<Branch> getBranchList(){
        return branchList;
    }

    public static int getStaffCount(String branchName){
        int count = 0;
        for (Employee employee : employeeList){
            if(employee instanceof Staff && ((Staff) employee).getBranch().equals(branchName)){
                count++;
            }
        }
        return count;
    }

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

