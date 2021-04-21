package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class MarketUserViewModel
{
  private Model model;
  private ViewState state;
  ObservableList<Product> products = FXCollections.observableArrayList();

  public MarketUserViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.state = viewState;
    getData();
  }

  public ObservableList<Product> getProducts()
  {
    return products;
  }

  public void reset()
  {
    getData();
  }

  public void getData()
  {
    //Get all products from model
    List<Product> Products = new ArrayList<>();
    Products.addAll(model.getAllProducts());
  }
}
