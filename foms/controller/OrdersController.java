package foms.controller;

import foms.models.Order;

import java.util.ArrayList;


public class OrdersController {
    //arraylist of all orders
    private static final ArrayList<Order> orderList = FileIO.getOrderList();
    
}
