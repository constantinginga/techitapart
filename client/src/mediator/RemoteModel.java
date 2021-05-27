package mediator;

import model.*;
import utility.observer.subject.RemoteSubject;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteModel extends RemoteSubject<String, Integer> {

    /**
     * Account
     **/
    User registerUSer(User user) throws RemoteException;

    User login(String username, String password) throws RemoteException;

    User getUser(String username) throws RemoteException;

    void updateUser(User user) throws RemoteException;

    ArrayList<String> getAllUsernames() throws RemoteException;

    /**
     * Category
     **/
    ArrayList<String> getAllCategory() throws RemoteException;

    Category getCategory(String name) throws RemoteException;

    ArrayList<Category> getAllCategories() throws RemoteException;

    /**
     * Product
     **/
    void addProduct(Product product, String categoryName) throws RemoteException;

    Product getProduct(String id, String categoryName) throws RemoteException;

    void removeProduct(String id, String categoryName) throws RemoteException;

    void updateProductQuantity(String id, int quantity, String categoryName) throws RemoteException;

    void updateProductPrice(String id, double price, String categoryName) throws RemoteException;

    ArrayList<Product> getAllProductsInCategory(String categoryName) throws RemoteException;

    ArrayList<Product> getAllProducts() throws RemoteException;

    ArrayList<Product> searchForProducts(String productName);

    File getImage(String url);

    void uploadImage(File file, String filePath) throws RemoteException;

    /**
     * update cart
     **/
    void addProductToCart(Product product, int quantity, String username) throws RemoteException;

    void updateCartItemQuantity(CartItem cartItem, int quantity, String username) throws RemoteException;

    void removeProductFromCart(CartItem cartItem, String username) throws RemoteException;

    void buy(String username) throws RemoteException;

    ArrayList<CartItem> getProductsFromCart(String username) throws RemoteException;


    /**
     * order
     **/
    ArrayList<Order> getAllOrdersByUsername(String username);

}
