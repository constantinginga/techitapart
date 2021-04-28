package viewmodel;

import model.LocalModel;

import java.io.File;
import java.rmi.RemoteException;

public class ViewModelFactory
{
  private  AddProductViewModel addProductViewModel;
  private DetailedProductViewModel detailedProductViewModel;
  private ItemViewModel itemViewModel;
  private MarketAdminViewModel marketAdminViewModel;
  private MarketUserViewModel marketUserViewModel;
  private DetailedProductAdminViewModel detailedProductAdminViewModel;
  private ViewState viewState;
  public ViewModelFactory(LocalModel model) throws RemoteException
  {
    viewState = new ViewState();
    addProductViewModel = new AddProductViewModel(model, viewState);
    detailedProductViewModel = new DetailedProductViewModel(model, viewState);
    marketAdminViewModel = new MarketAdminViewModel(model, viewState);
    marketUserViewModel = new MarketUserViewModel(model, viewState);
    itemViewModel = new ItemViewModel(model, viewState);
    detailedProductAdminViewModel = new DetailedProductAdminViewModel(model, viewState);
  }

  public AddProductViewModel getAddProductViewModel()
  {
    return addProductViewModel;
  }

  public DetailedProductViewModel getDetailedProductViewModel()
  {
    return detailedProductViewModel;
  }

  public ItemViewModel getItemViewModel()
  {
    return itemViewModel;
  }

  public MarketAdminViewModel getMarketAdminViewModel()
  {
    return marketAdminViewModel;
  }

  public MarketUserViewModel getMarketUserViewModel()
  {
    return marketUserViewModel;
  }

  public DetailedProductAdminViewModel getDetailedProductAdminViewModel()
  {
    return detailedProductAdminViewModel;
  }

  public ViewState getViewState()
  {
    return viewState;
  }
}
