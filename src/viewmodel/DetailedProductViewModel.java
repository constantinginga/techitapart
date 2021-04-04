package viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    Product product = model.getProduct(state.getProductID(),"General");
    productName.set(product.getName());
    productPrice.set(String.valueOf(product.getPrice()));
    productQuantity.set(String.valueOf(product.getTotal_quantity()));
    description.set(product.getDescription());
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
    productQuantity
        .set(String.valueOf(Integer.parseInt(productQuantity.get()) + 1));
  }

  public void removeQuantity()
  {
    productQuantity
        .set(String.valueOf(Integer.parseInt(productQuantity.get()) - 1));
  }

  public void orderProduct()
  {
    model.buyProduct(
        model.getProduct(state.getProductID(), state.getCategoryName())
            .getName(), Integer.parseInt(productQuantity.get()),
        state.getCategoryName(), "Bob");
    errorLabel.set("Item ordered... I think :D");
  }
}
