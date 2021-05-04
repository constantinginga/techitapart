package mediator;

import model.CartItem;
import model.Product;
import model.Role;
import model.UserProfile;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.RemoteSubject;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteModel extends RemoteSubject<String, Integer> {

    /**
     * Account
     **/
    UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role) throws RemoteException;

    boolean login(String username, String password) throws RemoteException;

    /**
     * Category
     **/
    ArrayList<String> getAllCategory() throws RemoteException;

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

    ArrayList<Product> searchForProducts(String productName) throws RemoteException;

    File getImage(String url) throws RemoteException;

    void uploadImage(File file, String filePath) throws RemoteException;

    /**
     * update cart
     **/
    void addProductToCart(Product product, int quantity) throws RemoteException;

    void updateCartItemQuantity(CartItem cartItem, int quantity, String username) throws RemoteException;

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

}
