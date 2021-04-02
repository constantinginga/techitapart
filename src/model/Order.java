package model;

import java.time.LocalTime;

public class Order {
    private String order_id;
    private String username;
    private DateTime date;
    public Order(String user_id, String order_id, DateTime date){
        this.username = user_id;
        this.order_id = order_id;
        this.date = date;
    }
    public Order(String username){
        this.username = username;
        this.date = new DateTime();
    }

    public void setOrder_id(String orderId) {
        this.order_id = order_id;
    }

    public void setUser_id(String username) {
        this.username = username;
    }


    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getUsername() {
        return username;
    }

    public DateTime getDate() {
        return date;
    }


    @Override
    public String toString(){
        return order_id +"-"+username+"-"+date;
    }
}
