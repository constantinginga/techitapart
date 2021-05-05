package persistence;

import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDB implements CategoryPersistence {


    @Override
    public ArrayList<String> getAllCategoryDB() {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT category_name FROM category");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("category_name");
                result.add(name);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }

    @Override
    public void addCategoryDB(String categoryName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Category(category_name) VALUES (?)");
            statement.setString(1, categoryName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
