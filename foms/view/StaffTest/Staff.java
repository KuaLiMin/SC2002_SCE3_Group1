import java.util.Scanner;

public class Staff {
    private String username;
    private String password;
    private char type;

    public Staff(String username, String password, char type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public char getType() {
        return type;
    }

    public void processOrder(Order order) {
        order.setStatus("Ready to pickup");
        System.out.println("Order " + order.getOrderID() + " has been processed and is now ready for pickup.");
    }



    public void displayNewOrders(Order[] orders) {
        System.out.println("New Orders:");
        for (Order order : orders) {
            if (order.getStatus().equals("New")) {
                System.out.println("OrderId: " + order.getOrderID() + ", itemName: " + order.getItemName()+ ", status: " + order.getStatus());
            }
        }
    }


    public boolean verifyPassword(String enteredPassword) {
        return enteredPassword.equals(password);
    }

    @Override
    public String toString() {
        return "Staff{" +
                "username='" + username + '\'' +
                ", type=" + type +
                '}';
    }
}
