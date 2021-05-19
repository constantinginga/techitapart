package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import model.LocalModel;
import model.Product;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.io.File;
import java.rmi.RemoteException;

public class DetailedProductAdminViewModel implements LocalListener<String, Integer>
{
    private LocalModel model;
    private ViewState state;
    private StringProperty productName, productPrice, productQuantity, errorLabel, description, totalQuantity;
    private BooleanProperty editableProperty;

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

    public StringProperty getProductName()
    {
        return productName;
    }

    public StringProperty getProductPrice()
    {
        return productPrice;
    }

    public StringProperty getProductQuantity()
    {
        return productQuantity;
    }

    public BooleanProperty getEditableProperty()
    {
        return editableProperty;
    }

    public StringProperty getErrorLabel()
    {
        return errorLabel;
    }

    public StringProperty getDescription()
    {
        return description;
    }

    public StringProperty getTotalQuantity() { return totalQuantity; }

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
    public void removeProduct() {
        try {

            model.removeProduct(state.getProductID(), state.getCategoryName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


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
