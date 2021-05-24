package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;

import java.rmi.RemoteException;

/**
 * The Sign up view model.
 */
public class SignUpViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty firstName, lastName, email, username, password, error;

    /**
     * Instantiates a new Sign up view model.
     *
     * @param model the model
     * @param state the state
     */
    public SignUpViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public StringProperty getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public StringProperty getLastName() {
        return lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public StringProperty getEmail() {
        return email;
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
     * Reset the viewmodel.
     */
    public void reset() {
        firstName.set(null);
        lastName.set(null);
        email.set(null);
        username.set(null);
        password.set(null);
        error.set(null);
    }

    /**
     * Sign up .
     *
     * @return the success
     */
    public boolean signUp() {
        try {
            User userCheck = new User(firstName.get(), lastName.get(), email.get(), username.get(), password.get());
            User user = model.registerUSer(userCheck);
            state.setUserID(user.getUsername());
            state.setRole("consumer");
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return true;
    }
}
