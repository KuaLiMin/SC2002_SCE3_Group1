package foms.view;

import foms.controller.EmployeeController;
import foms.controller.BranchController;
import foms.enums.UserRole;
import foms.models.Payment;
import foms.tools.ScannerCheck;



import static foms.tools.ScannerCheck.verifyInt;
import static foms.tools.ScannerCheck.verifyString;

public class AdminMenu {

    public static void displayAdminMenu() {


        int selection;
        do {
            System.out.println("\n --- Admin menu ---");
            System.out.println("1.Add, edit, or remove Staff accounts");
            System.out.println("2.Display staff list (filter: branch, role, gender, age)");
            System.out.println("3.Assign managers to branch.");
            System.out.println("4.Promote a staff to a Branch manager.");
            System.out.println("5.Transfer a staff/manager among branches.");
            System.out.println("6.Add/remove payment method.");
            System.out.println("7.Open/close branch.");
            System.out.println("8.Quit to previous menu");
            selection = ScannerCheck.verifySelection(1, 8);
            switch (selection)
            {
                case 1:
                    System.out.println("1.Add staff.");
                    System.out.println("2.Edit staff.");
                    System.out.println("3.Remove staff.");
                    int choice1 = ScannerCheck.verifySelection(1, 3);
                    if(choice1==1){
                        System.out.println("please give the detailed information of the staff.");
                        System.out.println("please input the role('A','S','M').");
                        String staffNewRole = verifyString();

                        if(!staffNewRole.equals("A")&&!staffNewRole.equals("S")&&!staffNewRole.equals("M")){
                            System.out.println("invalid choice! Please try again!");
                        continue;}

                        System.out.println("please input the name:");
                        String staffNewName = verifyString();
                        System.out.println("please input the gender:('M','F')");
                        String genderName = verifyString();

                        if(!genderName.equals("F")&&!genderName.equals("M")){
                            System.out.println("invalid choice! Please try again!");
                            continue;}

                        System.out.println("please input the age:");
                        int staffAge = verifyInt();
                        System.out.println("please input the userid:");
                        String staffNewUserID = verifyString();

                        if(EmployeeController.userIdExit(staffNewUserID)){
                            System.out.println("userid exists already! Please try again!");
                            continue;
                        }

                        System.out.println("please input the branch name('NTU','JP','JE'):");
                        String staffNewBranch = verifyString();
                        
                        if(!BranchController.BranchExist(staffNewBranch)){
                            System.out.println("invalid choice! Please try again!");
                            continue;}

                        if(EmployeeController.addStaff(staffNewRole,
                                staffNewName,
                                genderName,
                                staffAge,
                                staffNewUserID,
                                staffNewBranch)){
                            System.out.println("staff role: " + staffNewRole + " staff name: " +staffNewName + " gender: " + genderName + " age: "+staffAge+ " user id " +staffNewUserID + " Branch name " + staffNewBranch);
                            System.out.println("add successfully!");
                        }//role, name, gender, age, userid,branch
                        else{
                            System.out.println("invalid information!");
                            continue;
                        }

                    }
                    else if(choice1==2) {
                        System.out.println("please give the userid of the staff you want to edit.");
                        String staffUserID = verifyString();
                        if(!EmployeeController.userIdExit(staffUserID)){
                            System.out.println("invalid userid! Please try again!");
                            continue;
                        }

                        System.out.println("please input the role('A','S','M').");
                        String staffNewRole = verifyString();

                        if(!staffNewRole.equals("A")&&!staffNewRole.equals("S")&&!staffNewRole.equals("M")){
                            System.out.println("invalid choice! Please try again!");
                            continue;
                        }

                        System.out.println("please input the new name:");
                        String staffNewName = verifyString();
                        System.out.println("please input the new gender:('M','F')");
                        String genderName = verifyString();
                        if(!genderName.equals("F")&&!genderName.equals("M")){
                            System.out.println("invalid choice! Please try again!");
                            continue;
                        }

                        System.out.println("please input the new age:");
                        int staffAge = verifyInt();
                        System.out.println("please input the new userid:");
                        String staffNewUserID = verifyString();
                        if(EmployeeController.userIdExit(staffNewUserID)){
                            System.out.println("userid exists already! Please try again!");
                            continue;
                        }
                        System.out.println("please input the new branch name('NTU','JP','JE'):");
                        String staffNewBranch = verifyString();
                        if(!BranchController.BranchExist(staffNewBranch)){
                            System.out.println("invalid choice! Please try again!");
                            continue;}

                        if(EmployeeController.editStaff(staffUserID, staffNewRole, staffNewName, genderName,
                                staffAge,
                                staffNewUserID,
                                staffNewBranch)){
                                System.out.println("staff role:" + staffNewRole + " staff name:" + staffNewName + " gender:" + genderName + " age:" + staffAge + " user id:" + staffNewUserID + " staff branch:" + staffNewBranch);                            System.out.println("edit successfully!");
                        }//role, name, gender, age, userid,branch
                         else{
                            System.out.println("invalid information!");
                            continue;
                        }
                    } else if(choice1==3){
                        System.out.println("please give the user id of employee you want to remove:");
                        String Userid = verifyString();
                        if(EmployeeController.removeStaff(Userid))
                        System.out.println("employee with user id " + Userid + " is removed successfully!");                        
                        else{
                            System.out.println("invalid information!");
                            continue;
                        }
                    }
                    else
                        System.out.println("invalid choice! Please choose again!");
                    continue;

                case 2:
                    System.out.println("1.filter: branch.");
                    System.out.println("2.filter: role.");
                    System.out.println("3.filter: gender.");
                    System.out.println("4.filter: age.");
                    int choice2 = ScannerCheck.verifySelection(1, 4);
                    if(choice2==2) {
                        System.out.println("please give the role filter");
                        System.out.println("1.Manager");
                        System.out.println("2.Staff");
                        int rolechoiceindex = ScannerCheck.verifyInt();
                        UserRole roleChoice;
                        if (rolechoiceindex == 1){
                            roleChoice = UserRole.M;
                        } else roleChoice = UserRole.S;
                        EmployeeController.getStaffList(null,roleChoice,  null, 0);
                        
                    }else if(choice2==1)
                    {
                        System.out.println("please give the branch filter");
                        String branchChoice = verifyString();
                        EmployeeController.getStaffList(branchChoice, null, null, 0);
                    }
                    else if(choice2==3)
                    {
                        System.out.println("please give the gender filter");
                        String genderChoice = verifyString();
                        EmployeeController.getStaffList( null, null, genderChoice,0);
                    }
                    else if(choice2==4)
                    {
                        System.out.println("please give the age filter");
                        int ageChoice = ScannerCheck.verifyInt();
                        EmployeeController.getStaffList(null, null, null, ageChoice);
                    }
                    else
                        System.out.println("invalid choice!!");
                    continue;
                case 3:
                    System.out.println("please give the userid:");
                    String userid1 = verifyString();
                   if(!EmployeeController.userIdExit(userid1)){
                       System.out.println("invalid userid! Please try again!");
                       continue;
                   }
                    System.out.println("please give the branch name('NTU','JP','JE')");
                    String branch = verifyString();
                    if(!BranchController.BranchExist(branch)){
                        System.out.println("invalid choice! Please try again!");
                        continue;}
                    if(EmployeeController.assignManager(userid1,branch)){
                        System.out.println("The Manager with user id" + userid1 + " have been assigned toBranch "+ branch);
                    }
                    else{
                        System.out.println("some errors! Please try again!");
                    }
                    continue;
                case 4:
                    System.out.println("please give the userid");
                    String userid2 = verifyString();
                    if(!EmployeeController.userIdExit(userid2)){
                        System.out.println("invalid userid! Please try again!");
                        continue;
                    }
                    if(EmployeeController.promoteToBranchManager(userid2)) {
                        System.out.println("the staff with user id " + userid2+" have promoted to Manager successfully!");
                    }
                    else{
                        System.out.println("The user id is from Admin or Manager! Please try again!");
                    }
                    continue;
                case 5:
                    System.out.println("please give the userid the employee you want to transfer");
                    String userid3 = verifyString();
                    if(!EmployeeController.userIdExit(userid3)){
                        System.out.println("invalid userid! Please try again!");
                        continue;
                    }

                    System.out.println("please give the name of new branch you want to transfer");
                    String branchName = verifyString();
                    if(!BranchController.BranchExist(branchName)){
                        System.out.println("invalid choice! Please try again!");
                        continue;}
                    if(EmployeeController.transferEmployee(userid3,branchName)){
                        System.out.println("Employee with user id " +userid3+ "has beem transfered to new branch" +branchName+ " successfully!");
                    }
                    else{
                        System.out.println("some errors! Please try again!");
                    }
                    continue;
                case 6:
                    System.out.println("1.add payment method");
                    System.out.println("2.remove payment method");
                    int choice3 = ScannerCheck.verifySelection(1, 2);
                    if(choice3==1) {
                        System.out.println("please give the new payment method name.");
                        String newpaymentname = ScannerCheck.verifyString();
                        Payment newpaymentmethod = new Payment(newpaymentname);
                        if(BranchController.addPaymentMethod(newpaymentmethod)){
                            System.out.println("The new payment method " +newpaymentmethod+" add successfully!");
                        }
                        else{
                            System.out.println("This payment method exist already! Please try again!");
                        }
                        continue;
                    }else if(choice3==2)
                    {
                        BranchController.displayPaymentMethods(null);
                        System.out.println("please give the payment method name you want to remove.");
                        String paymentmethod = ScannerCheck.verifyString();
                        if(BranchController.removePaymentMethod(paymentmethod)){
                            System.out.println("The payment method name " +paymentmethod+ " remove successfully!");
                        }
                        else{
                            System.out.println("This payment method not exist! Please try again!");
                        }
                        continue;
                    }
                case 7:
                    System.out.println("1.open new branch");
                    System.out.println("2.close existed branch");
                    int choice4 = ScannerCheck.verifySelection(1, 2);
                    if(choice4==1) {
                        System.out.println("please give the new branch's name.");
                        String newBranchName = verifyString();
                        if(BranchController.BranchExist(newBranchName)){
                            System.out.println("Branch name exist! Please try again!");
                            continue;}
                        System.out.println("please give the new branch's location.");
                        String newBranchLocation = verifyString();
                        if(BranchController.openBranches(newBranchName,newBranchLocation
                                ,null,4,0,
                                0,1)){
                            System.out.println("Branch opened successfully!");
                        }
                        else{
                            System.out.println("some errors! Please try again!");
                        }
                    }else if(choice4==2)
                    {
                        System.out.println("please give the branch you want to close.");
                        String BranchName = verifyString();
                        if(!BranchController.BranchExist(BranchName)){
                            System.out.println("Branch not exist! Please try again!");
                            continue;}
                        if(BranchController.closeBranches(BranchName)){
                            System.out.println("Branch " +BranchName + " closed successfully!");
                        }
                    }
                    continue;
                case 8:
                    System.out.println("Quit to previous option.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
                
            } 
        } while (selection>0 && selection<=8);
    } 
}
        
        


