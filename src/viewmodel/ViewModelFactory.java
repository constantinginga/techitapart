package viewmodel;

import model.Model;

public class ViewModelFactory
{
  private  AddProductViewModel addProductViewModel;
  private DetailedProductViewModel detailedProductViewModel;
  private ItemViewModel itemViewModel;
  private MarketAdminViewModel marketAdminViewModel;
  private MarketUserViewModel marketUserViewModel;
  private ViewState viewState;
  public ViewModelFactory(Model model)
  {
    viewState = new ViewState();
    addProductViewModel = new AddProductViewModel(model, viewState);
    detailedProductViewModel = new DetailedProductViewModel(model, viewState);
    marketAdminViewModel = new MarketAdminViewModel(model, viewState);
    marketUserViewModel = new MarketUserViewModel(model, viewState);
    itemViewModel = new ItemViewModel(model, viewState);
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

  public ViewState getViewState()
  {
    return viewState;
  }
}
