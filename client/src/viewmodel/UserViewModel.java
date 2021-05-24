package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;

import java.rmi.RemoteException;

/**
 * The User view model.
 */
public class UserViewModel {
    private LocalModel localModel;
    private ViewState state;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;
    private StringProperty username;
    private StringProperty password;
    private StringProperty error;

    /**
     * Instantiates a new User view model.
     *
     * @param localModel the local model
     * @param viewState  the view state
     * @throws RemoteException the remote exception
     */
    public UserViewModel(LocalModel localModel, ViewState viewState)
            throws RemoteException {
        this.localModel = localModel;
        this.state = viewState;
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
    }

    /**
     * First name property.
     *
     * @return the string property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Last name property.
     *
     * @return the string property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Email property.
     *
     * @return the string property
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Username property.
     *
     * @return the string property
     */
    public StringProperty usernameProperty() {
        return username;
    }

    /**
     * Password property .
     *
     * @return the string property
     */
    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * Error property.
     *
     * @return the string property
     */
    public StringProperty errorProperty() {
        return error;
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
     * Edit the user.
     *
     * @return the boolean
     */
    public boolean edit() {
        try {
            User user = new User(firstName.get(), lastName.get(), email.get(), username.get(), password.get());
            localModel.updateUser(user);
        } catch (IllegalArgumentException | RemoteException e) {
            System.out.println(e.getMessage());
            error.set(e.getMessage());
            return false;
        }
        return true;
    }
}