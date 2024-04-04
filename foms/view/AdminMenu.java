package foms.view;

import foms.controller.AdminController;
import foms.controller.BranchController;
import foms.controller.OrdersController;
import foms.enums.UserRole;
import foms.tools.ScannerCheck;
import foms.models.Payment;
import static foms.tools.ScannerCheck.verifyInt;
import static foms.tools.ScannerCheck.verifyString;

public class AdminMenu {
    final static int MANAGE_STAFF_ACCOUNT=1;
    final static int DISPLAY_STAFF_LIST=2;
    final static int ASSIGN_MANAGERS=3;
    final static int PROMOTE_STAFF_TO__MANAGER=4;
    final static int TRANSFER_STAFF_MANAGER=5;
    final static int  MANAGE_PAYMENT_METHOD=6;
    final static int OPEN_CLOSE_BRANCH=7;

    public static void displayAdminMenu() {
        System.out.println("\n --- Admin menu ---");
        System.out.println("1.Add, edit, or remove Staff accounts");
        System.out.println("2.Display staff list (filter: branch, role, gender, age)");
        System.out.println("3.Assign managers to branch.");
        System.out.println("4.Promote a staff to a Branch manager.");
        System.out.println("5.Transfer a staff/manager among branches.");
        System.out.println("6.Add/remove payment method.");
        System.out.println("7.Open/close branch.");
        int selection = ScannerCheck.verifySelection(1, 7);

        do {
            switch (selection)
            {
                case MANAGE_STAFF_ACCOUNT:
                    System.out.println("1.Add staff.");
                    System.out.println("2.Edit staff.");
                    System.out.println("3.Remove staff.");
                    int choice1 = ScannerCheck.verifySelection(1, 3);
                    if(choice1==1){
                        System.out.println("please give the detailed information of the staff.");
                        System.out.println("please input the role.");
                        String staffNewRole = verifyString();
                        System.out.println("please input the name.");
                        String staffNewName = verifyString();
                        System.out.println("please input the gender.");
                        String genderName = verifyString();
                        System.out.println("please input the age.");
                        int staffAge = verifyInt();
                        System.out.println("please input the userid.");
                        String staffNewUserID = verifyString();
                        System.out.println("please input the branch name.");
                        String staffNewBranch = verifyString();

                        AdminController.addStaff(staffNewRole,
                                staffNewName,
                                genderName,
                                staffAge,
                                staffNewUserID,
                                staffNewBranch);//role, name, gender, age, userid,branch
                    }
                    else if(choice1==2) {
                        System.out.println("please give the userid of the staff you want to edit.");
                        String staffUserID = verifyString();
                        System.out.println("please input the new role.");
                        String staffNewRole = verifyString();
                        System.out.println("please input the new name.");
                        String staffNewName = verifyString();
                        System.out.println("please input the new gender.");
                        String genderName = verifyString();
                        System.out.println("please input the new age.");
                        int staffAge = verifyInt();
                        System.out.println("please input the new userid.");
                        String staffNewUserID = verifyString();
                        System.out.println("please input the new branch name.");
                        String staffNewBranch = verifyString();

                        AdminController.editStaff(staffUserID,
                                staffNewRole,
                                staffNewName,
                                genderName,
                                staffAge,
                                staffNewUserID,
                                staffNewBranch);//role, name, gender, age, userid,branch
                    }

                    else if(choice1==3){
                        System.out.println("please give the gender filter");
                        String Userid = verifyString();
                        AdminController.removeStaff(Userid);}
                    else
                        System.out.println("invalid choice!!");
                    break;
                case DISPLAY_STAFF_LIST:
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
                        AdminController.getStaffList(null,roleChoice,  null, 0);
                        
                    }else if(choice2==1)
                    {
                        System.out.println("please give the branch filter");
                        String branchChoice = verifyString();
                        AdminController.getStaffList(branchChoice, null, null, 0);
                    }
                    else if(choice2==3)
                    {
                        System.out.println("please give the gender filter");
                        String genderChoice = verifyString();
                        AdminController.getStaffList( null, null, genderChoice,0);
                    }
                    else if(choice2==4)
                    {
                        System.out.println("please give the age filter");
                        int ageChoice = verifyInt();
                        AdminController.getStaffList(null, null, null, ageChoice);
                    }
                    else
                        System.out.println("invalid choice!!");
                    break;
                case ASSIGN_MANAGERS:
                    System.out.println("please give the userid and the branch assigned");
                    String userid1 = verifyString();
                    String branch = verifyString();
                    AdminController.assignManager(userid1,branch);
                    break;
                case PROMOTE_STAFF_TO__MANAGER:
                    System.out.println("please give the userid");
                    String userid2 = verifyString();
                    AdminController.promoteToBranchManager(userid2);
                    break;
                case TRANSFER_STAFF_MANAGER:
                    System.out.println("please give the userid the employee you want to transfer");
                    String userid3 = verifyString();
                    System.out.println("please give the name of new branch you want to transfer");
                    String branchName = verifyString();
                    AdminController.transferEmployee(userid3,branchName);
                    break;
                case MANAGE_PAYMENT_METHOD:
                    System.out.println("1.add payment method");
                    System.out.println("2.remove payment method");
                    int choice3 = ScannerCheck.verifySelection(1, 2);
                    if(choice3==1) {
                        System.out.println("please give the new payment method name.");
                        Payment newpaymentmethod = new Payment(ScannerCheck.verifyString());
                        OrdersController.addPaymentMethod(newpaymentmethod);
                    }else if(choice3==2)
                    {
                        System.out.println("please give the new payment method name.");
                        Payment paymentmethod = new Payment(ScannerCheck.verifyString());
                        OrdersController.removePaymentMethod(paymentmethod);
                    }
                    break;
                case OPEN_CLOSE_BRANCH:
                    System.out.println("1.open new branch");
                    System.out.println("2.close existed branch");
                    int choice4 = ScannerCheck.verifySelection(1, 2);
                    if(choice4==1) {
                        System.out.println("please give the new branch's name.");
                        String newBranchName = verifyString();
                        System.out.println("please give the new branch's location.");
                        String newBranchLocation = verifyString();
                        BranchController.openBranches(newBranchName,newBranchLocation,null,4,0,0,1);
                    }else if(choice4==2)
                    {
                        System.out.println("please give the branch name.");
                        String BranchName = verifyString();
                        BranchController.closeBranches(BranchName);
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (selection<=0 || selection>7);
    }
        }
        


