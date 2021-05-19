package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.LocalModel;
import model.Product;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Optional;

public class DetailedProductViewModel implements LocalListener<String, Integer> {
    private LocalModel model;
    private ViewState state;
    private StringProperty productName, productPrice, productQuantity, errorLabel, description, totalQuantity;
    private BooleanProperty editableProperty;

    public DetailedProductViewModel(LocalModel model, ViewState viewState)
            throws RemoteException {
        this.model = model;
        this.state = viewState;
        productName = new SimpleStringProperty();
        productPrice = new SimpleStringProperty();
        productQuantity = new SimpleStringProperty("1");
        errorLabel = new SimpleStringProperty();
        description = new SimpleStringProperty();
        editableProperty = new SimpleBooleanProperty(true);
        totalQuantity = new SimpleStringProperty();
        model.addListener(this);
    }

    public void reset() {
        try {
            Product product = model
                    .getProduct(state.getProductID(), state.getCategoryName());
            productName.set(product.getName());
            productPrice.set(String.valueOf(product.getPrice()));
            System.out.println("Client: " + product.getTotal_quantity());
            description.set(product.getDescription());
            totalQuantity.set(String.valueOf(product.getTotal_quantity()));
            productQuantity.set(Integer.parseInt(totalQuantity.get()) == 0 ? "0" : "1");
            errorLabel.set("");
            editableProperty.set(product.getTotal_quantity() != 0);
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

    public BooleanProperty getEditableProperty() {
        return editableProperty;
    }

    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    public StringProperty getDescription() {
        return description;
    }

    public StringProperty getTotalQuantity() {
        return totalQuantity;
    }

    public void addQuantity() {
        try {
            if (Integer.parseInt(productQuantity.get()) >= model
                    .getProduct(state.getProductID(), state.getCategoryName())
                    .getTotal_quantity()) {
                productQuantity.set(String.valueOf(
                        model.getProduct(state.getProductID(), state.getCategoryName())
                                .getTotal_quantity()));
            } else {
                productQuantity
                        .set(String.valueOf(Integer.parseInt(productQuantity.get()) + 1));
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void removeQuantity() {
        if (Integer.parseInt(productQuantity.get()) == 1) {
            productQuantity.set(String.valueOf(1));
        } else if (Integer.parseInt(productQuantity.get()) == 0) {
            productQuantity.set(String.valueOf(0));
        } else {
            productQuantity
                    .set(String.valueOf(Integer.parseInt(productQuantity.get()) - 1));
        }
    }

    public void orderProduct() {
        try {
            if (model.getProduct(state.getProductID(), state.getCategoryID())
                    .getTotal_quantity() == 0) {
                editableProperty.set(false);
                return;
            }

            System.out.println(productQuantity.getValue()
            );

            if (confirmation()) {
                model.addProductToCart(model.getProduct(state.getProductID(), state.getCategoryName()), Integer
                        .parseInt(productQuantity.getValue()), state.getUserID());
                errorLabel.set("");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            errorLabel.set(e.getMessage());
        }

    }

    private boolean confirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(
                "Add Product [" + productName.getValue() + "] to cart with quantity: " + productQuantity.getValue()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    public File getImage() {
        try {
            return model.getImage(
                    (model.getProduct(state.getProductID(), state.getCategoryName()))
                            .getImgSrc());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        Platform.runLater(() -> {
            System.out.println("Fire property change in DetailedProductViewProperty");
            if (event.getPropertyName().contains("quantity")) {
                if (event.getValue1().equals(state.getProductID())) {
                    if (Integer.parseInt(productQuantity.get()) == Integer.parseInt(totalQuantity.get()))
                        productQuantity.set(Integer.toString(event.getValue2()));

                    totalQuantity.set(Integer.toString(event.getValue2()));
                }
            }
        });

    }
}