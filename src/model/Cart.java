package model;

import persistence.CartDB;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> cartItems;

    public Cart(String username) {
        CartDB cartDB = new CartDB();
        this.cartItems = cartDB.getAllProducts(username);
    }

    public CartItem getCartItem(int index){
        return cartItems.get(index);
    }

    public void set(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
