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


public class MarketUserViewModel implements LocalSubject<String, Integer>, LocalListener<String, Integer> {
    private LocalModel model;
    private ViewState state;
    private StringProperty searchBar;
    private Gson gson;
    private PropertyChangeAction<String, Integer> property;
    ObservableList<Product> products = FXCollections.observableArrayList();
    ObservableList<Category> categories = FXCollections.observableArrayList();

    public MarketUserViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.searchBar = new SimpleStringProperty();
        model.addListener(this);
        getData();
        gson = new Gson();
        property = new PropertyChangeProxy<>(this);
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public StringProperty searchBarProperty() {
        return searchBar;
    }


    public void reset() {
        System.out.println(state.getUserID());
        getData();
    }

    public void search() {
        products = FXCollections.observableArrayList(model.searchForProducts(searchBar.get()));
    }

    public File getImage(String url) {
        try {
            return model.getImage(url);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;

    }

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
