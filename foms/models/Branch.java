package foms.models;

import java.io.Serializable;
import java.util.ArrayList;

// Li Min

public class Branch implements Serializable{
    private String name;
    private String location;
    private int staffQuota;
    private int staffCount;
    private int managerCount;
    private int managerQuota;
    public ArrayList<MenuItem> menuItemsList = new ArrayList<>();
    public static ArrayList<Payment> paymentList = new ArrayList<Payment>() {{
        add(new Payment("Paynow"));
        add(new Payment("Credit / Debit Card"));
        add(new Payment("PayPal"));
    }};


    public Branch(String name, String location) {
        this.name = name;
        this.location = location;
    }


    
    public int getStaffCount() {
        return staffCount;
    }


    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
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

    public void setStaffQuota(int staffQuota) {
        this.staffQuota=staffQuota;

    }
    public int getManagerCount() {
        return managerCount;
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

    
    public void setManagerCount(int managerCount) {
        this.managerCount = managerCount;
    }


   
    public int getManagerQuota(String branch) {
        return managerQuota;
    }

    
    public void setManagerQuota(int managerQuota) {
        this.managerQuota=managerQuota;
    }

    public static ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public static void setPaymentList(ArrayList<Payment> paymentList1) {
        paymentList = paymentList1;
    }

    public static boolean addPaymentMethod(Payment newPaymentMethod) {
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
        return "Branch{name = " + name + ", location = " + location + ", menuItemsList = " + menuItemsList + ", staffQuota = " + staffQuota + ", staffCount = " + staffCount + ", paymentList = " + paymentList + ", managerCount = " + managerCount + ", managerQuota = " + managerQuota + "}";
    }
}
