package foms.models;

<<<<<<< Updated upstream
import java.io.Serializable;

public class Payment implements Serializable{
  private String paymentName;

  public String getName() {
    return paymentName;
  }

  public void setName(String paymentName) {
    this.paymentName = paymentName;
=======
public class Payment {
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
>>>>>>> Stashed changes
  }
}
