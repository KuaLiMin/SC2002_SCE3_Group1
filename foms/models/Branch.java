package foms.models;

import foms.fileio.*;
import java.io.Serializable;
import java.util.ArrayList;

// Li Min

public class Branch implements Serializable{
    private String name;
    private String location;
    private int staffCount;
    private int staffQuota;
    private int managerCount;
    private int managerQuota;
    public ArrayList<MenuItem> menuItemsList = new ArrayList<>();
    public ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    public static ArrayList<Payment> paymentList = new ArrayList<Payment>() {{
        add(new Payment("Paynow"));
        add(new Payment("Credit / Debit Card"));
        add(new Payment("PayPal"));
    }};
    
    public Branch(String name, String location, int staffQuota) {
        this.name = name;
        this.location = location;       
        this.staffQuota = staffQuota;
        // Set managerQuota based on staffquota
        if (staffQuota >= 1 && staffQuota <= 4) {
            this.managerQuota = 1;
        } else if (staffQuota >= 5 && staffQuota <= 8) {
            this.managerQuota = 2;
        } else if (staffQuota >= 9 && staffQuota <= 15) {
            this.managerQuota = 3;
        } else {
            this.managerQuota = 0;
        }
    }

    public int getStaffCount() {
        long currentStaff = employeeList.stream()
            .filter(employee -> employee instanceof Staff)
            .map(employee -> (Staff) employee)
            .filter(staff -> staff.getBranch().equals(name))
            .count();
        this.staffCount = (int) currentStaff;
        return staffCount;
    }

    public void setStaffQuota(int staffQuota){
        this.staffQuota = staffQuota;
    }

    public int getStaffQuota(){
        return staffQuota;
    }
    public void setStaffCount(int count){
        this.staffCount = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public  int getStaffQuota(String newBranchName) {return staffQuota;}


    public void setManagerCount(int count) {
        this.managerCount = count;
    }

    public ArrayList<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    // REMOVE LATER
    public void addMenuItem(MenuItem menuItem) {
        menuItemsList.add(menuItem);
    }

    
    public void setMenuItemsList(ArrayList<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }
   
    public int getManagerQuota() {
        return managerQuota;
    }

    
    public void setManagerQuota(int managerQuota) {
        this.managerQuota=managerQuota;
    }

    public int getManagerCount(){
        long currentManagers = employeeList.stream()
            .filter(employee -> employee instanceof Manager)
            .map(employee -> (Manager) employee)
            .filter(manager -> manager.getBranch().equals(name))
            .count();
        this.managerCount = (int) currentManagers;
        return managerCount;
    }

    public static ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public static void setPaymentList(ArrayList<Payment> paymentList1) {
        paymentList = paymentList1;
    }

    public static boolean addPaymentMethod(String newPaymentMethodName) {
        Payment newPaymentMethod = new Payment(newPaymentMethodName);
        
        if (paymentList.add(newPaymentMethod)) {
            return true;
        }
        return false;
    }

    public static boolean removePaymentMethod(String paymentMethod) {
        for (Payment payment : paymentList) {
            if (payment.getName().equals(paymentMethod)) {
                paymentList.remove(payment);
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        return "Branch{name = " + name + ", location = " + location + ", menuItemsList = " + menuItemsList + ", staffCount = " + staffCount + ", paymentList = " + paymentList + ", managerCount = " + managerCount + ", managerQuota = " + managerQuota + "}";
    }
}
