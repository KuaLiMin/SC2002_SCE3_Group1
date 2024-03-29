package foms.models;

// Li Min


public class MenuItem {
    private String name;
    private double price;
    private Branch branch;
    private String category;

    // Constructor
    public MenuItem(String name, double price, String branch, String category) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
    }

    // Getters and Setters
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
}
