package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.Product;

import java.rmi.RemoteException;

public class DetailedProductViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty productName, productPrice, productQuantity, errorLabel, description;

    public DetailedProductViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        productName = new SimpleStringProperty();
        productPrice = new SimpleStringProperty();
        productQuantity = new SimpleStringProperty("0");
        errorLabel = new SimpleStringProperty();
        description = new SimpleStringProperty();

    }

    public void reset() {
        try {
            Product product = model.getProduct(state.getProductID(), "General");
            productName.set(product.getName());
            productPrice.set(String.valueOf(product.getPrice()));
            productQuantity.set("1");
            description.set(product.getDescription());
            errorLabel.set("");

        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }

    public StringProperty getProductName() {
        return productName;
    }

    public StringProperty getProductPrice() {
        return productPrice;
    }

    public StringProperty getProductQuantity() {
        return productQuantity;
    }

    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    public StringProperty getDescription() {
        return description;
    }

    public void addQuantity() {
        try {
            if (Integer.parseInt(productQuantity.get()) >= model.getProduct(state.getProductID(), state.getCategoryName()).getTotal_quantity()) {
                productQuantity.set(String.valueOf(model.getProduct(state.getProductID(), state.getCategoryName()).getTotal_quantity()));
            } else {
                productQuantity
                        .set(String.valueOf(Integer.parseInt(productQuantity.get()) + 1));
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeQuantity() {
        if (Integer.parseInt(productQuantity.get()) <= 1) {
            productQuantity.set(String.valueOf(1));
        } else {
            productQuantity
                    .set(String.valueOf(Integer.parseInt(productQuantity.get()) - 1));
        }
    }

    public void orderProduct() {
        try {
            model.buyProduct(
                    model.getProduct(state.getProductID(), state.getCategoryName())
                    , Integer.parseInt(productQuantity.get()),
                    state.getCategoryName(), "Bob");

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public String getImage() {
        try {
            return model.getProduct(state.getProductID(), state.getCategoryName())
                    .getImgSrc();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

}
