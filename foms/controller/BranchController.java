package foms.controller;

import java.util.ArrayList;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.Payment;

public class BranchController {
    private static final ArrayList<Branch> branchList = FileIO.getBranchList();
    public void closeBranches(String branch_name){
        branchList.removeIf(branch-> branch.getName().equals(branch_name));

    }

    public void openBranches(Branch branch){
        branchList.add(branch);
    }
}
