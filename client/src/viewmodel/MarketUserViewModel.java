package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LocalModel;
import model.Product;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MarketUserViewModel
{
  private LocalModel model;
  private ViewState state;
  ObservableList<Product> products = FXCollections.observableArrayList();

  public MarketUserViewModel(LocalModel model, ViewState viewState)
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
  {        try {
    List<Product> Products = new ArrayList<>();
    Products.addAll(model.getAllProducts());

  } catch (RemoteException e) {
    e.printStackTrace();
  }
    //Get all products from model
  }
}
