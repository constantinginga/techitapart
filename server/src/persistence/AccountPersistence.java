package persistence;

import model.Role;
import model.User;

public interface AccountPersistence {
    User registerNewUserDB(String fName, String lName, String email, String username, String password, Role role);
    String loginDB(String username, String password);

    // Edit
    void updateUserName(String currentUsername, String newUsername);
    void updateEmail(String userName, String newEmail);
    void updatePassword(String username, String newPassword);
    void updateFName(String username, String newFName);
    void updateLName(String username, String newLName);
    void updateDetails(String username, String newUsername, String newPassword, String newFName, String newLName, String newEmail);

}
