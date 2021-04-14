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
    @Override
    public User registerNewUserDB(String fName, String lName, String email, String username, String password, Role role) {
        User user = null;
        try {

            user = new User(fName, lName, email, new UserName(username), new Password(password));

        } catch (IllegalArgumentException e) {
            throw e;
        }

        try( Connection connection = ConnectionDB.getInstance().getConnection()){
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

        }catch (SQLException e){
            e.printStackTrace();
            throw  new IllegalArgumentException("Can't connect to db");
        }
    }

    @Override
    public boolean loginDB(String username, String password) {
        try( Connection connection = ConnectionDB.getInstance().getConnection()) {

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

    }

    @Override
    public void updateEmail(String userName, String newEmail) {

    }

    @Override
    public void updatePassword(String username, String newPassword) {

    }

    @Override
    public void updateFName(String username, String newFName) {

    }

    @Override
    public void updateLName(String username, String newLName) {

    }

    @Override
    public void updateDetails(String username, String newUsername, String newPassword, String newFName, String newLName, String newEmail) {

    }
}
