package persistence;

import model.Password;
import model.Role;
import model.User;
import model.UserName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDB implements AccountPersistence {

    private Connection connection ;

    public AccountDB() {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    public AccountDB(String url, String schemaName, String username, String password){
        try {
            connection = ConnectionDB.getInstance().getConnection(url, schemaName, username, password);
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

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
            throw new IllegalArgumentException("Can't connect to db");
        }
    }

    @Override
    public boolean loginDB(String username, String password) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT  * from \"User\" WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
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
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
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
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
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
}
