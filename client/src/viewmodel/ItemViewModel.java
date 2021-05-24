package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;

import java.rmi.RemoteException;

/**
 * The Item view model.
 */
public class ItemViewModel {
    private final LocalModel model;
    private final ViewState state;
    private final StringProperty productName, price;

    /**
     * Instantiates a new Item view model.
     *
     * @param model     the model
     * @param viewState the view state
     */
    public ItemViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        productName = new SimpleStringProperty();
        price = new SimpleStringProperty();
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public StringProperty getProductName() {
        return productName;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public StringProperty getPrice() {
        return price;
    }

    /**
     * Resets the view.
     */
    public void reset() {
        try {
            productName.set(model.getProduct(state.productID, "General").getName());
            price.set(String.valueOf(model.getProduct(state.productID, "General").getPrice()));

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
