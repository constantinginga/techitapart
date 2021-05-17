package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.UserProfile;

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
            UserProfile userProfile = model.login(username.get(), password.get());
            state.setUserID(username.get());
            return userProfile.getRole();
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}