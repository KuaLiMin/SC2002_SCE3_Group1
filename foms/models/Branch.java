package foms.models;

import java.util.ArrayList;

// Li Min

public class Branch{
    private String name;
    private String location;
    private ArrayList<MenuItem> menuItemsList = new ArrayList<MenuItem>();
    private int staffQuota;
    private ArrayList<Payment> paymentList = new ArrayList<Payment>();



    // Constructor
    public Branch(String name, String location, int staffQuota, ArrayList<Payment> paymentList) {
        this.name = name;
        this.location = location;
        this.staffQuota = staffQuota;
        this.paymentList = paymentList;
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

    public int getStaffQuota() {
        return staffQuota;
    }

    public void setStaffQuota(int staffQuota) {
        this.staffQuota = staffQuota;
    }

    public ArrayList<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList<Payment>) {
        this.paymentList = paymentList;
    }

    // REMOVE LATER
    public void addMenuItem(MenuItem menuItem) {
        menuItemsList.add(menuItem);
    }
}
