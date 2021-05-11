package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;

import java.rmi.RemoteException;

public class ItemViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty productName;
    private StringProperty price;

    public ItemViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        productName = new SimpleStringProperty();
        price = new SimpleStringProperty();
    }


    public StringProperty getProductName() {
        return productName;
    }


    public StringProperty getPrice() {
        return price;
    }

    public void reset() {
        try {
            productName.set(model.getProduct(state.productID, "General").getName());
            price.set(String.valueOf(model.getProduct(state.productID, "General").getPrice()));

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
