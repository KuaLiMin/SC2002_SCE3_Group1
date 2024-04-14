package foms.models;

import foms.enums.UserRole;

import java.io.Serializable;


/**
 * Represents an abstract class for an Employee. An Employee is a User with additional attributes such as name.
 * An Employee is a User with additional attributes such as name, gender, age, userId, password, and branch.
 * This class provides getters and setters for these attributes.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public abstract class Employee extends User implements Serializable{
    private String name;
    private String gender;
    private int age;
    private String userId;
    private String password = "password";
    private String branch;

    /**
     * Constructs a new Employee object with the specified role, name, gender, age, userId, and branch.
     * @param role The role of the Employee.
     * @param name The name of the Employee.
     * @param gender The gender of the Employee.
     * @param age The age of the Employee.
     * @param userid The userId of the Employee.
     * @param branch The branch of the Employee.
     */
    public Employee(String role, String name, String gender, int age, String userid,String branch) {
        super(role);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.userId = userid;
        this.branch=branch;
    }

    /**
     * Gets the role of the Employee.
     * @return The role of the Employee.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Gets the role of the Employee as a string.
     * @return The role of the Employee as a string.
     */
    public String getRoleInString(){
        switch (role) {
            case S:
                return "Staff";
            case M:
                return "Manager";
            case A:
                return "Admin";
            default:
                return "Unknown";
        }
    }

    /**
     * Gets the name of the Employee.
     * @return The name of the Employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the gender of the Employee.
     * @return The gender of the Employee.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the age of the Employee.
     * @return The age of the Employee.
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the userId of the Employee.
     * @return The userId of the Employee.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the password of the Employee.
     * @return The password of the Employee.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the branch of the Employee.
     * @return The branch of the Employee.
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the role of the Employee.
     * @param role The role of the Employee.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Sets the name of the Employee.
     * @param name The name of the Employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the gender of the Employee.
     * @param gender The gender of the Employee.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the age of the Employee.
     * @param age The age of the Employee.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the userId of the Employee.
     * @param userId The userId of the Employee.
     */
    public void setUserid(String userId) {
        this.userId = userId;
    }

    /**
     * Sets the password of the Employee.
     * @param password The password of the Employee.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the branch of the Employee.
     * @param branch The branch of the Employee.
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
}
