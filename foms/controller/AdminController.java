package foms.controller;

import foms.fileio.FileIO;
import foms.models.*;
import foms.enums.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {
    public static boolean useridExit;
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    private static ArrayList<Branch> branchList = FileIO.getBranchList();

    public static boolean addStaff(String role, String name, String gender, int age, String userId, String branch) {
        Staff staff = new Staff(role, name, gender, age, userId, branch);
        boolean exists = employeeList.stream().anyMatch(e -> e.getUserid().equals(staff.getUserid()));
        if (!exists) {
            employeeList.add(staff);
            // 数据不持久化到文件
            return true; // 添加成功
        }
        return false; // 员工已存在，添加失败
    }

    public static boolean editStaff(String userId, String role, String name, String gender, int age, String userId1, String branch) {
        Staff staff = new Staff(role, name, gender, age, userId1, branch);
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getUserid().equals(userId)) {
                employeeList.set(i, staff);
                // 数据不持久化到文件
                return true; // 编辑成功
            }
        }
        return false; // 没有找到员工，编辑失败
    }

    public static boolean removeStaff(String userId) {
        boolean removed = employeeList.removeIf(staff -> staff.getUserid().equals(userId));
        // 数据不持久化到文件
        return removed; // 返回删除操作的结果
    }

    public static List<Employee> getStaffList(String branch, UserRole role, String gender, int age) {
        return employeeList.stream()
                .filter(staff -> (branch == null || staff.getBranch().equals(branch)) &&
                        (role == null || staff.getRole() == role) &&
                        (gender == null || staff.getGender().equals(gender)) &&
                        (age ==0 || staff.getAge()==age))
                .collect(Collectors.toList());
    }


    public static boolean assignManager(String userId, String branchName) {
        // 找到目标分支实例
        Branch branch = findBranchByName(branchName);
        if (branch != null) {
            int currentManagerCount = branch.getManagerCount(branchName);
            if (currentManagerCount < branch.getManagerQuota(branchName)) {
                for (Employee emp : employeeList) {
                    if (emp.getUserid().equals(userId) && emp.getRole() == UserRole.M) {
                        emp.setBranch(branchName);
                        branch.setManagerCount(currentManagerCount + 1); // 更新经理数量
                        // 注意：这里没有处理数据持久化逻辑
                        return true;
                    }
                }
            }
        }return false;
    }

    // 帮助方法：通过名称查找分支
    private static Branch findBranchByName(String branchName) {
        for (Branch branch : branchList) {
            if (branch.getName().equals(branchName)) {
                return branch;
            }
        }
        return null; // 如果没有找到，返回null
    }



    public static boolean promoteToBranchManager(String userId) {
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserid().equals(userId))
                .findFirst();
        if (employeeOptional.isPresent()) {
            Employee emp = employeeOptional.get();
            if (emp.getRole() != UserRole.S) {
                // 如果不是普通员工，返回false
                return false; // 员工不是普通员工，不能提升为分支经理
            }
            emp.setRole("M");
            // 数据不持久化到文件
            return true; // 操作成功
        }
        return false;
    }

    public static boolean transferEmployee(String userId, String newBranchName) {
        // 首先，找到新分支的Branch实例
        Branch newBranch = findBranchByName(newBranchName);
        if (newBranch == null) {
            System.out.println("Branch not found.");
            return false; // 如果找不到分支，直接返回操作失败
        }

        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserid().equals(userId))
                .findFirst();
        if (employeeOptional.isPresent()) {
            Employee emp = employeeOptional.get();
            // 判断要转移的员工是经理还是普通员工
            if (emp.getRole() == UserRole.M) {
                // 如果是经理，检查新分支的经理配额
                if (newBranch.getManagerCount(newBranchName) < newBranch.getManagerQuota(newBranchName)) {
                    emp.setBranch(newBranchName);
                    newBranch.setManagerCount(newBranch.getManagerCount(newBranchName) + 1); // 更新新分支的经理数量
                    return true; // 操作成功
                }
            } else if (emp.getRole() == UserRole.S) {
                // 如果是普通员工，检查新分支的员工配额
                if (newBranch.getStaffCount() < newBranch.getStaffQuota(newBranchName)) {
                    emp.setBranch(newBranchName);
                    newBranch.setStaffCount(newBranch.getStaffCount() + 1); // 更新新分支的员工数量
                    return true; // 操作成功
                }
            }
        }
        return false; // 员工不存在或新分支已达到配额，操作失败
    }
    public static boolean useridExit(String userId){
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserid().equals(userId))
                .findFirst();
        if (employeeOptional.isPresent())
            return true;
        return false;
    }
}

