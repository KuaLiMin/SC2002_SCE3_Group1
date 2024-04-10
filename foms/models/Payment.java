package foms.models;

import java.io.Serializable;


public class Payment implements Serializable {
  private String name;

  public Payment(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
