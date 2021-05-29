package viewmodel;

import model.LocalModel;

import java.rmi.RemoteException;

public class ViewModelFactory {
    private final AddProductViewModel addProductViewModel;
    private final DetailedProductViewModel detailedProductViewModel;
    private final MarketAdminViewModel marketAdminViewModel;
    private final MarketUserViewModel marketUserViewModel;
    private final DetailedProductAdminViewModel detailedProductAdminViewModel;
    private final SignUpViewModel signUpViewModel;
    private final UserViewModel userViewModel;
    private final LoginViewModel loginViewModel;
    private final UserListViewModel userListViewModel;
    private final ShoppingCartViewModel shoppingCartViewModel;
    private final ViewState viewState;

    public ViewModelFactory(LocalModel model) throws RemoteException {
        viewState = new ViewState();
        addProductViewModel = new AddProductViewModel(model, viewState);
        detailedProductViewModel = new DetailedProductViewModel(model, viewState);
        marketAdminViewModel = new MarketAdminViewModel(model, viewState);
        marketUserViewModel = new MarketUserViewModel(model, viewState);
        detailedProductAdminViewModel = new DetailedProductAdminViewModel(model, viewState);
        signUpViewModel = new SignUpViewModel(model, viewState);
        loginViewModel = new LoginViewModel(model, viewState);
        userViewModel = new UserViewModel(model, viewState);
        userListViewModel = new UserListViewModel(model, viewState);
        shoppingCartViewModel = new ShoppingCartViewModel(model, viewState);
    }

    public LoginViewModel getSignInViewModel() {
        return loginViewModel;
    }

    public AddProductViewModel getAddProductViewModel() {
        return addProductViewModel;
    }

    public DetailedProductViewModel getDetailedProductViewModel() {
        return detailedProductViewModel;
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public UserListViewModel getUserListViewModel() {
        return userListViewModel;
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public ShoppingCartViewModel getShoppingCartViewModel() {
        return shoppingCartViewModel;
    }

    public MarketAdminViewModel getMarketAdminViewModel() {
        return marketAdminViewModel;
    }

    public MarketUserViewModel getMarketUserViewModel() {
        return marketUserViewModel;
    }

    public DetailedProductAdminViewModel getDetailedProductAdminViewModel() {
        return detailedProductAdminViewModel;
    }

    public SignUpViewModel getSignUpViewModel() {
        return signUpViewModel;
    }

    public ViewState getViewState() {
        return viewState;
    }
}
