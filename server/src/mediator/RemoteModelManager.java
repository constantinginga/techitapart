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
    private Model model;
    private PropertyChangeProxy<String, Integer> property;

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
    public UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role) throws RemoteException {
        return model.registerUSer(fName, lName, email, username, password, role);
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        return model.login(username, password);
    }

    @Override
    public ArrayList<String> getAllCategory() throws RemoteException {
        return model.getAllCategory();
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
    public void addProductToCart(Product product, int quantity) throws RemoteException {
        model.addProductToCart(product, quantity);
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
    public void buyProduct(Product product, int quantity, String categoryName, String userName) throws RemoteException {
        model.buyProduct(product, quantity, categoryName, userName);
        //model.updateProductQuantity(product.getId(), quantity, categoryName);
    }

    @Override
    public void addOrder() throws RemoteException {
        model.addOrder();
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        System.out.println("Fire property change in RemoteModelManager");
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
