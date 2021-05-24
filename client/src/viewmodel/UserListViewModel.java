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

/**
 * The User list view model.
 */
public class UserListViewModel {
    private final ListProperty<String> users;
    private final ListProperty<Order> orders;
    private final LocalModel localModel;
    private final ViewState state;
    private final StringProperty firstName, lastName, email, username, password, error;
    private final SimpleStringProperty selectedUserProperty;

    /**
     * Instantiates a new User list view model.
     *
     * @param localModel the local model
     * @param viewState  the view state
     * @throws RemoteException the remote exception
     */
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

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public ListProperty<Order> getOrders() {
        return orders;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public ListProperty<String> getUsers() {
        return users;
    }

    /**
     * First name property string property.
     *
     * @return the string property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Last name property string property.
     *
     * @return the string property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Email property string property.
     *
     * @return the string property
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Username property string property.
     *
     * @return the string property
     */
    public StringProperty usernameProperty() {
        return username;
    }

    /**
     * Password property string property.
     *
     * @return the string property
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Error property string property.
     *
     * @return the string property
     */
    public StringProperty errorProperty() {
        return error;
    }

    /**
     * Gets all users.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void getAllUsers() throws InterruptedException {
        Platform.runLater(() -> {
            try {
                ObservableList<String> temp = FXCollections.observableArrayList();
                temp.setAll(localModel.getAllUsernames());
                users.set(temp);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets selected exercise property.
     *
     * @param selectedExerciseProperty the selected exercise property
     */
    public void setSelectedExerciseProperty(String selectedExerciseProperty) {
        this.selectedUserProperty.set(selectedExerciseProperty);
    }

    /**
     * Reset the viewmodel.
     */
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

    /**
     * Edit .
     *
     * @return the success
     */
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

    /**
     * Update details for selected user.
     */
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