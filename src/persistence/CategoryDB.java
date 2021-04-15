package persistence;

import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDB implements CategoryPersistence {

    private final Connection connection;

    public CategoryDB() {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    public CategoryDB(String url, String schemaName, String username, String password) {
        try {
            connection = ConnectionDB.getInstance().getConnection(url, schemaName, username, password);
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    @Override
    public ArrayList<Category> getAllCategoryDB() {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("SELECT category_name FROM category");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Category> result = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("category_name");
                Category category = new Category(name);
                result.add(category);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException(" connection issue");
        }
    }

    @Override
    public void addCategoryDB(String categoryName) {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Category(category_name) VALUES (?)");
            statement.setString(1, categoryName);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
