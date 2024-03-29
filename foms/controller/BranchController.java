package foms.controller;

import java.util.ArrayList;

import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.Payment;

public class BranchController {
    private static final ArrayList<Branch> employeeList = FileIO.getBranchList();

    public static void displayMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayMenu'");
    }

    public static ArrayList<Payment> getPaymentList() {
        return Branch.paymentList;
    }
}
