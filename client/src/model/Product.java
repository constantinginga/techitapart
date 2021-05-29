package model;


import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int total_quantity;
    private String description;
    private double price;
    private String id;
    private String imgSrc;

    public Product(String name, String description) {
        if (name.equals("") || description.equals("")) {
            throw new IllegalArgumentException("Product name or description is  empty");
        }
        this.name = name;
        this.description = description;
        this.total_quantity = 0;
        this.price = 0.00;
        this.imgSrc = "default.png";

    }

    public Product(String name, String description, int total_quantity, double price) {
        if (name.equals("") || description.equals("")) {
            throw new IllegalArgumentException("Product name or description is  empty");
        }
        this.name = name;
        this.description = description;
        this.total_quantity = total_quantity;
        this.price = price;
        this.imgSrc = "default.png";
    }

    public Product(String id, String name, String description, int total_quantity, double price) {
        if (name.equals("") || description.equals("")) {
            throw new IllegalArgumentException("Product name or description is  empty");
        }
        this.name = name;
        this.description = description;
        this.total_quantity = total_quantity;
        this.price = price;
        this.id = id;
        this.imgSrc = "default.png";
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

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void decreaseQuantity(int quantity) {
        if (quantity > total_quantity) {
            throw new IllegalArgumentException(getName() + " out of stock, new quantity is: " + total_quantity);
        }
        total_quantity -= quantity;
    }

    @Override
    public String toString() {
        return "ProductId: " + getId() + ", Product name: " + getName() + ", Description: " + getDescription() + ", Price: " + getPrice() + ", total quantity: " + total_quantity;
    }
}
