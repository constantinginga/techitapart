package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import model.LocalModel;
import model.Product;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;

import java.io.File;
import java.rmi.RemoteException;

public class DetailedProductViewModel implements LocalListener<String, Integer>
{
  private LocalModel model;
  private ViewState state;
  private StringProperty productName, productPrice, productQuantity, errorLabel, description;
  private BooleanProperty editableProperty;

  public DetailedProductViewModel(LocalModel model, ViewState viewState)
      throws RemoteException
  {
    this.model = model;
    this.state = viewState;
    productName = new SimpleStringProperty();
    productPrice = new SimpleStringProperty();
    productQuantity = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();
    description = new SimpleStringProperty();
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
      productQuantity.set(Integer.toString(product.getTotal_quantity()));
      description.set(product.getDescription());
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

  public void orderProduct()
  {
    try
    {
      if (model.getProduct(state.getProductID(), "General")
          .getTotal_quantity() == 0)
      {
        editableProperty.set(false);
        return;
      }
     /* model.buyProduct(
          model.getProduct(state.getProductID(), state.getCategoryName()),
          model.getProduct(state.getProductID(), state.getCategoryName())
              .getTotal_quantity() - Integer
              .parseInt(productQuantity.getValue()), state.getCategoryName(),
          "Bob");*/
      model.buyProduct(model.getProduct(state.getProductID(), state.getCategoryName()), Integer
          .parseInt(productQuantity.getValue()), "General", "Bob" );

    }
    catch (RemoteException e)
    {
      e.printStackTrace();
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
      if(event.getPropertyName().contains("quantity")){
        if(event.getValue1().equals(state.getProductID())){
          productQuantity.set(Integer.toString(event.getValue2()));
        }
      }
    });

  }
}
