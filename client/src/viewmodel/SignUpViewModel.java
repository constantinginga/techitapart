package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.Role;
import model.UserProfile;

import java.rmi.RemoteException;

public class SignUpViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty firstName, lastName, email, username, password, error;

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

    public StringProperty getFirstName() {
        return firstName;
    }

    public StringProperty getLastName() {
        return lastName;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getUsername() {
        return username;
    }

    public StringProperty getPassword() {
        return password;
    }

    public StringProperty getError() {
        return error;
    }

    public void reset() {
        firstName.set(null);
        lastName.set(null);
        email.set(null);
        username.set(null);
        password.set(null);
        error.set(null);
    }

    public boolean signUp() {
        try {
            UserProfile user = model.registerUSer(firstName.get(), lastName.get(), email.get(), username.get(), password.get(), Role.Consumer);
            state.setUserID(user.getUsername());
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return true;
    }
}
