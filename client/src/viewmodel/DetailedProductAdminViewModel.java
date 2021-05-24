package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import model.LocalModel;
import model.Product;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.io.File;
import java.rmi.RemoteException;

/**
 * The  Detailed product  view model for admin.
 */
public class DetailedProductAdminViewModel implements LocalListener<String, Integer>
{
    private LocalModel model;
    private ViewState state;
    private StringProperty productName, productPrice, productQuantity, errorLabel, description, totalQuantity;
    private BooleanProperty editableProperty;

    /**
     * Instantiates a new Detailed product admin view model.
     *
     * @param model     the model
     * @param viewState the view state
     * @throws RemoteException the remote exception
     */
    public DetailedProductAdminViewModel(LocalModel model, ViewState viewState)
        throws RemoteException
    {
        this.model = model;
        this.state = viewState;
        productName = new SimpleStringProperty();
        productPrice = new SimpleStringProperty();
        productQuantity = new SimpleStringProperty("1");
        errorLabel = new SimpleStringProperty();
        description = new SimpleStringProperty();
        totalQuantity = new SimpleStringProperty();
        editableProperty = new SimpleBooleanProperty(true);
        model.addListener(this);
    }

    /**
     * Resets the view.
     */
    public void reset()
    {
        try
        {
            Product product = model
                .getProduct(state.getProductID(), state.getCategoryName());
            productName.set(product.getName());
            productPrice.set(String.valueOf(product.getPrice()));
            System.out.println("Client: " + product.getTotal_quantity());
            description.set(product.getDescription());
            totalQuantity.set(String.valueOf(product.getTotal_quantity()));
            productQuantity.set(Integer.parseInt(totalQuantity.get()) == 0 ? "0" : "1");
            errorLabel.set("");
            if (product.getTotal_quantity() <= 0)
            {
                editableProperty.set(false);
            }
            else
            {
                editableProperty.set(true);
            }
        }
        catch (RemoteException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public StringProperty getProductName()
    {
        return productName;
    }

    /**
     * Gets product price.
     *
     * @return the product price
     */
    public StringProperty getProductPrice()
    {
        return productPrice;
    }

    /**
     * Gets product quantity.
     *
     * @return the product quantity
     */
    public StringProperty getProductQuantity()
    {
        return productQuantity;
    }

    /**
     * Gets editable property.
     *
     * @return the editable property
     */
    public BooleanProperty getEditableProperty()
    {
        return editableProperty;
    }

    /**
     * Gets error label.
     *
     * @return the error label
     */
    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public StringProperty getDescription()
    {
        return description;
    }

    /**
     * Gets total quantity.
     *
     * @return the total quantity
     */
    public StringProperty getTotalQuantity() { return totalQuantity; }

    /**
     * Add quantity for product.
     */
    public void addQuantity()
    {
        try
        {
            if (Integer.parseInt(productQuantity.get()) >= model
                .getProduct(state.getProductID(), state.getCategoryName())
                .getTotal_quantity())
            {
                productQuantity.set(String.valueOf(
                    model.getProduct(state.getProductID(), state.getCategoryName())
                        .getTotal_quantity()));
            }
            else
            {
                productQuantity
                    .set(String.valueOf(Integer.parseInt(productQuantity.get()) + 1));
            }

        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Remove product from system.
     */
    public void removeProduct() {
        try {

            model.removeProduct(state.getProductID(), state.getCategoryName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Remove quantity for product.
     */
    public void removeQuantity()
    {
        if (Integer.parseInt(productQuantity.get()) == 1)
        {
            productQuantity.set(String.valueOf(1));
        }
        else if (Integer.parseInt(productQuantity.get()) == 0)
        {
            productQuantity.set(String.valueOf(0));
        }
        else
        {
            productQuantity
                .set(String.valueOf(Integer.parseInt(productQuantity.get()) - 1));
        }
    }


    /**
     * Gets image of product.
     *
     * @return the image
     */
    public File getImage()
    {
        try
        {
            return model.getImage(
                (model.getProduct(state.getProductID(), state.getCategoryName()))
                    .getImgSrc());
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override public void propertyChange(ObserverEvent<String, Integer> event)
    {
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
