package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.User;

import java.rmi.RemoteException;

public class LoginViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty username, password, error;

    public LoginViewModel(LocalModel model, ViewState state) {
        this.model = model;
        this.state = state;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        error = new SimpleStringProperty();
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
        username.set(null);
        password.set(null);
        error.set(null);
    }

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