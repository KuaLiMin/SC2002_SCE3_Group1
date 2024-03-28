package foms.view;

import foms.models.Branch;
import foms.tools.ScannerCheck;

import java.util.ArrayList;

public class MakeOrderMenu {
    public static void displayOrderMenu(ArrayList<Branch> branchList) {
        int selection;

        do {
            System.out.println("Please select your current branch: ");
            for (int i=0; i<branchList.size(); i++) {
                System.out.println(i + ". " + branchList.get(i));
            }
            selection = ScannerCheck.verifySelection(1, branchList.size());
        } while (selection<=0 || selection>branchList.size());
        
    }
}
