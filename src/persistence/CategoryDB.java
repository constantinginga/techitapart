package persistence;

import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDB implements CategoryPersistence {


    @Override
    public ArrayList<Category> getAllCategoryDB() {
        return null;
    }

    @Override
    public void addCategoryDB(String categoryName) {
        try(Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Category(category_name) VALUES (?)");
            statement.setString(1, categoryName);
            statement.executeUpdate();

        }catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

   /* @Override
    public void removeCategoryDB(String categoryName) {

    }

    @Override
    public void updateCategoryDB(String categoryName) {

    }*/
}
