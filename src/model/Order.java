package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
    private String order_id;
    private String username;
    private LocalDate date;
    public Order(String username,  String date){
        this.username = username;
        this.date = LocalDate.parse(date);
    }
    public Order(String username){
        this.username = username;
        this.date = LocalDate.now();
    }

    public void setOrder_id(String orderId) {
        this.order_id = order_id;
    }

    public void setUser_id(String username) {
        this.username = username;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString(){
        return order_id +"-"+username+"-"+date;
    }
}
