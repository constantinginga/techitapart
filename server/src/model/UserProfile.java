package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProfile implements Serializable {
    private static Map<String, UserProfile> map = new HashMap<>();
    private String username;
    private OrderList orderList;
    private Cart cart;


    private UserProfile(String username) {
        this.username = username;
        this.orderList = new OrderList();
        this.cart = new Cart();
    }


    public static UserProfile getInstance(String key) {
        UserProfile instance = map.get(key);
        if (instance == null) {
            synchronized (map) {
                instance = map.get(key);
                if (instance == null) {
                    instance = new UserProfile(key);
                    map.put(key, instance);
                }
            }
        }
        return instance;
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

    public ArrayList<CartItem> getAllCartItem() {
        return cart.getCartItems();
    }

    public void addOrder(Order order) {
        orderList.addOrder(order);
    }

    public Order getOrderById(int id) {
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

