package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;
import model.Password;
import model.User;
import model.UserName;

import java.rmi.RemoteException;

public class UserViewModel
{
  private LocalModel localModel;
  private ViewState state;
  private StringProperty firstName;
  private StringProperty lastName;
  private StringProperty email;
  private StringProperty username;
  private StringProperty password;
  private StringProperty error;

  public UserViewModel(LocalModel localModel, ViewState viewState)
      throws RemoteException
  {
    this.localModel = localModel;
    this.state = viewState;
    this.firstName = new SimpleStringProperty();
    this.lastName = new SimpleStringProperty();
    this.email = new SimpleStringProperty();
    this.username = new SimpleStringProperty();
    this.password = new SimpleStringProperty();
    this.error = new SimpleStringProperty();
  }

  public StringProperty firstNameProperty()
  {
    return firstName;
  }

  public StringProperty lastNameProperty()
  {
    return lastName;
  }

  public StringProperty emailProperty()
  {
    return email;
  }

  public StringProperty usernameProperty()
  {
    return username;
  }

  public StringProperty passwordProperty()
  {
    return password;
  }

  public StringProperty errorProperty()
  {
    return error;
  }

  public void reset()
  {
    try
    {
      User user = localModel.getUser(state.getUserID());
      firstName.set(user.getfName());
      lastName.set(user.getlName());
      email.set(user.getEmail());
      username.set(user.getUserName().getName());
      password.set(user.getPassword().getPassword());
      error.set("");
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  public boolean edit()
  {
    try
    {
      UserName userName = new UserName(username.get());
      Password passWord = new Password(password.get());
      User user = new User(firstName.get(), lastName.get(), email.get(), userName, passWord);
      localModel.updateUser(user);
    }
    catch (IllegalArgumentException | RemoteException e)
    {
      error.set(e.getMessage());
      return false;
    }
    return true;
  }
}
