package mediator;

import model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RemoteModelManager implements RemoteModel, LocalListener<String, Integer> {
    private final Model model;
    private final PropertyChangeProxy<String, Integer> property;

    public RemoteModelManager(Model model) throws RemoteException, MalformedURLException {
        startRegistry();
        this.model = model;
        model.addListener(this);
        startServer();
        this.property = new PropertyChangeProxy<>(this, true);
    }

    private void startRegistry() throws RemoteException {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (java.rmi.server.ExportException e) {
            System.out.println("Registry already started? " + e.getMessage());
        }
    }

    private void startServer() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("shop", this);
        System.out.println("Server started...");
    }

    @Override
    public User registerUSer(User user) throws RemoteException {
        return model.registerUSer(user);
    }

    @Override
    public User login(String username, String password) throws RemoteException {
        return model.login(username, password);
    }

    @Override
    public User getUser(String username) throws RemoteException {
        return model.getUser(username);
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        model.updateUser(user);
    }

    @Override
    public ArrayList<String> getAllUsernames() throws RemoteException {
        return model.getAllUsernames();
    }

    @Override
    public ArrayList<String> getAllCategory() throws RemoteException {
        return model.getAllCategory();
    }

    @Override
    public Category getCategory(String name) throws RemoteException {
        return model.getCategory(name);
    }

    @Override
    public ArrayList<Category> getAllCategories() throws RemoteException {
        return model.getAllCategories();
    }

    @Override
    public void addProduct(Product product, String categoryName) throws RemoteException {
        model.addProduct(product, categoryName);
    }

    @Override
    public Product getProduct(String id, String categoryName) throws RemoteException {
        return model.getProduct(id, categoryName);
    }

    @Override
    public void removeProduct(String id, String categoryName) throws RemoteException {
        model.removeProduct(id, categoryName);
    }

    @Override
    public void updateProductQuantity(String id, int quantity, String categoryName) throws RemoteException {
        model.updateProductQuantity(id, quantity, categoryName);
    }

    @Override
    public void updateProductPrice(String id, double price, String categoryName) throws RemoteException {
        model.updateProductPrice(id, price, categoryName);
    }

    @Override
    public ArrayList<Product> getAllProductsInCategory(String categoryName) throws RemoteException {
        return model.getAllProductsInCategory(categoryName);
    }

    @Override
    public ArrayList<Product> getAllProducts() throws RemoteException {
        return model.getAllProducts();
    }

    @Override
    public ArrayList<Product> searchForProducts(String productName) throws RemoteException {
        return model.searchForProducts(productName);
    }

    @Override
    public File getImage(String url) throws RemoteException {
        return model.getImage(url);
    }

    @Override
    public void uploadImage(File file, String filePath) throws RemoteException {
        model.uploadImage(file, filePath);
    }

    @Override
    public void addProductToCart(Product product, int quantity, String username) throws RemoteException {
        model.addProductToCart(product, quantity, username);
    }

    @Override
    public ArrayList<CartItem> getProductsFromCart(String username) throws RemoteException {
        return model.getProductsFromCart(username);
    }

    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity, String username) throws RemoteException {
        model.updateCartItemQuantity(cartItem, quantity, username);
    }

    @Override
    public void removeProductFromCart(CartItem cartItem, String username) throws RemoteException {
        model.removeProductFromCart(cartItem, username);
    }

    @Override
    public void buy(String username) throws RemoteException {
        model.buy(username);
    }


    @Override
    public ArrayList<Order> getAllOrdersByUsername(String username)
            throws RemoteException {
        return model.getAllOrdersByUsername(username);
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        property.firePropertyChange(event.getPropertyName(), event.getValue1(), event.getValue2());
    }

    @Override
    public boolean addListener(
            GeneralListener<String, Integer> listener, String... propertyNames)
            throws RemoteException {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(
            GeneralListener<String, Integer> listener, String... propertyNames)
            throws RemoteException {
        return property.removeListener(listener, propertyNames);
    }
}
