package viewmodel;

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

public class MarketAdminViewModel implements LocalSubject<String, Integer>, LocalListener<String, Integer> {
    private LocalModel model;
    private ViewState state;
    private PropertyChangeAction<String, Integer> property;
    private StringProperty searchBar;
    ObservableList<Product> products = FXCollections.observableArrayList();
    ObservableList<Category> categories = FXCollections.observableArrayList();


    public MarketAdminViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.property = new PropertyChangeProxy<>(this);
        this.searchBar = new SimpleStringProperty();
        model.addListener(this);
        getData();
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
        getData();
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


    public void search() {
        products = FXCollections.observableArrayList(model.searchForProducts(searchBar.get()));
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        getData();
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

