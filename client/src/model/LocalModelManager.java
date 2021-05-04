package model;

import mediator.ClientModelManager;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class LocalModelManager
        implements LocalModel, LocalListener<String, Integer> {

    private UserProfile userProfile;
    private ClientModelManager client;
    private PropertyChangeAction<String, Integer> property;

    public LocalModelManager()
            throws IOException, InterruptedException, NotBoundException {
        this.client = new ClientModelManager(this);
        this.property = new PropertyChangeProxy<>(this, true);
        client.addListener(this);
    }

    /**
     * Register And Login
     **/
    // how will this login method work in server client system?
    @Override
    public UserProfile registerUSer(String fName, String lName,
                                    String email, String username, String password, Role role)
            throws RemoteException {
        return client.registerUSer(fName, lName, email, username, password, role);
    }

    @Override
    public boolean login(String username, String password)
            throws RemoteException {
        return client.login(username, password);
    }

    @Override
    public ArrayList<String> getAllCategory() throws RemoteException {
        return client.getAllCategory();
    }

    @Override
    public void addProduct(Product product, String categoryName)
            throws RemoteException {
        System.out.println("AddProduct fire propertyChange in LocalModelManager");
        client.addProduct(product, categoryName);
    }

    @Override
    public Product getProduct(String id, String categoryName)
            throws RemoteException {
        return client.getProduct(id, categoryName);
    }

    @Override
    public void removeProduct(String id, String categoryName)
            throws RemoteException {
        System.out.println("RemoveProduct fire propertyChange in LocalModelManager");
        client.removeProduct(id, categoryName);
    }

    @Override
    public void updateProductQuantity(String id, int quantity,
                                      String categoryName) throws RemoteException {
        client.updateProductQuantity(id, quantity, categoryName);
    }

    @Override
    public void updateProductPrice(String id, double price,
                                   String categoryName) throws RemoteException {
        client.updateProductPrice(id, price, categoryName);
    }

    @Override
    public ArrayList<Product> getAllProductsInCategory(
            String categoryName) throws RemoteException {
        return client.getAllProductsInCategory(categoryName);
    }

    @Override
    public ArrayList<Product> getAllProducts() throws RemoteException {
        return client.getAllProducts();
    }

    @Override
    public File getImage(String url) {
        return client.getImage(url);
    }

    @Override
    public void uploadImage(File file, String url) {
        try {
            client.uploadImage(file, url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Product> searchForProducts(String productName) {
        return client.searchForProducts(productName);
    }

    @Override
    public void addProductToCart(Product product, int quantity)
            throws RemoteException {
        client.addProductToCart(product, quantity);
    }

    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity,
                                       String username) throws RemoteException {
        client.updateCartItemQuantity(cartItem, quantity, username);
    }

    @Override
    public void removeProductFromCart(CartItem cartItem,
                                      String username) throws RemoteException {
        client.removeProductFromCart(cartItem, username);
    }

    @Override
    public void buy(String username) throws RemoteException {
        client.buy(username);
    }

    @Override
    public void buyProduct(Product product, int quantity,
                           String categoryName, String userName) throws RemoteException {
        client.buyProduct(product, quantity, categoryName, userName);
    }

    @Override
    public void addOrder() throws RemoteException {
        client.addOrder();
    }

    @Override
    public void close() throws NoSuchObjectException {
        property.close();
        client.close();
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        System.out.println("Fire property change in LocalModelManager");
        property.firePropertyChange(event);
    }

    @Override
    public boolean addListener(GeneralListener<String, Integer> listener,
                               String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<String, Integer> listener,
                                  String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
