package mediator;

import model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.RemoteListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientModelManager implements ClientModel, RemoteListener<String, Integer> {
    private final RemoteModel server;
    private final PropertyChangeAction<String, Integer> property;


    public ClientModelManager(LocalModel model) throws RemoteException, NotBoundException, MalformedURLException {
        this.server = (RemoteModel) Naming.lookup("rmi://localhost:1099/shop");
        UnicastRemoteObject.exportObject(this, 0);
        server.addListener(this);
        property = new PropertyChangeProxy<>(this, true);
    }

    @Override
    public User registerUSer(User user) throws RemoteException {
        return server.registerUSer(user);
    }

    @Override
    public User login(String username, String password) throws RemoteException {
        return server.login(username, password);
    }

    @Override
    public User getUser(String username) throws RemoteException {
        return server.getUser(username);
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        server.updateUser(user);
    }

    @Override
    public ArrayList<String> getAllUsernames() throws RemoteException {
        return server.getAllUsernames();
    }

    @Override
    public ArrayList<String> getAllCategory() throws RemoteException {
        return server.getAllCategory();
    }

    @Override
    public Category getCategory(String name) throws RemoteException {
        return server.getCategory(name);
    }

    @Override
    public ArrayList<Category> getAllCategories()
            throws RemoteException {
        return server.getAllCategories();
    }

    @Override
    public void addProduct(Product product, String categoryName) throws RemoteException {
        System.out.println("AddProduct fire propertyChange in ClientModelManager");
        server.addProduct(product, categoryName);
    }

    @Override
    public Product getProduct(String id, String categoryName) throws RemoteException {
        return server.getProduct(id, categoryName);
    }

    @Override
    public void removeProduct(String id, String categoryName) throws RemoteException {
        System.out.println("RemoveProduct fire propertyChange in ClientModelManager");
        server.removeProduct(id, categoryName);
    }

    @Override
    public void updateProductQuantity(String id, int quantity, String categoryName) throws RemoteException {
        server.updateProductQuantity(id, quantity, categoryName);
    }

    @Override
    public void updateProductPrice(String id, double price, String categoryName) throws RemoteException {
        server.updateProductPrice(id, price, categoryName);
    }

    @Override
    public ArrayList<Product> getAllProductsInCategory(String categoryName) throws RemoteException {
        return server.getAllProductsInCategory(categoryName);
    }

    @Override
    public ArrayList<Product> getAllProducts() throws RemoteException {
        return server.getAllProducts();
    }

    @Override
    public File getImage(String url) {
        return server.getImage(url);
    }

    @Override
    public void uploadImage(File file, String filePath) throws RemoteException {
        server.uploadImage(file, filePath);
    }

    @Override
    public ArrayList<Product> searchForProducts(String productName) {
        return server.searchForProducts(productName);
    }

    @Override
    public void addProductToCart(Product product, int quantity, String username) throws RemoteException {
        server.addProductToCart(product, quantity, username);
        System.out.println("FROM REMOTE CLIENT");
    }

    @Override
    public ArrayList<CartItem> getProductsFromCart(String username) throws RemoteException {
        return server.getProductsFromCart(username);
    }

    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity, String username) throws RemoteException {
        server.updateCartItemQuantity(cartItem, quantity, username);
    }

    @Override
    public void removeProductFromCart(CartItem cartItem, String username) throws RemoteException {
        server.removeProductFromCart(cartItem, username);
    }

    @Override
    public void buy(String username) throws RemoteException {
        server.buy(username);
    }


    @Override
    public void close() throws NoSuchObjectException {
        property.close();
        UnicastRemoteObject.unexportObject(this, true);
    }

    @Override
    public ArrayList<Order> getAllOrdersByUsername(String username) {
        return server.getAllOrdersByUsername(username);
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) throws RemoteException {
        System.out.println("Fire property change in ClientModelManager");
        property.firePropertyChange(event);
    }

    @Override
    public boolean addListener(
            GeneralListener<String, Integer> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(
            GeneralListener<String, Integer> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
