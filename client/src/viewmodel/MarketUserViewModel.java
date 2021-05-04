package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LocalModel;
import model.Product;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MarketUserViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty searchBar;
    ObservableList<Product> products = FXCollections.observableArrayList();

    public MarketUserViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.searchBar = new SimpleStringProperty();
        getData();
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public StringProperty searchBarProperty() {
        return searchBar;
    }

    public void reset() {
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
        //Get all products from model
        try {
            products = FXCollections.observableArrayList(model.getAllProducts());
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }
}
