package viewmodel;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.LocalModel;
import model.Product;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.rmi.RemoteException;

/**
 * The  Market admin view model.
 */
public class MarketAdminViewModel implements LocalSubject<String, Integer>, LocalListener<String, Integer> {
    private final LocalModel model;
    private final ViewState state;
    private final PropertyChangeAction<String, Integer> property;
    private final StringProperty searchBar;
    /**
     * The Products .
     */
    ObservableList<Product> products = FXCollections.observableArrayList();
    /**
     * The Categories.
     */
    ObservableList<Category> categories = FXCollections.observableArrayList();
    private final Gson gson;


    /**
     * Instantiates a new Market admin view model.
     *
     * @param model     the model
     * @param viewState the view state
     */
    public MarketAdminViewModel(LocalModel model, ViewState viewState) {
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
     * Resets the view.
     */
    public void reset() {
        getData();
    }

    /**
     * Gets image .
     *
     * @param url the url of image
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
     * Gets  all products and categories from model.
     */
    public void getData() {
        try {
            products = FXCollections.observableArrayList(model.getAllProductsInCategory(state.categoryName));
            categories = FXCollections.observableArrayList(model.getAllCategories());
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Searches the product.
     */
    public void search() {
        products = FXCollections.observableArrayList(model.searchForProducts(searchBar.get()));
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {

        Platform.runLater(() -> {
            if (event.getPropertyName().contains("Product")) {
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

