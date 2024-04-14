package foms.models;

import java.io.Serializable;


/**
 * Represents a Staff, which is a type of Employee.
 * A Staff is an Employee with additional attributes such as role
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class Staff extends Employee implements Serializable{
  
    /**
     * Constructs a new Staff object with the specified role, name
     * 
     * @param role   The role of the Staff.
     * @param name   The name of the Staff.
     * @param gender The gender of the Staff.
     * @param age    The age of the Staff.
     * @param userid The user ID of the Staff.
     * @param branch The branch of the Staff.
     */
    public Staff(String role, String name, String gender, int age, String userid, String branch) {
        super(role, name, gender, age, userid,branch);
       
    }

}
