package foms.models;

import java.io.Serializable;

import foms.enums.UserRole;

// Li Min

public abstract class Employee extends User implements Serializable{
    private String name;
    private String gender;
    private int age;
    private String userid;
    private String password = "password";
    private String branch;


    public Employee(String role, String name, String gender, int age, String userid,String branch) {
        super(role);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.userid = userid;
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

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }
    public String getBranch() {
        return branch;
    }



    // Setters
    public void setRole(UserRole role) {
        this.role = role;
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

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
