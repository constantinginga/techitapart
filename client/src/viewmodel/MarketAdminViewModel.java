package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LocalModel;
import model.Product;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class MarketAdminViewModel {
    private LocalModel model;
    private ViewState state;
    ObservableList<Product> products = FXCollections.observableArrayList();

    public MarketAdminViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        getData();
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void reset() {
        System.out.println("Called reset");
        getData();
    }

    public File getImage(String url){
        try {
            return model.getImage(url);
        }catch (RemoteException e){
            e.printStackTrace();
        }return null;

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
