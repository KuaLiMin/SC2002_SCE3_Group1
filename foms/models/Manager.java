package foms.models;

import java.io.Serializable;

// Li Min

public class Manager extends Staff implements Serializable{
    
    // Constructor
    public Manager(String role, String name, String gender, int age, String userid, String branch) {
        super(role, name, gender, age, userid, branch);
    }
    
}
