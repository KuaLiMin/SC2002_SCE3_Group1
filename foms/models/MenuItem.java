package foms.models;

import java.io.Serializable;

// Li Min


public class MenuItem implements Serializable{
    private String name;
    private double price;
    private String branch;
    private String category;
    private boolean availability = true; // Added availability attribute
    private String description;


    // Constructor
    public MenuItem(String name, double price, String branch, String category, String describtion , boolean availability) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.description = describtion;
        this.availability = availability;
    }
    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption){
        this.description = desciption;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getAvailablity() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
