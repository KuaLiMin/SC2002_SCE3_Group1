package foms.models;

import java.io.Serializable;


public class Payment implements Serializable {
  private String name;

  public Payment() {
  }

  public Payment(String name) {
    this.name = name;
  }

  /**
   * 获取
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 设置
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  public String toString() {
    return "Payment{name = " + name + "}";
  }
}
