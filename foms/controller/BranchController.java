package foms.controller;

import java.util.ArrayList;

import foms.fileio.FileIO;
import foms.models.Branch;

public class BranchController {
    private static final ArrayList<Branch> employeeList = FileIO.getBranchList();
}
