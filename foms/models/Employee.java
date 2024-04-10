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


    public Employee(String role, String name, String gender, int age, String userid) {
        super(role);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.userId = userid;
    }

    // Getters
    // public String getBranch(){
    //     return branch;
    // }
    public UserRole getRole() {
        return role;
    }

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

    

}
