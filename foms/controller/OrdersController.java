package foms.controller;

import foms.models.Customer;
import foms.models.Order;
import foms.fileio.FileIO;
import java.util.ArrayList;


public class OrdersController {
    //arraylist of all orders
    //private static final ArrayList<Order> orderList = FileIO.getOrderList();
   
    


    // get order list
    public static ArrayList<Order> getOrderList() {
        return new ArrayList<Order>();
    }




    public static Object calculateTotal(Order newOrder) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateTotal'");
    }




    public static Order[] getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }




    public static String getOrderStatus(String orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderStatus'");
    }




    public static void viewOrderDetails(String orderID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewOrderDetails'");
    }




    public static void setOrderReadyToPickup(String orderID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOrderReadyToPickup'");
    }




    public static boolean makeNewOrder(Customer customer) {
        return true;
    }


    
}
