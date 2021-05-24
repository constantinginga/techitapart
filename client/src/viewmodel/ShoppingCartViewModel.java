package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CartItem;
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
import java.util.ArrayList;


/**
 * The Shopping cart view model.
 */
public class ShoppingCartViewModel implements LocalListener<String, Integer>, LocalSubject<String, Integer> {
    private final LocalModel model;
    private final ViewState state;
    ObservableList<CartItem> items;
    private final StringProperty totalItems, totalPrice, error;
    private final PropertyChangeAction<String, Integer> property;

    /**
     * Instantiates a new Shopping cart view model.
     *
     * @param model the model
     * @param state the state
     */
    public ShoppingCartViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
        this.totalItems = new SimpleStringProperty();
        this.totalPrice = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
        items = FXCollections.observableArrayList();
        property = new PropertyChangeProxy<>(this);
        model.addListener(this);
        reset();
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public StringProperty getError() {
        return error;
    }

    /**
     * Gets total items.
     *
     * @return the total items
     */
    public StringProperty getTotalItems() {
        return totalItems;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public StringProperty getTotalPrice() {
        return totalPrice;
    }

    /**
     * Remove item int.
     *
     * @param title the title
     * @return the int
     */
    public int removeItem(String title) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getName().equals(title)) {
                items.remove(i);
                totalItems.set(String.valueOf(items.size()));
                return i;
            }
        }

        return -1;
    }

    /**
     * Resets the view.
     */
    public void reset() {
        error.set("");
        try {
            ArrayList<CartItem> cartItems = model.getProductsFromCart(state.getUserID());
            if (cartItems != null) {
                items.setAll(cartItems);
                updateTotalPrice();
            }
            totalItems.set(String.valueOf(items.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTotalPrice() {
        int tempPrice = 0;
        for (CartItem c : items) {
            tempPrice += c.getQuantity() * c.getProduct().getPrice();
        }

        totalPrice.set(String.valueOf(tempPrice));
    }

    /**
     * Sets error.
     *
     * @param errorMessage the error message
     */
    public void setError(String errorMessage) {
        error.set(errorMessage);
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public ObservableList<CartItem> getItems() {
        return items;
    }

    /**
     * Gets image.
     *
     * @param index the index of product
     * @return the image
     */
    public File getImage(int index) {
        try {
            return model.getImage(items.get(index).getProduct().getImgSrc());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get current quantity.
     *
     * @param productId the product id
     * @return the int
     */
    public int getCurrentQuantity(String productId) {
        int quantity = -1;
        try {
            ArrayList<Product> products = model.getAllProducts();
            for (Product p : products) {
                if (p.getId().equals(productId)) quantity = p.getTotal_quantity();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return quantity;
    }

    /**
     * Update cart items quantity.
     *
     * @param cartItem the cart item
     * @param quantity the new quantity
     */
    public void updateCartItemQuantity(CartItem cartItem, int quantity) {
        try {
            model.updateCartItemQuantity(cartItem, quantity, state.getUserID());
            Platform.runLater(this::updateTotalPrice);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Remove cart item.
     *
     * @param cartItem the cart item
     */
    public void removeCartItem(CartItem cartItem) {
        try {
            model.removeProductFromCart(cartItem, state.getUserID());
            Platform.runLater(this::updateTotalPrice);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checkouts.
     */
    public void checkout() {
        if (items.size() != 0) {
            try {
                model.buy(state.getUserID());
                Platform.runLater(() -> {
                    items.clear();
                    updateTotalPrice();
                    totalItems.set(String.valueOf(items.size()));
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                error.set(e.getMessage());
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    @Override
    public void propertyChange(ObserverEvent<String, Integer> event) {
        Platform.runLater(() -> {
            if (event.getPropertyName().contains("Product")) {
                if (event.getPropertyName().equals("removeProduct")) {
                    String id = event.getValue1();
                    for (CartItem c : items) {
                        if (c.getProduct().getId().equals(id)) {
                            items.remove(c);
                            removeCartItem(c);
                            break;
                        }
                    }
                }

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
