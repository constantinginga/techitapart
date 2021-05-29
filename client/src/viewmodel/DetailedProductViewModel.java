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

/**
 * The  Detailed product view model.
 */
public class DetailedProductViewModel implements LocalListener<String, Integer> {
    private final LocalModel model;
    private final ViewState state;
    private final StringProperty productName, productPrice, productQuantity, errorLabel, description, totalQuantity;
    private final BooleanProperty editableProperty;

    /**
     * Instantiates a new Detailed product view model.
     *
     * @param model     the model
     * @param viewState the view state
     */
    public DetailedProductViewModel(LocalModel model, ViewState viewState) {
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

    /**
     * Resets the view.
     */
    public void reset() {
        try {
            Product product = model
                    .getProduct(state.getProductID(), state.getCategoryName());
            productName.set(product.getName());
            productPrice.set(String.valueOf(product.getPrice()));
            description.set(product.getDescription());
            totalQuantity.set(String.valueOf(product.getTotal_quantity()));
            productQuantity.set(Integer.parseInt(totalQuantity.get()) == 0 ? "0" : "1");
            errorLabel.set("");
            editableProperty.set(product.getTotal_quantity() != 0);
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets products name.
     *
     * @return the product name
     */
    public StringProperty getProductName() {
        return productName;
    }

    /**
     * Gets products price.
     *
     * @return the product price
     */
    public StringProperty getProductPrice() {
        return productPrice;
    }

    /**
     * Gets products quantity.
     *
     * @return the product quantity
     */
    public StringProperty getProductQuantity() {
        return productQuantity;
    }

    /**
     * Gets editable property.
     *
     * @return the editable property
     */
    public BooleanProperty getEditableProperty() {
        return editableProperty;
    }

    /**
     * Gets error label.
     *
     * @return the error label
     */
    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public StringProperty getDescription() {
        return description;
    }

    /**
     * Gets total quantity.
     *
     * @return the total quantity
     */
    public StringProperty getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Add quantity.
     */
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

    /**
     * Remove quantity .
     */
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

    /**
     * Orders product with selected quantity.
     */
    public void orderProduct() {
        try {
            if (model.getProduct(state.getProductID(), state.getCategoryID())
                    .getTotal_quantity() == 0) {
                editableProperty.set(false);
                return;
            }

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

    /**
     * Gets image.
     *
     * @return the image
     */
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