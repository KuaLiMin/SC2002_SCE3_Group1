package foms.models;

import java.io.Serializable;

public class Payment implements Serializable{
  private String paymentName;

  public String getName() {
    return paymentName;
  }

  public void setName(String paymentName) {
    this.paymentName = paymentName;
  }
}
