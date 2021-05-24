package model;

import model.CartItem;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Cart object for the client.
 */
public class Cart implements Serializable {
    private ArrayList<CartItem> cartItems;

    /**
     * Instantiates a new Cart.
     */
    public Cart() {
        this.cartItems = new ArrayList<>();
    }


    /**
     * Setter to set CartItem.
     *
     * @param cartItems the new CarItems
     */
    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * Add cart item.
     *
     * @param cartItem the cart item
     */
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    /**
     * Update item quantity.
     *
     * @param cartItem the cart item
     * @param quantity the quantity
     */
    public void updateItemQuantity(CartItem cartItem, int quantity) {
        for (CartItem cartItem1 : cartItems) {
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

    /**
     * Remove cart item by id.
     *
     * @param id the id of CartItem
     */
    public void removeCartItemById(int id) {
        cartItems.removeIf(cartItem1 -> cartItem1.getId() == id);
    }

    /**
     * Gets CartItem.
     *
     * @return the CartItem
     */
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }
}
