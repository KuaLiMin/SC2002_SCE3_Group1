package foms.controller;

import foms.fileio.FileIO; // 注意，现在这个import可能不再需要
import foms.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();

    public static void addStaff(String role, String name, String gender, int age, String userId, String branch) {
        // 检查员工是否已存在
        Staff staff = new Staff(role,  name, gender, age, userId, branch);
        boolean exists = employeeList.stream().anyMatch(e -> e.getUserid().equals(staff.getUserid()));
        if (!exists) {
            employeeList.add(staff);
            // 数据不持久化到文件
        }
    }

    public static void editStaff(String userId, String role, String name, String gender, int age, String userId1, String branch) {
        Staff staff = new Staff(role,  name, gender, age, userId1, branch);
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getUserid().equals(userId)) {
                employeeList.set(i, staff);
                // 数据不持久化到文件
                break;
            }
        }
    }

    public static void removeStaff(String userId) {
        employeeList.removeIf(staff -> staff.getUserid().equals(userId));
        // 数据不持久化到文件
    }

    public static List<Employee> getStaffList(String branch, String role, String gender, int age) {
        return employeeList.stream()
                .filter(staff -> (branch == null || staff.getBranch().equals(branch)) &&
                        (role == null || staff.getRole().equals(role)) &&
                        (gender == null || staff.getGender().equals(gender)) &&
                        (age ==0 || staff.getAge()==age))
                .collect(Collectors.toList());
    }

    public static void assignManager(String userId, String branch) {
        // 检查经理配额约束
        for (Employee emp : employeeList) {
            if (emp.getUserid().equals(userId) && emp.getRole().equals("Manager") && getManagerCount(branch) < Branch.getManagerQuota(branch)) {
                emp.setBranch(branch);
                // 数据不持久化到文件
                break;
            }
        }
    }



    public static int getManagerCount(String branch) {
        return (int) employeeList.stream()
                .filter(emp -> emp.getBranch().equals(branch) && emp.getRole().equals("Manager"))
                .count();
    }

    public static int getStaffCount(String branch) {
        return (int) employeeList.stream()
                .filter(emp -> emp.getBranch().equals(branch) && emp.getRole().equals("Staff"))
                .count();
    }

    public static void promoteToBranchManager(String userId) {
        employeeList.stream()
                .filter(emp -> emp.getUserid().equals(userId))
                .findFirst()
                .ifPresent(emp -> {
                    emp.setRole("Manager");
                    // 数据不持久化到文件
                });
    }

    public static void transferEmployee(String userId, String newBranch) {
        employeeList.stream()
                .filter(emp -> emp.getUserid().equals(userId))
                .findFirst()
                .ifPresent(emp -> {
                    if (emp.getRole().equals("Manager") && getManagerCount(newBranch) < Branch.getManagerQuota(newBranch)) {
                        emp.setBranch(newBranch);
                    } else if (emp.getRole().equals("Staff") && getStaffCount(newBranch) < Branch.getStaffQuota(newBranch)) {
                        emp.setBranch(newBranch);
                    }
                    // 数据不持久化到文件
                });
    }
}
