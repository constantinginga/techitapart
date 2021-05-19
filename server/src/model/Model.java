package model;

import utility.observer.subject.LocalSubject;

import java.io.File;
import java.util.ArrayList;

public interface Model extends LocalSubject<String, Integer>
{

    /** Account **/
    User registerUSer(User user);
    User login(String username, String password);
    User getUser(String username);
    void updateUser(User user);
    ArrayList<String> getAllUsernames();


    /** Category **/
    ArrayList<String> getAllCategory();
    Category getCategory(String name);
    ArrayList<Category> getAllCategories();

    /** Product **/
    void addProduct(Product product, String categoryName);
    Product getProduct(String id, String categoryName);
    void removeProduct(String id, String categoryName);
    void updateProductQuantity(String id,int quantity, String categoryName);
    void updateProductPrice(String id,double price, String categoryName);
    ArrayList<Product> getAllProductsInCategory(String categoryName);
    ArrayList<Product> getAllProducts();
    File getImage(String url);
    void uploadImage(File file, String fileName);
    ArrayList<Product> searchForProducts(String productName);
    /** update cart **/
    void addProductToCart (Product product,int quantity, String username);
    ArrayList<CartItem> getProductsFromCart(String username);
    void updateCartItemQuantity(CartItem cartItem, int quantity, String username);
    void removeProductFromCart(CartItem cartItem, String username);
    void decreaseProductQuantity(String id, int quantity);
    void buy(String username);


    /** order **/
    ArrayList<Order> getAllOrdersByUsername(String username);
}
