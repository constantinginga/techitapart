package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
import model.Product;

public class DetailedProductViewModel
{
  private Model model;
  private ViewState state;
  private StringProperty productName, productPrice, productQuantity, errorLabel, description;

  public DetailedProductViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.state = viewState;
    productName = new SimpleStringProperty();
    productPrice = new SimpleStringProperty();
    productQuantity = new SimpleStringProperty("0");
    errorLabel = new SimpleStringProperty();
    description = new SimpleStringProperty();

  }

  public void reset()
  {
    Product product = model.getProduct(state.getProductID(), "General");
    productName.set(product.getName());
    productPrice.set(String.valueOf(product.getPrice()));
    productQuantity.set("1");
    description.set(product.getDescription());
    errorLabel.set("");
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
    if (Integer.parseInt(productQuantity.get()) >= model.getProduct(state.getProductID(), state.getCategoryName()).getTotal_quantity()){
      productQuantity.set(String.valueOf(model.getProduct(state.getProductID(), state.getCategoryName()).getTotal_quantity()));
    }
    else{
      productQuantity
          .set(String.valueOf(Integer.parseInt(productQuantity.get()) + 1));
    }
  }

  public void removeQuantity()
  {
    if (Integer.parseInt(productQuantity.get()) <= 1)
    {
      productQuantity.set(String.valueOf(1));
    }
    else
    {
      productQuantity
          .set(String.valueOf(Integer.parseInt(productQuantity.get()) - 1));
    }
  }

  public void orderProduct()
  {
    model.buyProduct(
        model.getProduct(state.getProductID(), state.getCategoryName())
            , Integer.parseInt(productQuantity.get()),
        state.getCategoryName(), "Bob");

  }

  public String getImage()
  {
    return model.getProduct(state.getProductID(), state.getCategoryName())
        .getImgSrc();
  }
}
