package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

        password.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                try {
                    handleLoginButton();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getSignInViewModel().reset();
    }

    @FXML
    private void handleLoginButton() throws IOException {
        String role = super.getViewModelFactory().getSignInViewModel().login();
        if (role != null) {
            switch (role.toLowerCase()) {
                case "consumer" -> super.getViewHandler().openView("MarketUserView.fxml");
                case "admin" -> super.getViewHandler().openView("MarketAdminView.fxml");
            }
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        super.getViewHandler().openView("EntryView.fxml");
    }

    @FXML
    private void handleHyperLink() throws IOException {
        super.getViewHandler().openView("SignUpView.fxml");
    }
}