package foms.controller;
import java.util.ArrayList;
import java.util.Optional;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.MenuItem;
import foms.models.Payment;
import foms.tools.ScannerCheck;

public class BranchController {
    protected static ArrayList<Branch> branchList = FileIO.getBranchList();

    public static boolean closeBranches(String branch_name) {
        boolean removed =branchList.removeIf(branch -> branch.getName().equals(branch_name));
        return removed;
    }

    public static boolean openBranches(String name, String location, ArrayList<MenuItem> menuItemsList, int staffQuota,
            int staffCount, int managerCount, int managerQuota) {
        Branch branch = new Branch(name, location /* , menuItemsList, staffQuota, staffCount, managerCount, managerQuota*/);

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

    public static boolean addPaymentMethod(Payment newPaymentMethod) {
        if (Branch.addPaymentMethod(newPaymentMethod)) {
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
    }

