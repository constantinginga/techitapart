package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CartItem;
import model.LocalModel;
import model.Product;
import model.UserProfile;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ShoppingCartViewModel {
    private LocalModel model;
    private ViewState state;
    ObservableList<CartItem> items;
    private StringProperty totalItems;
    private StringProperty totalPrice;

    public ShoppingCartViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
        this.totalItems = new SimpleStringProperty();
        this.totalPrice = new SimpleStringProperty();
        items = FXCollections.observableArrayList();
        // this is temporary
//        for (int i = 0; i < 20; i++) {
//            Product product = new Product("PRODUCT #" + i, "Description lol", 50, 499);
//            product.setImgSrc("default.jpg");
//            items.add(new CartItem(product, 5));
//        }
        reset();
    }

    public StringProperty getTotalItems() {
        return totalItems;
    }

    public StringProperty getTotalPrice() {
        return totalPrice;
    }

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

    public void reset() {
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

    public ObservableList<CartItem> getItems() {
        return items;
    }

    public File getImage(int index) {
        try {
            return model.getImage(items.get(index).getProduct().getImgSrc());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateCartItemQuantity(CartItem cartItem,  int quantity){
        try {
            model.updateCartItemQuantity(cartItem, quantity, state.getUserID());
            Platform.runLater(this::updateTotalPrice);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void removeCartItem(CartItem cartItem){
        try {
            model.removeProductFromCart(cartItem, state.getUserID());
            Platform.runLater(this::updateTotalPrice);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
