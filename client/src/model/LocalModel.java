package model;

import utility.observer.subject.LocalSubject;

import java.io.File;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LocalModel extends LocalSubject<String, Integer>
{

    /**
     * Account
     *
     * @return*/
    UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role) throws RemoteException;

    UserProfile login(String username, String password) throws RemoteException;

//    UserProfile getUserProfile() throws RemoteException;

    User getUser(String username) throws RemoteException;

    ArrayList<String> getAllUsernames() throws RemoteException;

    /**
     * Category
     **/
    ArrayList<String> getAllCategory() throws RemoteException;

    void updateUser(User user) throws RemoteException;

    Category getCategory(String name) throws RemoteException;

    ArrayList<Category> getAllCategories() throws RemoteException;

    /**
     * Product
     **/
    void addProduct(Product product, String categoryName) throws RemoteException;

    ArrayList<Order> getAllOrdersByUsername(String username);

    Product getProduct(String id, String categoryName) throws RemoteException;

    void removeProduct(String id, String categoryName) throws RemoteException;

    void updateProductQuantity(String id, int quantity, String categoryName) throws RemoteException;

    void updateProductPrice(String id, double price, String categoryName) throws RemoteException;

    ArrayList<Product> getAllProductsInCategory(String categoryName) throws RemoteException;

    ArrayList<Product> getAllProducts() throws RemoteException;

    File getImage(String url) throws RemoteException;

    void uploadImage(File file, String url);
    ArrayList<Product> searchForProducts(String productName);

    /**
     * update cart
     **/
    void addProductToCart(Product product, int quantity, String username) throws RemoteException;

    void updateCartItemQuantity(CartItem cartItem, int quantity, String username) throws RemoteException;

    ArrayList<CartItem> getProductsFromCart(String username) throws RemoteException;

    void removeProductFromCart(CartItem cartItem, String username) throws RemoteException;

    void buy(String username) throws RemoteException;

    /**
     * this method will be deleted after implementing the cart shop
     **/
    void buyProduct(Product product, int quantity, String categoryName, String userName) throws RemoteException;

    /**
     * order
     **/
    void addOrder() throws RemoteException;

    /**
     * closing of window
     */
    void close() throws NoSuchObjectException;
}
