package persistence;
import model.User;


import java.util.ArrayList;

public interface AccountPersistence {
    User registerNewUserDB(User user);

    String loginDB(String username, String password);

    // Edit
    void updateUserName(String currentUsername, String newUsername);

    void updateEmail(String userName, String newEmail);

    void updatePassword(String username, String newPassword);

    void updateFName(String username, String newFName);

    void updateLName(String username, String newLName);

    void updateDetails(String username, String newUsername, String newPassword, String newFName, String newLName, String newEmail);


    // Get
    User getUser(String username);
    String getfName();
    ArrayList<String> getAllUsernames();
}
