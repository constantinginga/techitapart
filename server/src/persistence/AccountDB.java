package persistence;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDB implements AccountPersistence {


    @Override
    public User registerNewUserDB(User user) {

        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO \"User\"(first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?); ");
            statement.setString(1, user.getfName());
            statement.setString(2, user.getlName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new IllegalArgumentException(errorMessage(e.getMessage()));
        }
    }


    private String errorMessage(String e){
        if (e.contains("ERROR: new row for relation \"User\" violates check constraint \"User_username_check\""))
            return  "Username must be at least 5 characters long";
        else if (e.contains("ERROR: new row for relation \"User\" violates check constraint \"User_password_check\""))
            return  "Password must be at least 6 characters long and contain at least one uppercase character, one number";
        return null;
    }


    @Override
    public String loginDB(String username, String password) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT  * from \"User\" WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                throw new IllegalArgumentException("Username or password is wrong, maybe you need to sign up first");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updateUserName(String currentUsername, String newUsername) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET username=? WHERE username=?");
            statement.setString(2, currentUsername);
            statement.setString(1, newUsername);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(errorMessage(e.getMessage()));

        }
    }

    @Override
    public void updateEmail(String currentUsername, String newEmail) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET email=? WHERE username=?");
            statement.setString(1, newEmail);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(errorMessage(e.getMessage()));

        }
    }


    @Override
    public void updatePassword(String currentUsername, String newPassword) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET password=? WHERE username=?");
            statement.setString(1, newPassword);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(errorMessage(e.getMessage()));

        }
    }

    @Override
    public void updateFName(String currentUsername, String newFName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET first_name=? WHERE username=?");
            statement.setString(1, newFName);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(errorMessage(e.getMessage()));

        }
    }

    @Override
    public void updateLName(String currentUsername, String newLName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE \"User\" SET last_name=? WHERE username=?");
            statement.setString(1, newLName);
            statement.setString(2, currentUsername);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(errorMessage(e.getMessage()));

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
        } catch (SQLException e) {
         throw new IllegalArgumentException(errorMessage(e.getMessage()));

        }
    }


    @Override
    public User getUser(String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"User\" WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(firstName, lastName, email, userName, password);
                return user;
            } else {
                throw new IllegalArgumentException(" connection issue");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }

    @Override
    public String getfName() {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT first_name FROM \"User\" WHERE username = ?");
            ResultSet resultSet = statement.executeQuery();
            String result = resultSet.getString("first_name");
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }

    @Override
    public ArrayList<String> getAllUsernames() {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM \"User\"");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                result.add(username);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }
}
