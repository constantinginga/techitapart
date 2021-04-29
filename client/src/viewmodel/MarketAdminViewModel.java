package viewmodel;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LocalModel;
import model.Product;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.GeneralListener;
import utility.observer.listener.LocalListener;
import utility.observer.subject.LocalSubject;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class MarketAdminViewModel implements LocalSubject<String, Integer>, LocalListener<String, Integer>
{
    private LocalModel model;
    private ViewState state;
    private PropertyChangeAction<String, Integer> property;
    ObservableList<Product> products = FXCollections.observableArrayList();

    public MarketAdminViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.property = new PropertyChangeProxy<>(this);
        model.addListener(this);
        getData();
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void reset() {
        System.out.println(products.size());
        getData();
        System.out.println(products.size());
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

    @Override public void propertyChange(ObserverEvent<String, Integer> event)
    {
        getData();
        property.firePropertyChange(event);
        }

    @Override public boolean addListener(
        GeneralListener<String, Integer> listener, String... propertyNames)
    {
        return property.addListener(listener, propertyNames);
    }

    @Override public boolean removeListener(
        GeneralListener<String, Integer> listener, String... propertyNames)
    {
        return property.removeListener(listener, propertyNames);
    }
}

