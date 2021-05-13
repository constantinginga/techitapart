package model;

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
        for (CartItem cartItem1: cartItems){
            if (cartItem1.getId() == cartItem.getId()){
                cartItem1.setQuantity(quantity);
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
