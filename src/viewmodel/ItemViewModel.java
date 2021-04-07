package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

public class ItemViewModel
{
  private Model model;
  private ViewState state;
  private StringProperty productName;
  private StringProperty price;
  // kekw hot to bind image?

  public ItemViewModel(Model model,ViewState viewState)
  {
    this.model = model;
    this.state = viewState;

    productName = new SimpleStringProperty();
    price = new SimpleStringProperty();

  }


  public StringProperty getProductName()
  {
    return productName;
  }


  public StringProperty getPrice()
  {
    return price;
  }

  public void reset(){
    productName.set(model.getProduct(state.productID,"General").getName());
    price.set(String.valueOf(model.getProduct(state.productID,"General").getPrice()));

  }
}
