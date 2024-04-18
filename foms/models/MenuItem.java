package foms.models;

import java.io.Serializable;

/**
 * Represents a menu item in a restaurant.
 * A menu item has a name, price, branch, category, availability, and description.
 * Provides getters and setters for these attributes.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */
public class MenuItem implements Serializable{
    /**
     * The name of this MenuItem.
     */
    private String name;

    /**
     * The price of this MenuItem.
     */
    private double price;

    /**
     * The branch where this MenuItem is available.
     */
    private String branch;

    /**
     * The category of this MenuItem.
     */
    private String category;

    /**
     * The availability of this MenuItem. Default is true.
     */
    private boolean availability = true; 

    /**
     * The description of this MenuItem.
     */
    private String description;

    /**
     * Constructs a new MenuItem object with the specified name, price, branch, category, description, and availability.
     * 
     * @param name The name of the menu item.
     * @param price The price of the menu item.
     * @param branch The branch of the menu item.
     * @param category The category of the menu item.
     * @param description The description of the menu item.
     * @param availability The availability of the menu item.
     */
    public MenuItem(String name, double price, String branch, String category, String description, boolean availability) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.description = description;
        this.availability = availability;
    }

    /**
     * Gets the name of the menu item.
     * 
     * @return The name of the menu item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the menu item.
     * 
     * @param name The name of the menu item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the menu item.
     * 
     * @return The price of the menu item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the menu item.
     * 
     * @param price The price of the menu item.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the branch of the menu item.
     * 
     * @return The branch of the menu item.
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the branch of the menu item.
     * 
     * @param branch The branch of the menu item.
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Gets the category of the menu item.
     * 
     * @return The category of the menu item.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the menu item.
     * 
     * @param category The category of the menu item.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the availability of the menu item.
     * 
     * @return The availability of the menu item.
     */
    public boolean getAvailability() {
        return availability;
    }

    /**
     * Sets the availability of the menu item.
     * 
     * @param availability The availability of the menu item.
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    /**
     * Gets the description of the menu item.
     * 
     * @return The description of the menu item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the menu item.
     * 
     * @param description The description of the menu item.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
