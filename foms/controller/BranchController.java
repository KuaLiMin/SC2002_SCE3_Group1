package foms.controller;

import java.util.ArrayList;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.MenuItem;

public class BranchController {
    private static final ArrayList<Branch> branchList = FileIO.getBranchList();
    public static void closeBranches(String branch_name){
        branchList.removeIf(branch-> branch.getName().equals(branch_name));

    }

    public static void openBranches(String name, String location, ArrayList<MenuItem> menuItemsList, int staffQuota, int staffCount, int managerCount, int managerQuota){
       Branch branch=new Branch(name,location,menuItemsList,staffQuota,  staffCount,  managerCount, managerQuota);
        branchList.add(branch);
    }
}
