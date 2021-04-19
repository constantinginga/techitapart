package model;

import java.util.ArrayList;

public class UserProfile {
    private String username;
    private OrderList orderList;
    private Cart cart;

    public UserProfile(String username){
        this.username = username;
        this.orderList = new OrderList();
        this.cart = new Cart();
    }


    public void addProductToCart(Product product, int quantity) {
        cart.addCartItem(new CartItem(product, quantity));
    }

    public void updateCartItemQuantity(CartItem cartItem, int quantity) {
        cart.updateItemQuantity(cartItem, quantity);
    }


    public void removeCartItem(CartItem cartItem) {
        cart.removeCartItemById(cartItem.getId());
    }

    public ArrayList<CartItem> getAllCartItem(){
        return cart.getCartItems();
    }

    public void addOrder(Order order){
        orderList.addOrder(order);
    }

    public Order getOrderById(int id){
        return orderList.getOrder(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}
