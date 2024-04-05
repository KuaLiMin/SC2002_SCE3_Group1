package foms.models;



public class Customer extends User{
    private Order order;

    public Customer() {
        super("C");
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
