package foms.models;

import java.io.Serializable;

import foms.enums.UserRole;

/**
 * Abstract representation of a user in the Food Order Management System (FOMS).
 * This class provides common functionality for all user types by defining a user's role in the system. 
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public abstract class User implements Serializable{
    /**
     * The user's role in the system.
     */
    protected UserRole role;

    /**
     * Constructs a user and sets the user's role based on the provided role string.
     * The role string must match one of the predefined roles in the UserRole enum.
     *
     * @param role a string representation of the user's role
     * @throws IllegalArgumentException if the role string does not correspond to an enum constant in UserRole
     */
    public User(String role) {
        this.role = UserRole.valueOf(role);
    }

    /**
     * Retrieves the user's role in the system.
     *
     * @return the user's role as a UserRole enum constant
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the user's role. The provided role string must match one of the predefined roles in the UserRole enum.
     *
     * @param role a string representation of the user's role
     * @throws IllegalArgumentException if the role string does not correspond to an enum constant in UserRole
     */
    public void setRole(String role) {
        this.role = UserRole.valueOf(role);
    }
}
