package foms.controller;

import foms.enums.UserRole;
import foms.fileio.FileIO;
import foms.models.Branch;
import foms.models.Employee;
import foms.models.Staff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminController {
    public static boolean useridExit;
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    private static ArrayList<Branch> branchList = FileIO.getBranchList();

    public static boolean addStaff(String role, String name, String gender, int age, String userId, String branchName) {
        boolean exists = employeeList.stream().anyMatch(e -> e.getUserid().equals(userId));
        if (!exists) {
            Branch targetBranch = branchList.stream()
                                             .filter(b -> b.getName().equals(branchName))
                                             .findFirst()
                                             .orElse(null);
            if (targetBranch != null) {
                Staff staff = new Staff(role, name, gender, age, userId, branchName);
                employeeList.add(staff);
                if ("S".equals(role)) {
                    targetBranch.setStaffCount(targetBranch.getStaffCount() + 1);
                } else if ("M".equals(role)) {
                    targetBranch.setManagerCount(targetBranch.getManagerCount() + 1);
                }
                // 适当的数据持久化逻辑
                return true; // 添加成功
            }
        }
        return false; // 员工已存在或找不到指定分支，添加失败
    }
    
    public static boolean editStaff(String userId, String newRoleStr, String newName, String newGender, int newAge, String newUserId, String newBranchName) {
        for (Employee emp : employeeList) {
            if (emp.getUserid().equals(userId)) {
                // Found the employee
                Branch oldBranch = findBranchByName(emp.getBranch());
                Branch newBranch = findBranchByName(newBranchName);
                UserRole oldRole = emp.getRole();
                UserRole newRole = UserRole.valueOf(newRoleStr.toUpperCase());
    
                // Check if branch or role changed
                boolean branchChanged = !emp.getBranch().equals(newBranchName);
                boolean roleChanged = oldRole != newRole;
    
                // Adjust counts in the old branch if there's a change
                if (oldBranch != null && (branchChanged || roleChanged)) {
                    updateBranchStaffCount(oldBranch.getName(), oldRole, false);
                }
    
                // Update employee information
                emp.setRole(newRole); // Assuming Employee.setRole accepts UserRole enum directly
                emp.setName(newName);
                emp.setGender(newGender);
                emp.setAge(newAge);
                emp.setUserid(newUserId); // 注意：确保userId的唯一性
                emp.setBranch(newBranchName);
    
                // Adjust counts in the new branch if there's a change
                if (newBranch != null && (branchChanged || roleChanged)) {
                    updateBranchStaffCount(newBranch.getName(), newRole, true);
                }
    
                // 数据持久化逻辑 (略)
                return true; // 成功编辑员工
            }
        }
    
        return false; // 未找到员工，编辑失败
    }
    
    public static void updateBranchStaffCount(String branchName, UserRole role, boolean increase) {
        Branch branch = findBranchByName(branchName);
    
        // 确认分支存在
        if (branch != null) {
            if (role == UserRole.S) {
                // 更新普通员工计数
                int newCount = branch.getStaffCount() + (increase ? 1 : -1);
                branch.setStaffCount(Math.max(0, newCount)); // 防止计数变负
            } else if (role == UserRole.M) {
                // 更新管理者计数
                int newCount = branch.getManagerCount() + (increase ? 1 : -1);
                branch.setManagerCount(Math.max(0, newCount)); // 防止计数变负
            }
            // 数据持久化逻辑 (略)
        } else {
            System.out.println("Branch not found: " + branchName);
            // 这里可以抛出异常或者进行其他错误处理
        }
    }
    
    
    

    public static boolean removeStaff(String userId) {
        Employee staffToRemove = employeeList.stream()
                                          .filter(staff -> staff.getUserid().equals(userId))
                                          .findFirst()
                                          .orElse(null);
        if (staffToRemove != null) {
            boolean removed = employeeList.remove(staffToRemove);
            if (removed) {
                // 假设有方法根据员工所在的分支名找到分支实例
                Branch branch = findBranchByName(staffToRemove.getBranch());
                if (branch != null) {
                    if ("S".equals(staffToRemove.getRole())) {
                        branch.setStaffCount(branch.getStaffCount() - 1);
                    } else if ("M".equals(staffToRemove.getRole())) {
                        branch.setManagerCount(branch.getManagerCount() - 1);
                    }
                    // 适当的数据持久化逻辑
                }
            }
            return removed; // 返回删除操作的结果
        }
        return false; // 找不到员工，删除失败
    }
    

    public static List<Employee> displayFilteredStaffList(String branch, UserRole role, String gender, int age) {
        return employeeList.stream()
        .filter(emp -> (branch == null || Optional.ofNullable(emp.getBranch()).map(b -> b.equals(branch)).orElse(false)) &&
                (role == null || emp.getRole() == role) &&
                (gender == null || emp.getGender().equalsIgnoreCase(gender)) &&
                (age == 0 || emp.getAge() == age))
        .collect(Collectors.toList());
                
    }
    public static void getStaffList(String branch, UserRole role, String gender, int age) {
        List<Employee> filteredEmployees = displayFilteredStaffList(branch, role, gender, age);

        if (filteredEmployees.isEmpty()) {
            System.out.println("No employees found with the specified filters.");
        } else {
            System.out.println("\nFiltered employees:");
            for (Employee emp : filteredEmployees) {
                String employeeDetails = String.format(
                        "Name: %s | Role: %s | Gender: %s | Age: %d | Branch: %s",
                        emp.getName(),
                        emp.getRole(), // Assuming getRole() returns a UserRole which is properly overridden to return a string representation.
                        emp.getGender(),
                        emp.getAge(),
                        emp.getBranch()
                );
                System.out.println(employeeDetails);
            }
        }
    }

    public static boolean assignManager(String userId, String branchName) {
        // 找到目标分支实例
        Branch targetBranch = findBranchByName(branchName);
        if (targetBranch != null) {
            // 获取目标分支当前的管理者数量
            int currentManagerCount = targetBranch.getManagerCount();
            // 计算目标分支的管理者配额
            int managerQuota = targetBranch.getManagerQuota(branchName);
            
            if (currentManagerCount < managerQuota) {
                for (Employee emp : employeeList) {
                    if (emp.getUserid().equals(userId) && emp.getRole() == UserRole.M) {
                        // 检查员工当前是否已经分配到某个分支
                        String previousBranchName = emp.getBranch();
                        if (previousBranchName != null && !previousBranchName.isEmpty()) {
                            // 找到原先的分支并减少其管理者计数
                            Branch previousBranch = findBranchByName(previousBranchName);
                            if (previousBranch != null) {
                                int prevManagerCount = previousBranch.getManagerCount();
                                previousBranch.setManagerCount(prevManagerCount - 1); // 减少原分支的管理者数量
                            }
                        }
                        // 为员工设置新的分支
                        emp.setBranch(branchName);
                        // 更新目标分支的管理者数量
                        targetBranch.setManagerCount(currentManagerCount + 1);
                        // 注意：这里没有处理数据持久化逻辑
                        return true; // 成功分配管理者
                    }
                }
            }
        }
        return false; // 分配管理者失败
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
                if (newBranch.getManagerCount() < calculateManagerQuota(newBranchName)) {
                    emp.setBranch(newBranchName);
                    newBranch.setManagerCount(newBranch.getManagerCount() + 1); // 更新新分支的经理数量
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
    public static int calculateManagerQuota(String branchName) {
        // 从现有分支列表中找到对应的分支
        Branch branch = branchList.stream()
                                  .filter(b -> b.getName().equals(branchName))
                                  .findFirst()
                                  .orElse(null);
    
        if (branch == null) {
            System.out.println("Branch not found: " + branchName);
            return 0; // 分支未找到时，可能需要适当处理这种情况
        }
    
        // 使用找到的分支实例来获取员工配额
        int staffQuota = branch.getStaffQuota(branchName);
    
        // 根据员工配额计算管理者配额
        int managerQuota;
        if (staffQuota >= 1 && staffQuota <= 4) {
            managerQuota = 1;
        } else if (staffQuota >= 5 && staffQuota <= 8) {
            managerQuota = 2;
        } else if (staffQuota >= 9 && staffQuota <= 15) {
            managerQuota = 3;
        } else {
            // 对于staffQuota超过15的情况，根据需要定义逻辑
            managerQuota = 3; // 例如，这里假设超过15名员工的最多有3名管理者
        }
    
        // 返回计算出的管理者配额
        return managerQuota;
    }
    
    
}

