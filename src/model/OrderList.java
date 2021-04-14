package model;

import persistence.OrderDB;
import persistence.OrderPersistence;

import java.util.ArrayList;

public class OrderList {
    private ArrayList<Order> orders;
    private OrderPersistence orderDB;

    public OrderList(){
        orders = new ArrayList<>();
        orderDB = new OrderDB();
        orders = orderDB.getAllOrderDB();
    }


    public void addOrder(Order order){
        orders.add(order);
    }

    public Order getOrder(String id){
        for (Order order1: orders){
            if (order1.getOrder_id().equals(id)) return order1;
        }

        return null;
    }

    public ArrayList<Order> getAllOrders(){
        return orders;
    }


}
