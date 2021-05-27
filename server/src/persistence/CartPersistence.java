package persistence;

import model.CartItem;

import java.util.ArrayList;

public interface CartPersistence {
    ArrayList<CartItem> getOrderedProducts(int orderId);
    void addProductToCart(int product_id, int quantity, String username);
    void updateCartItemQuantity(int product_id, int quantity, String username);
    int cartItemExists(int product_id, String username);
    void removeCartItem(int product_id, String username);
    void removeCartItem(int cartItemId);
    ArrayList<CartItem> getAllProductsInCart(String username);
    void setOrderId(int orderId, String username);
}
