package foms.controller;
import foms.models.Branch;
import foms.models.*;

import foms.fileio.FileIO;
import java.util.ArrayList;
import foms.enums.UserRole;

import static foms.controller.BranchController.selectBranchByName;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;


public class EmployeeController {
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();


    public static boolean addStaff(String role, String name, String gender, int age, String userId, String branchName) {
        Branch branch = BranchController.selectBranchByName(branchName);

        if (role.equals(UserRole.M.name())) {
            int managerCount = BranchController.getManagerCount(branchName);
            System.out.println("manager count before: " + BranchController.getManagerCount(branchName));
            if (managerCount >= branch.getManagerQuota()){
                System.out.println("Manager quota reached for branch " + branchName + ". Cannot add more managers.");
                return false;
            }

            Manager manager = new Manager(role, name, gender, age, userId, branchName);
            manager.setBranch(branchName);
            boolean exists = employeeList.stream().anyMatch(e -> e.getUserId().equals(manager.getUserId()));

            if (!exists) {
                employeeList.add(manager);
                branch.setManagerCount(managerCount + 1); 
                System.out.println("manager count after: " + BranchController.getManagerCount(branchName));
                return true; 
            }

        } else if (role.equals(UserRole.S.name())){
            int staffCount = BranchController.getStaffCount(branchName);
            System.out.println("staff count before: " + BranchController.getStaffCount(branchName));

            if (staffCount >= branch.getStaffQuota()){
                System.out.println("Staff quota reached for branch " + branchName + ". Cannot add more staff.");
                return false;
            }
            Staff staff = new Staff(role, name, gender, age, userId, branchName);
            staff.setBranch(branchName);
            boolean exists = employeeList.stream().anyMatch(e -> e.getUserId().equals(staff.getUserId()));
            if (!exists) {
                employeeList.add(staff);
                branch.setStaffCount(staffCount + 1); 
                System.out.println("staff count after: " + BranchController.getStaffCount(branchName));

                // 数据不持久化到文件
                return true; // 添加成功
            }
        } else {
            Admin admin = new Admin(role, name, gender, age, userId);
            boolean exists = employeeList.stream().anyMatch(e -> e.getUserId().equals(admin.getUserId()));

            if (!exists) {
                employeeList.add(admin);
                return true;
            }
        }

        return false; // 员工已存在，添加失败
    }
    
