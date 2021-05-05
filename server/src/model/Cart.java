package model;

import persistence.CartDB;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    private ArrayList<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public CartItem getCartItem(int index){
        return cartItems.get(index);
    }

    public void set(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCartItem(CartItem cartItem) {
    }

    public void updateItemQuantity(CartItem cartItem, int quantity) {
    }

    public void removeCartItemById(int id) {
    }

    public ArrayList<CartItem> getCartItems() {
        return null;
    }
}
