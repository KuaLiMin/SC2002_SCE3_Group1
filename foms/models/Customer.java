package foms.models;

/**
 * Represents a customer in the Food Order Management System (FOMS).
 * Inherits from the User class and may include additional customer-specific properties and methods.
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class Customer extends User{
    /**
     * The constructor for the Customer class.
     * Initializes a new Customer instance with a specific user type.
     */
    public Customer() {
        super("C");
    }
}
