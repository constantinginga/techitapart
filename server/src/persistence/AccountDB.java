package persistence;

import model.Password;
import model.Role;
import model.User;
import model.UserName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDB implements AccountPersistence {


    @Override
    public User registerNewUserDB(String fName, String lName, String email, String username, String password, Role role) {
        User user = null;
        try {

            user = new User(fName, lName, email, new UserName(username), new Password(password));

        } catch (IllegalArgumentException e) {
            throw e;
        }

        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO \"User\"(first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?); ");
            statement.setString(1, user.getfName());
            statement.setString(2, user.getlName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserName().getName());
            statement.setString(5, user.getPassword().getPassword());

            statement.executeUpdate();
//           ResultSet keys = statement.getGeneratedKeys();
//
//           if (keys.next()) return user ;
//           else throw new SQLException("No keys generated");
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Username already exists");
        }
    }

    @Override
    public String loginDB(String username, String password) {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT  * from \"User\" WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next()) ? resultSet.getString("role") : null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Username not exist, maybe you need to SignUp first");
        }
    }


        @Override
    public void updateUserName(String currentUsername, String newUsername) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET username=? WHERE username=?");
            statement.setString(2, currentUsername);
            statement.setString(1, newUsername);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateEmail(String currentUsername, String newEmail) {
        try(Connection connection = ConnectionDB.getInstance().getConnection())  {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET email=? WHERE username=?");
            statement.setString(1, newEmail);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updatePassword(String currentUsername, String newPassword) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET password=? WHERE username=?");
            statement.setString(1, newPassword);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateFName(String currentUsername, String newFName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET first_name=? WHERE username=?");
            statement.setString(1, newFName);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateLName(String currentUsername, String newLName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET last_name=? WHERE username=?");
            statement.setString(1, newLName);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateDetails(String currentUsername, String newUsername, String newPassword, String newFName, String newLName, String newEmail) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET (first_name, last_name, email, username, password) = (?, ?, ?, ?, ?) WHERE username = ?");
            statement.setString(1, newFName);
            statement.setString(2, newLName);
            statement.setString(3, newEmail);
            statement.setString(4, newUsername);
            statement.setString(5, newPassword);
            statement.setString(6, currentUsername);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override public User getUser(String username){
        try (Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"User\" WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                UserName userName = new UserName(username);
                String password = resultSet.getString("password");
                Password passWord = new Password(password);
                String email = resultSet.getString("email");
                User user = new User(firstName,lastName,email,userName,passWord);
                return user;
            }
            else {
                throw new IllegalArgumentException(" connection issue");

            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }
    @Override public String getfName()
    {
        try (Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT first_name FROM \"User\" WHERE username = ?");
            //statement.setString(1, );
            ResultSet resultSet = statement.executeQuery();
            String result = resultSet.getString("first_name");
            return result;
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }

    @Override public String getlName()
    {
        return null;
    }

    @Override public UserName getUsername()
    {
        return null;
    }

    @Override public Password getPassword()
    {
        return null;
    }

    @Override public String email()
    {
        return null;
    }

    @Override public ArrayList<String> getAllUsernames()
    {
        try (Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM \"User\"");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()){
               String username = resultSet.getString("username");
               result.add(username);
            }
            return result;
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }
}
