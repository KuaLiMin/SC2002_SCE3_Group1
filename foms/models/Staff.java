package foms.models;

import java.io.Serializable;

// Li Min

public class Staff extends Employee implements Serializable{
    private String branch;

    // Constructor
    public Staff(String role, String name, String gender, int age, String userid, String branch) {
        super(role, name, gender, age, userid);
        this.branch = branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getBranch() {
        return branch;
    }
}

    // Getters and setters
   /* public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
 
}
*/