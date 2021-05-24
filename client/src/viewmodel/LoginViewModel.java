package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;

import java.rmi.RemoteException;

/**
 * The  Login view model.
 */
public class LoginViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty username, password, error;

    /**
     * Instantiates a new Login view model.
     *
     * @param model the model
     * @param state the state
     */
    public LoginViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        error = new SimpleStringProperty();
    }


    /**
     * Gets username.
     *
     * @return the username
     */
    public StringProperty getUsername() {
        return username;
    }


    /**
     * Gets password.
     *
     * @return the password
     */
    public StringProperty getPassword() {
        return password;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public StringProperty getError() {
        return error;
    }

    /**
     * Reset the view.
     */
    public void reset() {
        username.set(null);
        password.set(null);
        error.set(null);
    }

    /**
     * Login method.
     *
     * @return the role of user
     */
    public String login() {
        try {
            User user = model.login(username.get(), password.get());
            state.setUserID(username.get());
            String role = user.getRole();
            state.setRole(role);
            return role;
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}