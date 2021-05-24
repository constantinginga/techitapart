package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserViewController extends ViewController
{
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label errorLabel;
    @FXML private Button editButton;


    @Override protected void init() throws InterruptedException

    {

        firstName.textProperty().bindBidirectional(super.getViewModelFactory().getUserViewModel().firstNameProperty());
        lastName.textProperty().bindBidirectional(super.getViewModelFactory().getUserViewModel().lastNameProperty());
        email.textProperty().bindBidirectional(super.getViewModelFactory().getUserViewModel().emailProperty());
        username.textProperty().bindBidirectional(super.getViewModelFactory().getUserViewModel().usernameProperty());
        password.textProperty().bindBidirectional(super.getViewModelFactory().getUserViewModel().passwordProperty());
        errorLabel.textProperty().bind(super.getViewModelFactory().getUserViewModel().errorProperty());
        enablingOfTextFields(true);

    }

    @Override public void reset() throws InterruptedException
    {
        super.getViewModelFactory().getUserViewModel().reset();
        //errorLabel.setText("");
        enablingOfTextFields(true);
    }

    public void handleApplyButton(ActionEvent actionEvent) throws IOException
    {
        if(super.getViewModelFactory().getUserViewModel().edit())
            super.getViewHandler().openView("MarketUserView.fxml");
    }

    public void handleBackButton(ActionEvent actionEvent)
    {
        try
        {
            super.getViewHandler().openView("MarketUserView.fxml");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void enablingOfTextFields(boolean disabled){
        firstName.setDisable(disabled);
        lastName.setDisable(disabled);
        email.setDisable(disabled);
        password.setDisable(disabled);
    }

    public void handleEditButton(ActionEvent actionEvent)
    {
        enablingOfTextFields(false);
    }

    public void handlePasswordField(MouseEvent mouseEvent)
    {
        Platform.runLater(()->{
            password.setSkin(username.getSkin());
//      password.setAccessibleRole(AccessibleRole.TEXT_FIELD);
        });
    }
}