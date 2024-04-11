package foms.models;

import java.io.Serializable;

// Li Min



public class Admin extends Employee implements Serializable{

    public Admin(String role, String name, String gender, int age, String userid,String branch) {
        super(role, name, gender, age, userid,branch);


    }
}
