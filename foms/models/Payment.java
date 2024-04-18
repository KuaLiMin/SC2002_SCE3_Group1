package foms.models;

import java.io.Serializable;

/**
 * Represents a payment method in the Food Order Management System (FOMS).
 * This class contains details about the payment method used in transactions.
 * 
 * @author Chen Ziyan
 * @version 1.0
 * @since 2024-04-15
 */
public class Payment implements Serializable {
  /**
   * The name of the payment method.
   */
  private String name;

  /**
   * Constructs a new Payment instance with the specified name.
   *
   * @param name the name of the payment method
   */
  public Payment(String name) {
    this.name = name;
  }

  /**
   * Retrieves the name of this payment method.
   *
   * @return the payment method name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this payment method.
   *
   * @param name the new name for the payment method
   */
  public void setName(String name) {
    this.name = name;
  }
}
