package viewmodel;

import javafx.beans.property.*;
import model.LocalModel;
import model.Product;

import java.io.File;
import java.rmi.RemoteException;

public class DetailedProductViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty productName, productPrice, productQuantity, errorLabel, description;
    private ObjectProperty<Boolean> editableProperty;

    public DetailedProductViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        productName = new SimpleStringProperty();
        productPrice = new SimpleStringProperty();
        productQuantity = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();
        description = new SimpleStringProperty();
        editableProperty = new SimpleObjectProperty<>(false);
    }

    public void reset() {
        try {
            Product product = model.getProduct(state.getProductID(), state.getCategoryName());
            productName.set(product.getName());
            productPrice.set(String.valueOf(product.getPrice()));
            productQuantity.set(String.valueOf(product.getTotal_quantity()));
            description.set(product.getDescription());
            errorLabel.set("");
            editableProperty = new SimpleObjectProperty<>(false);
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

    public ObjectProperty<Boolean> getEditableProperty()
    {
        return editableProperty;
    }

    public ObjectProperty<Boolean> editablePropertyProperty()
    {
        return editableProperty;
    }

    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    public StringProperty getDescription() {
        return description;
    }

    public void addQuantity() {
        try {
            editableProperty.set(!getEditableProperty().get());
            editableProperty.setValue(!getEditableProperty().get());
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
            if(model.getProduct(state.getProductID(), state.getCategoryName()).getTotal_quantity() == 0){
                editableProperty.set(false);
            }
            model.buyProduct(
                    model.getProduct(state.getProductID(), state.getCategoryName())
                    , Integer.parseInt(productQuantity.get()),
                    state.getCategoryName(), "Bob");

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public File getImage() {
        try {
            return model.getImage((model.getProduct(state.getProductID(), state.getCategoryName())).getImgSrc());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

}
