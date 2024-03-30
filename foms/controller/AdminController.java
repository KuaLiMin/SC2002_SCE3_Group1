package foms.controller;

import foms.fileio.FileIO;
import foms.models.Admin;
import foms.models.Branch;
import foms.models.Manager;
import foms.models.Staff;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {
    private ArrayList<Staff> staffList;

    public AdminController() {
        this.staffList = FileIO.getStaffList();
    }


    public void addStaff(Staff staff) {
        // need to ensure the employee does not already exist
        // and that the employee details are valid before adding
        staffList.add(staff);
        // FileIO.savestaffList(staffList);
    }

    public void editStaff(String userId, Staff updatedStaff) {

        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getUserid().equals(userId)) {
                staffList.set(i, updatedStaff);
                break;
            }
        }
        // FileIO.savestaffList(staffList);
    }

    public void removeStaff(String userId) {
        staffList.removeIf(staff -> staff.getUserid().equals(userId));
        // FileIO.savestaffList(staffList);
    }

    public List<Staff> getStaffList(String branch, String role, String gender, Integer age) {
        return staffList.stream()
                .filter(staff -> (branch == null || staff.getBranch().equals(branch)) &&
                        (role == null || staff.getRole().equals(role)) &&
                        (gender == null || staff.getGender().equals(gender)) &&
                        (age == null || staff.getAge() == age))
                .collect(Collectors.toList());
    }
    public void assignManager(String userId, String branch) {
        // assign the employee with userId as a manager to the branch
        // need to chekc the quota/ratio constraint



    }

    public void promoteToBranchManager(String userId) {
        // promote the employee with userId to a branch manager
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getUserid().equals(userId)) {
                staffList.get(i).setRole("Manager");
                break;
            }
        }
    }
    public void transferEmployee(String userId, String newBranch) {
        // transfer the employee with userId to a new branch
    }





}
