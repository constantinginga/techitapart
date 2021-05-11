package viewmodel;

import model.LocalModel;
import java.rmi.RemoteException;

public class ViewModelFactory
{
  private  AddProductViewModel addProductViewModel;
  private DetailedProductViewModel detailedProductViewModel;
  private ItemViewModel itemViewModel;
  private CategoryViewModel categoryViewModel;
  private MarketAdminViewModel marketAdminViewModel;
  private MarketUserViewModel marketUserViewModel;
  private DetailedProductAdminViewModel detailedProductAdminViewModel;
  private SignUpViewModel signUpViewModel;
  private EntryViewModel entryViewModel;
  private UserViewModel userViewModel;
  private LoginViewModel loginViewModel;
  private UserListViewModel userListViewModel;
  private ViewState viewState;

  public ViewModelFactory(LocalModel model) throws RemoteException
  {
    viewState = new ViewState();
    addProductViewModel = new AddProductViewModel(model, viewState);
    detailedProductViewModel = new DetailedProductViewModel(model, viewState);
    marketAdminViewModel = new MarketAdminViewModel(model, viewState);
    marketUserViewModel = new MarketUserViewModel(model, viewState);
    itemViewModel = new ItemViewModel(model, viewState);
    categoryViewModel = new CategoryViewModel(model, viewState);
    detailedProductAdminViewModel = new DetailedProductAdminViewModel(model, viewState);
    signUpViewModel = new SignUpViewModel(model, viewState);
    loginViewModel = new LoginViewModel(model, viewState);
    entryViewModel = new EntryViewModel(model, viewState);
    userViewModel = new UserViewModel(model, viewState);
    userListViewModel = new UserListViewModel(model, viewState);
  }

  public LoginViewModel getSignInViewModel() {
    return loginViewModel;
  }

  public AddProductViewModel getAddProductViewModel()
  {
    return addProductViewModel;
  }

  public CategoryViewModel getCategoryViewModel() {
    return categoryViewModel;
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

  public LoginViewModel getLoginViewModel()
  {
    return loginViewModel;
  }

  public UserListViewModel getUserListViewModel()
  {
    return userListViewModel;
  }

  public MarketUserViewModel getMarketUserViewModel()
  {
    return marketUserViewModel;
  }

  public DetailedProductAdminViewModel getDetailedProductAdminViewModel()
  {
    return detailedProductAdminViewModel;
  }

  public SignUpViewModel getSignUpViewModel() {
    return signUpViewModel;
  }

  public EntryViewModel getEntryViewModel() {
    return entryViewModel;
  }

  public UserViewModel getUserViewModel()
  {
    return userViewModel;
  }

  public ViewState getViewState()
  {
    return viewState;
  }
}
