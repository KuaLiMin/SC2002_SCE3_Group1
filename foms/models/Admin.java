package foms.models;

import java.io.Serializable;

/**
 * Represents an Admin, which is a type of Employee.
 * An Admin is an Employee with additional attributes such as role, name, gender, age, userId, and branch.
 * This class provides a constructor to create a new Admin object.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class Admin extends Employee implements Serializable{

    /**
     * Constructs a new Admin object with the specified role, name, gender, age, userId, and branch.
     * 
     * @param role The role of the Admin.
     * @param name The name of the Admin.
     * @param gender The gender of the Admin.
     * @param age The age of the Admin.
     * @param userid The userId of the Admin.
     * @param branch The branch of the Admin.
     */
    public Admin(String role, String name, String gender, int age, String userid,String branch) {
        super(role, name, gender, age, userid,branch);
    }
}
