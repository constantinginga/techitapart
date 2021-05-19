package viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;


import java.rmi.RemoteException;

public class UserListViewModel {
    private ListProperty<String> users;
    private ListProperty<Order> orders;
    private LocalModel localModel;
    private ViewState state;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;
    private StringProperty username;
    private StringProperty password;
    private StringProperty error;
    private SimpleStringProperty selectedUserProperty;

    public UserListViewModel(LocalModel localModel, ViewState viewState)
            throws RemoteException {
        this.localModel = localModel;
        this.state = viewState;
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
        this.users = new SimpleListProperty<>();
        this.orders = new SimpleListProperty<>();
        selectedUserProperty = new SimpleStringProperty();
    }

    public ListProperty<Order> getOrders() {
        return orders;
    }

    public ListProperty<String> getUsers() {
        return users;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void getAllUsers() throws InterruptedException {
        Platform.runLater(() -> {
            try {
                ObservableList<String> temp = FXCollections.observableArrayList();
                System.out.println(localModel.getAllUsernames());
                temp.setAll(localModel.getAllUsernames());
                users.set(temp);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public void setSelectedExerciseProperty(String selectedExerciseProperty) {
        this.selectedUserProperty.set(selectedExerciseProperty);
    }

    public void reset() {
        try {

            User user = localModel.getUser(state.getUserID());
            firstName.set(user.getfName());
            lastName.set(user.getlName());
            email.set(user.getEmail());
            username.set(user.getUsername());
            password.set(user.getPassword());
            error.set("");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean edit() {
        try {
            User user = new User(firstName.get(), lastName.get(), email.get(),
                    username.get(), password.get());
            localModel.updateUser(user);
        } catch (IllegalArgumentException | RemoteException e) {
            error.set(e.getMessage());
            return false;
        }
        return true;
    }

    public void updateDetails() {
        Platform.runLater(() -> {
            try {
                User user = localModel.getUser(selectedUserProperty.get());
                firstName.set(user.getfName());
                lastName.set(user.getlName());
                email.set(user.getEmail());
                username.set(user.getUsername());
                password.set(user.getPassword());
                ObservableList<Order> temp = FXCollections.observableArrayList(localModel.getAllOrdersByUsername(selectedUserProperty.get()));
                orders.set(temp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}