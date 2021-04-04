package model;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class Product {
    private String name;
    private int total_quantity;
    private String description;
    private double price;
    private String id;
    private String imgSrc;

    public Product(String name, String description){
        if (name.equals("") || description.equals("")){
            throw new IllegalArgumentException("Product name or description is  empty");
        }
         this.name = name;
        this.description = description;
        this.total_quantity = 0;
        this.price = 0.00;

    }

    public Product(String name, String description, int total_quantity, double price){
        if (name.equals("") || description.equals("")){
            throw new IllegalArgumentException("Product name or description is  empty");
        }
        this.name = name;
        this.description = description;
        this.total_quantity = total_quantity;
        this.price = price;
        this.id = String.valueOf(LocalTime.now().getNano() + ThreadLocalRandom
            .current().nextInt(1, 100 + 1));

    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc()
    {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc)
    {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void buyProduct(int quantity){
        if (quantity < total_quantity){
            throw new IllegalArgumentException("quantity is not available in the stock");
        }
        total_quantity -= quantity;
    }


    @Override
    public String toString(){
       return "Product name: "+getName()+", Description: "+getDescription()+", Price: "+getPrice()+", total quantity: "+total_quantity;

    }
}
