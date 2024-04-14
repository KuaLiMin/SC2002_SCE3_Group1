package foms.models;

import java.io.Serializable;

/**
 * Represents a Manager, which is a type of Staff.
 * A Manager is an Employee with additional attributes such as role, name, gender, age, userId, and branch.
 * This class provides a constructor to create a new Manager object.
 * 
 * @author  Kua Li Min
 * @version 1.0
 * @since   2024-04-15
 */

public class Manager extends Staff implements Serializable{
    /**
     * Constructs a new Manager object with the specified role, name, gender, age, userId, and branch.
     * 
     * @param role The role of the Manager.
     * @param name The name of the Manager.
     * @param gender The gender of the Manager.
     * @param age The age of the Manager.
     * @param userid The userId of the Manager.
     * @param branch The branch of the Manager.
     */
    public Manager(String role, String name, String gender, int age, String userid, String branch) {
        super(role, name, gender, age, userid, branch);
    }
}
