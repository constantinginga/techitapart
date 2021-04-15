package model;

import persistence.CartDB;

public class CartItem {
    private Product product;
    private int quantity;
    private int id;

    public CartItem(Product product, int quantity, String username) {
        this.product = product;
        this.quantity = quantity;
        CartDB cartDB = new CartDB();
        cartDB.addProduct(Integer.parseInt(product.getId()), quantity, username);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
