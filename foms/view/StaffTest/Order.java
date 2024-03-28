public class Order {
    private int orderID;
    private String itemName;
    private String status;

    public Order(int orderID, String itemName, String status) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", itemName='" + itemName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
