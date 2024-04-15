package foms.models;

import foms.fileio.*;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * Represents a branch in the system.
 * A branch has a name, location, staff count, staff quota, manager count, manager quota, menu items list, employee list, and payment list.
 * The branch class provides methods to set and get the attributes of a branch.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class Branch implements Serializable{
    private String name;
    private String location;
    private int staffCount = 0;
    private int staffQuota;
    private int managerCount = 0;
    private int managerQuota;
    public ArrayList<MenuItem> menuItemsList = new ArrayList<>();
    public ArrayList<Employee> employeeList = FileIO.getEmployeeList();
    public static ArrayList<Payment> paymentList = new ArrayList<Payment>() {{
        add(new Payment("Paynow"));
        add(new Payment("Credit / Debit Card"));
        add(new Payment("PayPal"));
    }};
    
    /**
     * Constructs a new Branch object with the specified name, location, and staff quota.
     * The manager quota is set based on the staff quota.
     * 
     * @param name The name of the branch.
     * @param location The location of the branch.
     * @param staffQuota The staff quota of the branch.
     */
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

    /**
     * Sets the staff quota of the branch.
     * 
     * @param staffQuota The staff quota of the branch.
     */
    public void setStaffQuota(int staffQuota){
        this.staffQuota = staffQuota;
    }

    /**
     * Gets the staff quota of the branch.
     * 
     * @return The staff quota of the branch.
     */
    public int getStaffQuota(){
        return staffQuota;
    }

    /**
     * Sets the staff count of the branch.
     * 
     * @param count The staff count of the branch.
     */
    public void setStaffCount(int count){
        this.staffCount = count;
    }

    /**
     * Gets the name of the branch.
     * 
     * @return The name of the branch.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the branch.
     * 
     * @param name The name of the branch.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location of the branch.
     * 
     * @return The location of the branch.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the branch.
     * 
     * @param location The location of the branch.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the staff quota of the branch.
     * 
     * @param newBranchName The name of the branch.
     * @return The staff quota of the branch.
     */
    public  int getStaffQuota(String newBranchName) {return staffQuota;}

    /**
     * Sets the manager count of the branch.
     * 
     * @param count The manager count of the branch.
     */
    public void setManagerCount(int count) {
        this.managerCount = count;
    }

    /**
     * Gets the menu items list of the branch.
     * 
     * @return The menu items list of the branch.
     */
    public ArrayList<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    /**
     * Adds a menu item to the menu items list of the branch.
     * 
     * @param menuItem The menu item to be added.
     */
    public void addMenuItem(MenuItem menuItem) {
        menuItemsList.add(menuItem);
    }

    /**
     * Sets the menu items list of the branch.
     * 
     * @param menuItemsList The menu items list of the branch.
     */
    public void setMenuItemsList(ArrayList<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }

    /**
     * Gets the manager quota of the branch.
     * 
     * @return The manager quota of the branch.
     */
    public int getManagerQuota() {
        return managerQuota;
    }

    /**
     * Sets the manager quota of the branch.
     * 
     * @param managerQuota The manager quota of the branch.
     */
    public void setManagerQuota(int managerQuota) {
        this.managerQuota=managerQuota;
    }

    /**
     * Gets the payment list of the branch.
     * 
     * @return The payment list of the branch.
     */
    public static ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    /**
     * Sets the payment list of the branch.
     * 
     * @param paymentList1 The payment list of the branch.
     */
    public static void setPaymentList(ArrayList<Payment> paymentList1) {
        paymentList = paymentList1;
    }

    /**
     * Adds a payment method to the payment list of the branch.
     * 
     * @param newPaymentMethodName The name of the new payment method.
     * @return true if the payment method is added successfully, false otherwise.
     */
    public static boolean addPaymentMethod(String newPaymentMethodName) {
        Payment newPaymentMethod = new Payment(newPaymentMethodName);
        
        if (paymentList.add(newPaymentMethod)) {
            return true;
        }
        return false;
    }

    /**
     * Removes a payment method from the payment list of the branch.
     * 
     * @param paymentMethod The name of the payment method to be removed.
     * @return true if the payment method is removed successfully, false otherwise.
     */
    public static boolean removePaymentMethod(String paymentMethod) {
        for (Payment payment : paymentList) {
            if (payment.getName().equals(paymentMethod)) {
                paymentList.remove(payment);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns a string representation of the branch.
     * 
     * @return A string representation of the branch.
     */
    public String toString() {
        return "Branch{name = " + name + ", location = " + location + ", menuItemsList = " + menuItemsList + ", staffCount = " + staffCount + ", paymentList = " + paymentList + ", managerCount = " + managerCount + ", managerQuota = " + managerQuota + "}";
    }
}
