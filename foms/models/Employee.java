package foms.models;

// Li Min

public class Employee extends User{
    private String name;
    private String gender;
    private int age;
    private String userid;
    private String password = "password";

    // Constructor
    public Employee(String role, String name, String gender, int age, String userid) {
        super(role);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.userid = userid;
    }

    // Getters
    //getrole
    public String getRole() {
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

    // Setters
    public void setRole(String role) {
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


    // Add other methods here
}
