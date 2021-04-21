package model;


import java.util.ArrayList;

public class OrderList {
    private ArrayList<Order> orders;

    public OrderList(){
        orders = new ArrayList<>();
    }


    public void addOrder(Order order){
        orders.add(order);
    }

    public Order getOrder(int id){
        for (Order order1: orders){
            if (order1.getOrder_id() == id) return order1;
        }

        return null;
    }

    public ArrayList<Order> getAllOrders(){
        return orders;
    }


}
