package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;

public class SignUpViewController extends ViewController {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label error;

    @Override
    protected void init() throws InterruptedException {
        firstName.textProperty().bindBidirectional(super.getViewModelFactory().getSignUpViewModel().getFirstName());
        lastName.textProperty().bindBidirectional(super.getViewModelFactory().getSignUpViewModel().getLastName());
        email.textProperty().bindBidirectional(super.getViewModelFactory().getSignUpViewModel().getEmail());
        username.textProperty().bindBidirectional(super.getViewModelFactory().getSignUpViewModel().getUsername());
        password.textProperty().bindBidirectional(super.getViewModelFactory().getSignUpViewModel().getPassword());
        error.textProperty().bind(super.getViewModelFactory().getSignUpViewModel().getError());

        password.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                try {
                    handleSignUpButton();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getSignUpViewModel().reset();
    }

    @FXML
    private void handleSignUpButton() throws IOException {
        if (super.getViewModelFactory().getSignUpViewModel().signUp()) super.getViewHandler().openView("MarketUserView.fxml");
    }

    @FXML
    private void handleBackButton() throws IOException {
        super.getViewHandler().openView("EntryView.fxml");
    }

    @FXML
    private void handleHyperLink() throws IOException {
        super.getViewHandler().openView("LoginView.fxml");
    }
}
