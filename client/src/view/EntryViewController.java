package view;

import javafx.fxml.FXML;

import java.io.IOException;

public class EntryViewController extends ViewController {

    @Override
    protected void init() throws InterruptedException {

    }

    @Override
    public void reset() throws InterruptedException {

    }

    @FXML
    private void handleSignUpButton() throws IOException {
        super.getViewHandler().openView("SignUpView.fxml");
    }

    @FXML
    private void handleLogInButton() throws IOException {
        super.getViewHandler().openView("LoginView.fxml");
    }
}
