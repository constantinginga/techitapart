package model;

import mediator.ClientModelManager;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class LocalModelManager
        implements LocalModel, LocalListener<String, Integer> {

    private final ClientModelManager client;
    private final PropertyChangeAction<String, Integer> property;

    public LocalModelManager()
            throws IOException, NotBoundException {
        this.client = new ClientModelManager(this);
        this.property = new PropertyChangeProxy<>(this, true);
        client.addListener(this);
    }

    /**
     * Register And Login
     *
     * @return
     */
    // how will this login method work in server client system?
    @Override
    public User registerUSer(User user)
            throws RemoteException {
        return client.registerUSer(user);
    }

    @Override
    public User login(String username, String password)
            throws RemoteException {
        return client.login(username, password);
    }

    @Override
    public User getUser(String username) throws RemoteException {
        return client.getUser(username);
    }

    @Override
    public ArrayList<String> getAllUsernames() throws RemoteException {
        return client.getAllUsernames();
    }

    @Override
    public ArrayList<String> getAllCategory() throws RemoteException {
        return client.getAllCategory();
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        client.updateUser(user);
    }

    @Override
    public Category getCategory(String name) throws RemoteException {
        return client.getCategory(name);
    }

    @Override
    public ArrayList<Category> getAllCategories()
            throws RemoteException {
        return client.getAllCategories();
    }

    @Override
    public void addProduct(Product product, String categoryName)
            throws RemoteException {
        System.out.println("AddProduct fire propertyChange in LocalModelManager");
        client.addProduct(product, categoryName);
    }

    @Override
    public ArrayList<Order> getAllOrdersByUsername(String username) {
        return client.getAllOrdersByUsername(username);
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
    public void addProductToCart(Product product, int quantity, String username)
            throws RemoteException {
        client.addProductToCart(product, quantity, username);
        System.out.println("FROM CLIENT: ADDPRODUCT METHOD");
    }

    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity,
                                       String username) throws RemoteException {
        client.updateCartItemQuantity(cartItem, quantity, username);
    }

    @Override
    public ArrayList<CartItem> getProductsFromCart(String username) {
        try {
            return client.getProductsFromCart(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
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
