package viewmodel;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.rmi.RemoteException;


/**
 * The  Market user view model.
 */
public class MarketUserViewModel implements LocalSubject<String, Integer>, LocalListener<String, Integer> {
    private LocalModel model;
    private ViewState state;
    private StringProperty searchBar;
    private Gson gson;
    private PropertyChangeAction<String, Integer> property;
    /**
     * The Products.
     */
    ObservableList<Product> products = FXCollections.observableArrayList();
    /**
     * The Categories.
     */
    ObservableList<Category> categories = FXCollections.observableArrayList();

    /**
     * Instantiates a new Market user view model.
     *
     * @param model     the model
     * @param viewState the view state
     */
    public MarketUserViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.searchBar = new SimpleStringProperty();
        model.addListener(this);
        getData();
        gson = new Gson();
        property = new PropertyChangeProxy<>(this);
    }

    /**
     * Gets products.
     *
     * @return the products
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    public ObservableList<Category> getCategories() {
        return categories;
    }

    /**
     * Search bar property string property.
     *
     * @return the string property
     */
    public StringProperty searchBarProperty() {
        return searchBar;
    }


    /**
     * Reset.
     */
    public void reset() {
        System.out.println(state.getUserID());
        getData();
    }

    /**
     * Searches for the products.
     */
    public void search() {
        products = FXCollections.observableArrayList(model.searchForProducts(searchBar.get()));
    }

    /**
     * Gets image.
     *
     * @param url the url
     * @return the image
     */
    public File getImage(String url) {
        try {
            return model.getImage(url);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Gets Get all products and categories.
     */
    public void getData() {
        //Get all products and categories from model
        try {
            products = FXCollections.observableArrayList(model.getAllProductsInCategory(state.categoryName));
            categories = FXCollections.observableArrayList(model.getAllCategories());
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        Platform.runLater(() -> {
            if (event.getPropertyName().contains("Product")) {
                System.out.println("From Market->>>>>>>>>>>>>"+ event.getPropertyName());
                if (event.getPropertyName().equals("addProduct")) {
                    Product product = gson.fromJson(event.getValue1(), Product.class);
                    products.add(product);
                } else if (event.getPropertyName().equals("removeProduct")) {
                    String id = event.getValue1();
                    products.removeIf(p -> p.getId().equals(id));
                }
                getData();
                property.firePropertyChange(event);
            }
        });
    }

    @Override
    public boolean addListener(GeneralListener<String, Integer> listener, String... propertyNames) {
        return property.addListener(listener);
    }

    @Override
    public boolean removeListener(GeneralListener<String, Integer> listener, String... propertyNames) {
        return property.removeListener(listener);
    }
}
