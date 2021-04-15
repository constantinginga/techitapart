package persistence;

import model.CartItem;
import model.Product;

import java.util.ArrayList;

public interface CartPersistence {
    ArrayList<CartItem> getOrderedProducts(int orderId);
    void addProduct(int cart_id, int quantity, String username);
    void removeProduct(int product_id, String username);
    void removeProduct(int cartItemId);
    ArrayList<CartItem> getAllProducts(String username);
}
