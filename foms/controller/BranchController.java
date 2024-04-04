package foms.controller;

import java.util.ArrayList;
import java.util.Iterator;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.MenuItem;
import foms.tools.ScannerCheck;

public class BranchController {
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();

    public static void closeBranches(String branch_name) {
        branchList.removeIf(branch -> branch.getName().equals(branch_name));

    }

    public static void openBranches(String name, String location, ArrayList<MenuItem> menuItemsList, int staffQuota,
            int staffCount, int managerCount, int managerQuota) {
        Branch branch = new Branch(name, location /* , menuItemsList, staffQuota, staffCount, managerCount, managerQuota*/);
        branchList.add(branch);
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

}