    public static boolean editStaff(String userId, String role, String name, String gender, int age, String userId1, String branch) {
        Staff staff = new Staff(role, name, gender, age, userId1, branch);
        staff.setBranch(branch);
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getUserId().equals(userId)) {
                employeeList.set(i, staff);
                // 数据不持久化到文件
                return true; // 编辑成功
            }
        }
        return false; // 没有找到员工，编辑失败
    }

    public static boolean removeStaff(String userId) {
        boolean removed = employeeList.removeIf(staff -> staff.getUserId().equals(userId));
        // 数据不持久化到文件
        return removed; // 返回删除操作的结果
    }

    public static boolean assignManager(String userId, String branchName) {
        // 找到目标分支实例
        Branch branch = BranchController.selectBranchByName(branchName);
        if (branch != null) {
            int currentManagerCount = BranchController.getManagerCount(branchName);

            if (currentManagerCount < branch.getManagerQuota()) {
                for (Employee employee : employeeList) {
                    if (employee instanceof Manager){
                        Manager manager = (Manager) employee;

                        if (manager.getUserId().equals(userId) && manager.getRole() == UserRole.M) {
                            manager.setBranch(branchName);
                            branch.setManagerCount(currentManagerCount + 1); // 更新经理数量
                            // 注意：这里没有处理数据持久化逻辑
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public static boolean promoteToBranchManager(String userId) {
        Optional<Staff> staffOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId) && emp instanceof Staff)
                .map(emp -> (Staff) emp)
                .findFirst();
    
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            int currentManagersCount = BranchController.getManagerCount(staff.getBranch());
            int maxManagersAllowed = selectBranchByName(staff.getBranch()).getManagerQuota();
    
            if (currentManagersCount < maxManagersAllowed && staff.getBranch()!= null) {
                // Promote staff to manager
                Manager promotedManager = new Manager("M", staff.getName(), staff.getGender(), staff.getAge(),staff.getUserId(),staff.getBranch());
                employeeList.add(promotedManager); // Add the promoted manager to the employeeList
                employeeList.remove(staff);

                return true; // Promotion successful
            } else {
                System.out.println("Manager quota reached, promotion failed");
                return false;
            }
        } else{
            System.out.println("Staff not found, promotion failed ");
        }
        return false;
    }

    public static boolean transferEmployee(String userId, String newBranchName) {
        // 首先，找到新分支的Branch实例
        Branch newBranch = BranchController.selectBranchByName(newBranchName);
        if (newBranch == null) {
            System.out.println("Branch not found.");
            return false; // 如果找不到分支，直接返回操作失败
        }

        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId))
                .findFirst();
        
        if (employeeOptional.isPresent()) {
            Employee emp = employeeOptional.get();

            if (emp instanceof Manager) {
                // 如果是经理，检查新分支的经理配额
                Manager manager = (Manager) emp;
                if (BranchController.getManagerCount(newBranchName) < newBranch.getManagerQuota()) {
                    manager.setBranch(newBranchName);
                    // newBranch.setManagerCount(newBranch.getManagerCount() + 1); // 更新新分支的经理数量
                    return true; // 操作成功
                } else {
                    System.out.println("Manager quota reached");
                    return false;
                }
            } else if (emp instanceof Staff) {
                Staff staff = (Staff) emp;
                if (BranchController.getStaffCount(newBranchName) < newBranch.getStaffQuota()) {
                    staff.setBranch(newBranchName);
                    // newBranch.setStaffCount(newBranch.getStaffCount() + 1); 
                    return true; 
                }else {
                    System.out.println("Staff quota reached");
                    return false;
                }
                
            }
        }
        return false; // 员工不存在或新分支已达到配额，操作失败
    }

    public static boolean userIdExit(String userId){
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId))
                .findFirst();
        if (employeeOptional.isPresent())
            return true;
        return false;
    }

    
    public static List<Staff> getAllStaffList() {
        List<Staff> staffList = employeeList.stream()
                .filter(employee -> employee instanceof Staff)
                .map(employee -> (Staff) employee)
                .collect(Collectors.toList());
    
        // Adjust the groupingBy to handle null branch names
        Map<String, List<Staff>> staffByBranchMap = staffList.stream()
                .collect(Collectors.groupingBy(staff -> {
                    String branch = staff.getBranch();
                    return (branch != null) ? branch : "No Branch";
                }));
    
        List<Staff> groupedStaffList = new ArrayList<>();
    
        // Iterate over the entries in the staffByBranchMap
        staffByBranchMap.forEach((branch, staffInBranch) -> {
            List<Staff> sortedStaff = staffInBranch.stream()
                    .sorted(Comparator.comparing(s -> s.getRole() == UserRole.M ? 0 : 1))
                    .collect(Collectors.toList());
    
            groupedStaffList.addAll(sortedStaff);
        });
    
        return groupedStaffList;
    }
    
  

    public static List<Staff> getStaffListByAttribute(String branch, UserRole role, String gender, int age) {
        return employeeList.stream()
                .filter(employee -> employee instanceof Staff)
                .map(employee -> (Staff) employee) 
                .filter(staff -> (branch == null || staff.getBranch().equals(branch)) &&
                                (role == null || staff.getRole() == role) &&
                                (gender == null || staff.getGender().equals(gender)) &&
                                (age == 0 || staff.getAge() == age))
                .collect(Collectors.toList());
    }

    public static void printStaffList(List<Staff> staffList) {
        System.out.printf("%-5s |%-10s | %-10s | %-20s | %-6s | %-5s | %-10s\n", "Index", "Role", "Branch", "Name", "Gender", "Age", "UserId");
        System.out.println("----------------------------------------------------------------------------------------------");
        int counter = 1;
        String branchName;
        for (Staff staff : staffList) {
            if (staff.getBranch() == null){
                branchName = "No Branch"; 
            } else {
                branchName = staff.getBranch();
            }
            System.out.printf("%-5s |%-10s | %-10s | %-20s | %-6s | %-5s | %-10s\n",
                    counter++ ,staff.getRoleInString(),branchName, staff.getName(), staff.getGender(), staff.getAge(), staff.getUserId());
        }
    }

    public static List<Staff> getStaffListInIncreasingAge() {
        List<Staff> staffList = employeeList.stream()
                .filter(employee -> employee instanceof Staff)
                .map(employee -> (Staff) employee)
                .sorted(Comparator.comparingInt(Staff::getAge))
                .collect(Collectors.toList());
        return staffList;
    }
    

}