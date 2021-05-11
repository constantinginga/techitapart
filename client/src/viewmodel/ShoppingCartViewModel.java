package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CartItem;
import model.LocalModel;
import model.Product;
import java.io.File;
import java.rmi.RemoteException;

public class ShoppingCartViewModel {
    private LocalModel model;
    private ViewState state;
    ObservableList<CartItem> items;
    private StringProperty totalItems;

    public ShoppingCartViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
        this.totalItems = new SimpleStringProperty();
        items = FXCollections.observableArrayList();
        // this is temporary
        for (int i = 0; i < 20; i++) {
            Product product = new Product("PRODUCT #" + i, "Description lol", 50, 499);
            product.setImgSrc("default.jpg");
            items.add(new CartItem(product, 5));
        }
        totalItems.set(String.valueOf(items.size()));
    }

    public StringProperty getTotalItems() {
        return totalItems;
    }

    public int getItemIndex(String title) {
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
}
