package foms.models;

import java.io.Serializable;

import foms.enums.UserRole;

// Li Min

public abstract class Employee extends User implements Serializable{
    private String name;
    private String gender;
    private int age;
    private String userId;
    private String password = "password";
    private String branch;


    public Employee(String role, String name, String gender, int age, String userid,String branch) {
        super(role);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.userId = userid;
        this.branch=branch;
    }

    // Getters
    //getrole
    public UserRole getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
    public String getBranch() {
        return branch;
    }

    // Setters
    public void setRole(String role) {
        this.role = UserRole.valueOf(role);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserid(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
