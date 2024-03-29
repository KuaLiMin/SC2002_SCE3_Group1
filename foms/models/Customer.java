package foms.models;

public class Customer {
    private Order order;

    public Customer() {
    }

    public void placeOrder(Order order) {
        this.order = order;
    }

}
