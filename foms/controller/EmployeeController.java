package foms.controller;
import foms.models.Branch;
import foms.models.Employee;
import foms.models.Manager;
import foms.models.Staff;
import foms.fileio.FileIO;
import java.util.ArrayList;
import foms.enums.UserRole;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


public class EmployeeController {
    public static boolean useridExit;
    private static ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    protected static final ArrayList<Branch> branchList = FileIO.getBranchList();

    public static void displayStaffList(String branchToDisplay) {
        System.out.println("Staff in " + branchToDisplay + ":\n");
    
        // Display table headers
        System.out.printf("%-10s | %-15s | %-10s | %-5s | %-10s\n", "Role", "Name", "Gender", "Age", "UserId");
        System.out.println("-------------------------------------------------------------");
    
        for (Employee employee : employeeList) {
            if (employee instanceof Staff) {
                Staff staff = (Staff) employee;
                if (staff.getBranch().equals(branchToDisplay)) {
                    String roleString = "";
                    switch (employee.getRole()) {
                        case S:
                            roleString = "Staff";
                            break;
                        case M:
                            roleString = "Manager";
                            break;
                        case A:
                            roleString = "Admin";
                            break;
                        default:
                            roleString = "Unknown";
                            break;
                    }
                    // Display employee details in a formatted table
                    System.out.printf("%-10s | %-15s | %-10s | %-5s | %-10s\n",
                            roleString, employee.getName(), employee.getGender(), employee.getAge(), employee.getUserId());
                }
            }
        }
    }

    public static boolean addStaff(String role, String name, String gender, int age, String userId, String branchName) {
        Branch branch = findBranchByName(branchName);

        if (role.equals("M")) {
            int managerCount = branch.getManagerCount();
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
                System.out.println("after");
                return true; 
            }
        } else {
            int staffCount = branch.getStaffCount();

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

                // 数据不持久化到文件
                return true; // 添加成功
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

public static List<Employee> getStaffList(String branch, UserRole role, String gender, int age) {
    
    return employeeList.stream()
            .filter(employee -> {
                if (employee instanceof Staff) {
                    Staff staff = (Staff) employee; // Cast Employee to Staff
                    return (branch == null || staff.getBranch().equals(branch)) &&
                            (role == null || staff.getRole() == role) &&
                            (gender == null || staff.getGender().equals(gender)) &&
                            (age == 0 || staff.getAge() == age);
                }
                return false; 
            })
            .collect(Collectors.toList());
}


    public static boolean assignManager(String userId, String branchName) {
        // 找到目标分支实例
        Branch branch = findBranchByName(branchName);
        if (branch != null) {
            int currentManagerCount = branch.getManagerCount();

            if (currentManagerCount < branch.getManagerQuota()) {
                for (Employee employee : employeeList) {
                    if (employee instanceof Manager){
                        Manager manager = (Manager) employee;

                        if (manager.getUserId().equals(userId) && manager.getRole() == UserRole.M) {
                            manager.setBranch(branchName);
                            // branch.setManagerCount(currentManagerCount + 1); // 更新经理数量
                            // 注意：这里没有处理数据持久化逻辑
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
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
                .filter(emp -> emp.getUserId().equals(userId))
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
                .filter(emp -> emp.getUserId().equals(userId))
                .findFirst();
        
        if (employeeOptional.isPresent()) {
            Employee emp = employeeOptional.get();

            if (emp instanceof Manager) {
                // 如果是经理，检查新分支的经理配额
                Manager manager = (Manager) emp;
                if (newBranch.getManagerCount() < newBranch.getManagerQuota()) {
                    manager.setBranch(newBranchName);
                    // newBranch.setManagerCount(newBranch.getManagerCount() + 1); // 更新新分支的经理数量
                    return true; // 操作成功
                }
            } else if (emp instanceof Staff) {
                Staff staff = (Staff) emp;
                if (newBranch.getStaffCount() < newBranch.getStaffQuota()) {
                    staff.setBranch(newBranchName);
                    // newBranch.setStaffCount(newBranch.getStaffCount() + 1); 
                    return true; 
                }
            }
        }
        return false; // 员工不存在或新分支已达到配额，操作失败
    }

    public static boolean useridExit(String userId){
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(emp -> emp.getUserId().equals(userId))
                .findFirst();
        if (employeeOptional.isPresent())
            return true;
        return false;
    }

}