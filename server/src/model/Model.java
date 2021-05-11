package model;

import utility.observer.subject.LocalSubject;

import java.io.File;
import java.util.ArrayList;

public interface Model extends LocalSubject<String, Integer>
{

    /** Account **/
    UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role );
    UserProfile login(String username, String password);
    UserProfile getUserProfile();
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
    void addProductToCart (Product product,int quantity);
    void updateCartItemQuantity(CartItem cartItem, int quantity, String username);
    void removeProductFromCart(CartItem cartItem, String username);
    void buy(String username);

    /** this method will be deleted after implementing the cart shop **/
    void buyProduct(Product product, int quantity, String categoryName, String userName);

    /** order **/
    void addOrder();
}
