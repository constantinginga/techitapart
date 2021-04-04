package viewmodel;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
import model.Product;

public class AddProductViewModel
{
  private Model model;
  private ViewState state;
  private StringProperty name,description,quantity,price;


  public AddProductViewModel(Model model,ViewState viewState)
  {
    this.model = model;
    this.state = viewState;
    this.name = new SimpleStringProperty();
    this.description = new SimpleStringProperty();
    this.quantity  = new SimpleStringProperty();
    this.price = new SimpleStringProperty();
  }

  public StringProperty getName()
  {
    return name;
  }

  public StringProperty getDescription()
  {
    return description;
  }

  public StringProperty getQuantity()
  {
    return quantity;
  }

  public StringProperty getPrice()
  {
    return price;
  }
  public void reset(){
    name.set("");
    description.set("");
    quantity.set("");
    price.set("");

  }
  public void addProduct(){
    model.addProduct(new Product(name.get(),description.get(),Integer.parseInt(quantity.get()),Double.parseDouble(price.get())),"General");

  }
}
