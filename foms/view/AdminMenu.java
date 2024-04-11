package foms.view;

import foms.controller.EmployeeController;
import foms.controller.OrdersController;
import foms.controller.AdminController;
import foms.controller.BranchController;
import foms.enums.UserRole;
import foms.models.Payment;
import foms.tools.ScannerCheck;
import foms.models.*;

import java.util.List;

import static foms.controller.BranchController.getBranchList;
import static foms.controller.BranchController.selectBranch;
import static foms.tools.ScannerCheck.verifySelection;
import static foms.tools.ScannerCheck.verifyString;

public class AdminMenu {

    public static void displayAdminMenu() {

        int selection;
        do {
            System.out.println("\n --- Admin menu ---");
            System.out.println("1. Add, edit, or remove Staff accounts");
            System.out.println("2. Display staff list (filter: branch, role, gender, age)");
            System.out.println("3. Promote a staff to a Branch manager.");
            System.out.println("4. Demote a manager to staff");
            System.out.println("5. Transfer a staff/manager among branches.");
            System.out.println("6. Add/remove payment method.");
            System.out.println("7. Open/close branch.");
            System.out.println("8. Quit to previous menu");
            selection = ScannerCheck.verifySelection(1, 8);
            switch (selection)
            {
                case 1: //Add, edit, or remove Staff accounts
                    System.out.println("\n1.Add staff.");
                    System.out.println("2.Edit staff info (Name/Gender/Age/UserID/Password).");
                    System.out.println("3.Remove staff.");
                    System.out.println("4. Quit to previous menu");
                    int choice1 = ScannerCheck.verifySelection(1, 4);
                    if(choice1==1){ //1.Add staff.
                        System.out.println("Please give the detailed information of the staff.");

                        System.out.print("Please input user role: S/M/A: ");
                        String staffNewRole = "";
                        while (true) {
                            staffNewRole = ScannerCheck.verifyString().toUpperCase();
                            if (staffNewRole.equals("S") || staffNewRole.equals("M") || staffNewRole.equals("A")) {
                                break;
                            } else {
                                System.out.println("Invalid choice! Please try again! Enter S/M/A.");
                            }
                        }
                        
                        System.out.println("Please input the name:");
                        String staffNewName = verifyString();
                        
                        System.out.println("Please input the gender: ('M' or 'F')");
                        String genderName = "";
                        while (true) {
                            genderName = ScannerCheck.verifyString().toUpperCase();
                            if (genderName.equalsIgnoreCase("F") || genderName.equalsIgnoreCase("M")) {
                                break;
                            } else {
                                System.out.println("Invalid choice! Please try again! Enter M/F.");
                            }
                        }
                        
                        System.out.println("Please input the age:");
                        int staffAge = ScannerCheck.verifySelection(1, 100);
                        
                        System.out.println("Please input the userid:");
                        String staffNewUserID = verifyString();
                        while (EmployeeController.userIdExit(staffNewUserID)) {
                            System.out.println("User ID already exists! Please try again.");
                            staffNewUserID = verifyString();
                        }
                        
                        String staffNewBranch = "";
                        if (staffNewRole.equalsIgnoreCase("S") || staffNewRole.equalsIgnoreCase("M")) {
                            System.out.println("Please input the branch name ('NTU', 'JP', 'JE'):");
                            while (true) {
                                staffNewBranch = verifyString().toUpperCase();
                                if (BranchController.BranchExist(staffNewBranch)) {
                                    break;
                                } else {
                                    System.out.println("Invalid branch! Please try again!");
                                }
                            }
                        }
                        
                        // Adding the staff for any role
                        if (EmployeeController.addStaff(staffNewRole, staffNewName, genderName, staffAge, staffNewUserID, staffNewBranch)) {
                            System.out.println("Staff role: " + staffNewRole + " | Staff name: " + staffNewName + " | Gender: " + genderName +
                                    " | Age: " + staffAge + " | User ID: " + staffNewUserID + " | Branch: " + staffNewBranch);
                            System.out.println("Added successfully!");
                        } else {
                            System.out.println("Invalid information! Please try again.");
                        }

                    }
                    else if(choice1==2) { //2.Edit staff info (Name/Gender/Age/UserID/Password).
                        System.out.println("Stafflist: ");
                        List<Staff> allStaffList = EmployeeController.getAllStaffList(); 
                        EmployeeController.printStaffList(allStaffList);
                        System.out.println(allStaffList.size()+1 + ": Quit to previous menu");
                        System.out.println("Enter the index of the staff member you want to edit: ");
                        
                        int selectedStaffIndex = ScannerCheck.verifySelection(1, allStaffList.size()+1);
                        if (selectedStaffIndex == allStaffList.size()+1) break;

                        Staff selectedStaff = allStaffList.get(selectedStaffIndex-1);
                        int choice;
                        do {
                            System.out.println("\nSelected Staff Details (Choose option below to edit):");
                            System.out.println("1. Role:        " + selectedStaff.getRoleInString());
                            System.out.println("2. Name:        " + selectedStaff.getName());
                            System.out.println("3. Gender:      " + selectedStaff.getGender());
                            System.out.println("4. Age:         " + selectedStaff.getAge());
                            System.out.println("5. UserID:      " + selectedStaff.getUserId());
                            System.out.println("6. Reset password :" + selectedStaff.getPassword());
                            System.out.println("7. Save Changes/Exit");

                            choice = ScannerCheck.verifySelection(1, 7);

                            switch (choice) {
                                case 1:
                                    System.out.print("Enter new Role: S/M/A: ");
                                    String newRole = "";

                                    while((!newRole.equalsIgnoreCase("S")) && (!newRole.equalsIgnoreCase("M"))&&(!newRole.equalsIgnoreCase("M"))){
                                        newRole = ScannerCheck.verifyString().toUpperCase();
                                        if ((!newRole.equalsIgnoreCase("S")) && (!newRole.equalsIgnoreCase("M"))&&(!newRole.equalsIgnoreCase("M"))){
                                            System.out.println("invalid choice! Please try again! (Enter S/M/A)");
                                        } else break;
                                    }

                                    selectedStaff.setRole(newRole.toUpperCase());
                                    break;
                                case 2:
                                    System.out.print("Enter new Name: ");
                                    String newName = ScannerCheck.verifyString();
                                    selectedStaff.setName(newName);
                                    break;
                                case 3:
                                    System.out.print("Enter new Gender: ");
                                    String newGender = "";
                                    while((!newGender.equalsIgnoreCase("F")) && (!newGender.equalsIgnoreCase("M"))){
                                        newGender = ScannerCheck.verifyString().toUpperCase();
                                        if ((!newGender.equalsIgnoreCase("F")) && (!newGender.equalsIgnoreCase("M"))){
                                            System.out.println("invalid choice! Please try again! (Enter M/F)");
                                        } else break;
                                    }
                                    selectedStaff.setGender(newGender);
                                    break;
                                case 4:
                                    System.out.print("Enter new Age: ");
                                    int newAge = ScannerCheck.verifySelection(1, 100);
                                    selectedStaff.setAge(newAge);
                                    break;
                                case 5:
                                    System.out.print("Enter new UserID: ");
                                    String newUserID = ScannerCheck.verifyString();
                                    selectedStaff.setUserid(newUserID);
                                    break;
                                case 6:
                                    System.out.print("password has been set to default (password) ");
                                    selectedStaff.setPassword("password");
                                    break;
                                    
                                case 7:
                                    System.out.println("Changes saved successfully.");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } while (choice != 7);

                    } else if(choice1==3){ //3.Remove staff.
                        System.out.println("Stafflist: ");
                        List<Staff> allStaffList = EmployeeController.getAllStaffList(); 
                        EmployeeController.printStaffList(allStaffList);
                        System.out.println(allStaffList.size()+1 + ". Quit to previous menu");
                        System.out.println("Please give the index of the employee you want to remove:");

                        int userIndexToBeRemoved = ScannerCheck.verifySelection(1, allStaffList.size()+1);

                        if(EmployeeController.removeStaff(allStaffList.get(userIndexToBeRemoved - 1).getUserId())){
                            System.out.println("employee with user id " + allStaffList.get(userIndexToBeRemoved -1 ).getUserId() + " is removed successfully!");                        
                        }else{
                            System.out.println("invalid information!");
                            continue;
                        }
                    } else if (choice1 == 4){ //4. Quit to previous menu
                        break;
                    }
                    else
                        System.out.println("invalid choice! Please choose again!");
                    continue;

                case 2: //2.Display staff list (filter: branch, role, gender, age)
                    System.out.println("\n1.filter: branch.");
                    System.out.println("2.filter: role.");
                    System.out.println("3.filter: gender.");
                    System.out.println("4.filter: age.");
                    System.out.println("5. Quit to previous menu");
                    List<Staff> staffListToDisplay;
                    int choice2 = ScannerCheck.verifySelection(1, 5);
                    if(choice2==2) { //2.filter: role.
                        System.out.println("\nplease give the role filter");
                        System.out.println("1.Manager");
                        System.out.println("2.Staff");
                        int rolechoiceindex = ScannerCheck.verifyInt();
                        UserRole roleChoice;
                        if (rolechoiceindex == 1){
                            roleChoice = UserRole.M;
                        } else roleChoice = UserRole.S;
                        staffListToDisplay = EmployeeController.getStaffListByAttribute(null, roleChoice,  null, 0);
                        EmployeeController.printStaffList(staffListToDisplay);

                    }else if(choice2==1) //1.filter: branch.
                    {
                        System.out.println("\nPlease select a branch filter:");
                        String branchChoice = selectBranch(); 
                        System.out.println("Branch choice: "+ branchChoice);

                        staffListToDisplay = EmployeeController.getStaffListByAttribute(branchChoice, null, null, 0);
                        EmployeeController.printStaffList(staffListToDisplay);
                    }
                    else if(choice2==3) //filter: gender.
                    {
                        System.out.println("\nplease give the gender filter");
                        System.out.println("1. Male");
                        System.out.println("2. Female");
                        System.out.println("3. Quit");
                        String genderChoice;
                        int choice = ScannerCheck.verifySelection(1, 3);
                        if (choice == 1){
                            genderChoice = "M";
                        } else if (choice == 2){
                            genderChoice = "F";
                        } else break;
                        staffListToDisplay = EmployeeController.getStaffListByAttribute( null, null, genderChoice,0);
                        EmployeeController.printStaffList(staffListToDisplay);
                    }
                    else if(choice2==4) //4.filter: age.
                    {
                        System.out.println("\nDisplay Staff in increasing age:");
                        EmployeeController.printStaffList(EmployeeController.getStaffListInIncreasingAge());
                    } else if (choice2 == 5){ //5. Quit to previous menu
                        break; 
                    }
                    else
                        System.out.println("invalid choice!!");
                        
                    continue;
            
                case 3: //3.Promote a staff to a Branch manager.
                    System.out.println("Staff list: ");
                    List<Staff> selectedStaffList = EmployeeController.getStaffListByAttribute(null, UserRole.S, null, 0); 
                    EmployeeController.printStaffList(selectedStaffList);
                    System.out.println(selectedStaffList.size()+1 + ". Quit to previous menu");

                    System.out.println("\nplease give the index of the manager you want to demote");
                    int promoteIndex = verifySelection(1, selectedStaffList.size()+1);

                    if (promoteIndex== selectedStaffList.size()+1) continue;
                    Staff victim = selectedStaffList.get(promoteIndex-1);
                    victim.setRole(UserRole.M);
                    System.out.println("The staff with user id "+ victim.getUserId() +" have been promoted to branch manager successfully");
                    continue;

                case 4://4.demote manager to a staff
                    System.out.println("Manager list: ");
                    selectedStaffList = EmployeeController.getStaffListByAttribute(null, UserRole.M, null, 0); 
                    EmployeeController.printStaffList(selectedStaffList);
                    System.out.println(selectedStaffList.size()+1 + ". Quit to previous menu");

                    System.out.println("\nplease give the index of the manager you want to demote");
                    int demoteIndex = verifySelection(1, selectedStaffList.size()+1);

                    if (demoteIndex == selectedStaffList.size()+1) continue;
                    victim = selectedStaffList.get(demoteIndex-1);
                    victim.setRole(UserRole.S);
                    System.out.println("The manager with user id "+ victim.getUserId() +" have been demoted to staff successfully");
                    continue;

                case 5://5.Transfer a staff/manager among branches.

                    System.out.println("Stafflist: ");
                    List<Staff> allStaffList = EmployeeController.getAllStaffList(); 
                    EmployeeController.printStaffList(allStaffList);
                    System.out.println(allStaffList.size()+1 + ". Quit to previous menu");
                    System.out.println("\nplease give the index of the employee you want to transfer");
                    
                    int transferUserIndex = verifySelection(1, allStaffList.size()+1);
                    if (transferUserIndex == allStaffList.size()+1) continue;

                    String userid3 = allStaffList.get(transferUserIndex-1).getUserId();
                    
                    List<Branch> updatedBranchList = BranchController.getBranchList();
                    BranchController.printBranchList(updatedBranchList);
                    System.out.println("Please choose the branch you want " + userid3 + " to be transferred to: ");
                    int branchIndex = ScannerCheck.verifySelection(1, updatedBranchList.size());
                    String branchName = updatedBranchList.get(branchIndex-1).getName();

                    if(EmployeeController.transferEmployee(userid3,branchName)){
                        System.out.println("Employee with user id "+userid3+ " has beem transfered to new branch "+branchName+" successfully!");
                    }
                    else{
                        System.out.println("Transfer failed");
                    }
                    continue;
                case 6: //5.Add/remove payment method.
                    System.out.println("1. Add payment method");
                    System.out.println("2. Remove payment method");
                    System.out.println("3. Quit to previous menu");
                    int choice3 = ScannerCheck.verifySelection(1, 3);
                    if(choice3==1) { //1.add payment method
                        System.out.println("please give the new payment method name.");

                        String newpaymentmethod = ScannerCheck.verifyString();
                        if(BranchController.addPaymentMethod(newpaymentmethod)){
                            System.out.println("New payment method"+newpaymentmethod+" add successfully!");
                        }
                        else{
                            System.out.println("The payment method is already exist!");
                            continue;
                        }
                        continue;

                    }else if(choice3==2) //2.remove payment method
                    {
                        BranchController.displayPaymentMethods(null);
                        System.out.println("please give the payment method name you want to remove.");
                        String paymentmethod = ScannerCheck.verifyString();
                        if(BranchController.removePaymentMethod(paymentmethod)){
                            System.out.println("The payment method name "+paymentmethod+ "remove successfully!");
                        }
                        else{
                            System.out.println("This payment method not exist! Please try again!");
                        }
                        continue;
                    } else continue;

                case 7: //6.Open/close branch.
                    System.out.println("1.open new branch");
                    System.out.println("2.close existed branch");
                    System.out.println("3. Quit to previous menu");
                    int choice4 = ScannerCheck.verifySelection(1, 3);

                    if(choice4==1) {
                        System.out.println("please give the new branch's name.");
                        String newBranchName = verifyString();
                        if(BranchController.BranchExist(newBranchName)){
                            System.out.println("The branch name already exist!");
                            continue;
                        }
                        System.out.println("please give the new branch's location.");
                        String newBranchLocation = verifyString();
                        System.out.println("please enter the staff quota of branch "+ newBranchName);
                        int staffQuota = ScannerCheck.verifyInt();
                        BranchController.openBranches(newBranchName,newBranchLocation,null,staffQuota,0,0,1);
                        continue;

                    }else if(choice4==2) //close branch
                    {
                        updatedBranchList = BranchController.getBranchList();
                        BranchController.printBranchList(updatedBranchList);
                        System.out.println(updatedBranchList.size()+1 + ". Quit to previous menu");
                        System.out.println("please give the branch you want to close.");

                        branchIndex = ScannerCheck.verifySelection(1, updatedBranchList.size()+1);

                        if (branchIndex == updatedBranchList.size()+1){
                            continue;
                        }
                        branchName = updatedBranchList.get(branchIndex-1).getName();
                        if(!BranchController.BranchExist(branchName)){
                            System.out.println("Branch not exist! Please try again!");
                            continue;}
                        if(BranchController.closeBranches(branchName)){
                            System.out.println("Branch " + branchName + " closed successfully!");
                            List<Staff> affectedStaffList= EmployeeController.getStaffListByAttribute(branchName, null, null, 0);
                            for (Staff staff : affectedStaffList){
                                staff.setBranch(null);
                            }
                            continue;
                        }
                    } else continue;

                case 8:
                    System.out.println("Quit to previous option.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                
            } 
        } while (selection != 8);
    } 
}
        
        


