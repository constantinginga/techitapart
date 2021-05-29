package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Order;

import java.io.IOException;

public class UserListViewController extends ViewController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button editButton;
    @FXML
    private ListView<Order> orderListView;

    @Override
    protected void init() throws InterruptedException {
        listView.itemsProperty()
                .bind(super.getViewModelFactory().getUserListViewModel().getUsers());
        orderListView.itemsProperty()
                .bind(super.getViewModelFactory().getUserListViewModel().getOrders());
        firstName.textProperty().bindBidirectional(
                super.getViewModelFactory().getUserListViewModel().firstNameProperty());
        lastName.textProperty().bindBidirectional(
                super.getViewModelFactory().getUserListViewModel().lastNameProperty());
        email.textProperty().bindBidirectional(
                super.getViewModelFactory().getUserListViewModel().emailProperty());
        username.textProperty().bindBidirectional(
                super.getViewModelFactory().getUserListViewModel().usernameProperty());
        password.textProperty().bindBidirectional(
                super.getViewModelFactory().getUserListViewModel().passwordProperty());
        errorLabel.textProperty()
                .bind(super.getViewModelFactory().getUserListViewModel().errorProperty());
        enablingOfTextFields(true);
        listView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    super.getViewModelFactory().getUserListViewModel()
                            .setSelectedUserProperty(newVal);
                    super.getViewModelFactory().getUserListViewModel().updateDetails();
                });
        super.getViewModelFactory().getUserListViewModel().getAllUsers();
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getUserListViewModel().reset();
        enablingOfTextFields(true);
    }

    public void handleApplyButton() {
        if (super.getViewModelFactory().getUserListViewModel().edit())
            enablingOfTextFields(true);
    }

    public void handleBackButton() {
        try {
            super.getViewHandler().openView("MarketAdminView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enablingOfTextFields(boolean disabled) {
        firstName.setDisable(disabled);
        lastName.setDisable(disabled);
        email.setDisable(disabled);
        password.setDisable(disabled);
    }

    public void handleEditButton() {
        enablingOfTextFields(false);
    }
}