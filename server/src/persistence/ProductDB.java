package persistence;

import model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB implements ProductPersistence {


    @Override
    public Product addProductToCategoryDB(Product product, String categoryName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Product(category_name, description,price, image, total_quantity,name) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, categoryName);
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setString(4, product.getImgSrc());
            statement.setInt(5, product.getTotal_quantity());
            statement.setString(6, product.getName());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Product product1 = new Product(String.valueOf(keys.getInt(1)), product.getName(), product.getDescription(), product.getTotal_quantity(), product.getPrice());
                product1.setImgSrc(product.getImgSrc());
                return product1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
        return null;
    }


    @Override
    public void updateProductQuantityDB(String productId, int quantity) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Product SET total_quantity = ? WHERE product_id=?");
            statement.setInt(1, quantity);
            statement.setInt(2, Integer.parseInt(productId));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection ");
        }
    }


    @Override
    public void decreaseProductQuantity(String productId, int quantity) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Product SET total_quantity = total_quantity - ?  WHERE product_id = ?");
            statement.setInt(1, quantity);
            statement.setInt(2, Integer.parseInt(productId));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection");
        }
    }


    @Override
    public void updateProductImageDB(String productId, String image) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Product SET image = ? WHERE product_id=?");
            statement.setString(1, image);
            statement.setInt(2, Integer.parseInt(productId));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection ");
        }
    }


    @Override
    public void updateProductPriceDB(double price, String productId) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Product SET price = ? WHERE product_id=?");
            statement.setDouble(1, price);
            statement.setInt(2, Integer.parseInt(productId));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection ");
        }
    }


    @Override
    public void updateProductDescriptionDB(String description, String productId) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE Product SET description = ? WHERE product_id=?");
            statement.setString(1, description);
            statement.setInt(2, Integer.parseInt(productId));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection ");
        }
    }


    @Override
    public Product getProductByIdDB(String id) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product WHERE product_id =?");
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                String img = resultSet.getString("image");
                int total_quantity = resultSet.getInt("total_quantity");
                double price = resultSet.getDouble("price");
                Product product = new Product(String.valueOf(productId), name, description, total_quantity, price);
                product.setImgSrc(img);
                return product;
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throw new IllegalArgumentException("DB issue with connectivity");
        }
    }

    @Override
    public void removeProductByIdDB(String id) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Product WHERE product_id=?");

            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection ");
        }
    }


    @Override
    public ArrayList<Product> getAllProductDB(String categoryName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product WHERE category_name = ?");
            statement.setString(1, categoryName);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Product> result = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String img = resultSet.getString("image");
                int total_quantity = resultSet.getInt("total_quantity");
                double price = resultSet.getDouble("price");
                Product product = new Product(String.valueOf(productId), name, description, total_quantity, price);
                product.setImgSrc(img);
                result.add(product);
            }
            return result;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connectivity");
        }
    }

    @Override
    public ArrayList<Product> searchForProducts(String productName) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product WHERE name ~* ?");
            statement.setString(1, ".*" + productName + ".*");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Product> result = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String img = resultSet.getString("image");
                int total_quantity = resultSet.getInt("total_quantity");
                double price = resultSet.getDouble("price");
                Product product = new Product(String.valueOf(productId), name, description, total_quantity, price);
                product.setImgSrc(img);
                result.add(product);
            }
            return result;

        } catch (SQLException throwables) {

            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connectivity");
        }
    }

}
