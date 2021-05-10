package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginViewController extends ViewController {

    @FXML
    private Label error;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    protected void init() throws InterruptedException {
        username.textProperty().bindBidirectional(super.getViewModelFactory().getSignInViewModel().getUsername());
        password.textProperty().bindBidirectional(super.getViewModelFactory().getSignInViewModel().getPassword());
        error.textProperty().bindBidirectional(super.getViewModelFactory().getSignInViewModel().getError());
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getSignInViewModel().reset();
    }

    @FXML
    private void handleSLoginButton(ActionEvent actionEvent) throws IOException {
        if (super.getViewModelFactory().getSignInViewModel().login().equalsIgnoreCase("consumer")) {
            super.getViewHandler().openView("MarketUserView.fxml");
        } else if (super.getViewModelFactory().getSignInViewModel().login().equalsIgnoreCase("admin")) {
            super.getViewHandler().openView("MarketAdminView.fxml");
        }
    }

    @FXML
    private void handleBackButton(ActionEvent actionEvent) throws IOException {
        super.getViewHandler().openView("EntryView.fxml");
    }

    @FXML
    private void handleHyperLink(ActionEvent actionEvent) throws IOException {
        super.getViewHandler().openView("SignUpView.fxml");
    }
}