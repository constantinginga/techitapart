package model;

import model.CartItem;

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

    public void set(ArrayList<CartItem> cartItems)
    {
        this.cartItems = cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void updateItemQuantity(CartItem cartItem, int quantity) {
        for (CartItem cartItem1: cartItems) {
            if (cartItem1.getProduct().getId().equals(cartItem.getProduct().getId())) {
                int newQuantity = cartItem1.getQuantity() + quantity;
                if (newQuantity < 1) {
                    throw new IllegalArgumentException("Quantity too low");
                }
                if (newQuantity > cartItem1.getProduct().getTotal_quantity()) {
                    throw new IllegalArgumentException("Quantity too high");
                }
                cartItem1.setQuantity(newQuantity);
            }
        }
    }

    public void removeCartItemById(int id) {
        cartItems.removeIf(cartItem1 -> cartItem1.getId() == id);
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }
}
