package foms.models;

import java.io.Serializable;

import foms.enums.UserRole;


public abstract class User implements Serializable{
    protected UserRole role;

    public User(String role) {
        this.role = UserRole.valueOf(role);
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = UserRole.valueOf(role);
    }
}
