package foms.models;

import java.io.Serializable;

// Li Min

public class Staff extends Employee implements Serializable{
  

    // Constructor
    public Staff(String role, String name, String gender, int age, String userid, String branch) {
        super(role, name, gender, age, userid,branch);
       
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