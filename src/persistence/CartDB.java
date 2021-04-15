package persistence;

import model.CartItem;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDB implements CartPersistence {

    private final Connection connection;

    public CartDB() {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    public CartDB(String url, String schemaName, String username, String password) {
        try {
            connection = ConnectionDB.getInstance().getConnection(url, schemaName, username, password);
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }


    @Override
    public ArrayList<CartItem> getOrderedProducts(int orderId) {
        try (connection) {
            // update cart items
            PreparedStatement statement = connection.prepareStatement("UPDATE CartItem SET order_id = ? WHERE order_id IS NULL");
            statement.setInt(1, orderId);
            // select items for current order
            statement = connection.prepareStatement("SELECT * FROM CartItem JOIN Product USING (product_id) WHERE order_id = ?");
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CartItem> result = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String img = resultSet.getString("image");
                String username = resultSet.getString("username");
                int quantity = resultSet.getInt("quantity");
                int total_quantity = resultSet.getInt("total_quantity");
                int cartItemId = resultSet.getInt("cartitem_id");
                double price = resultSet.getDouble("price");
                Product product = new Product(String.valueOf(productId), name, description, total_quantity, price);
                product.setImgSrc(img);
                CartItem item = new CartItem(product, quantity, username);
                item.setId(cartItemId);
                result.add(item);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public void addProduct(int product_id, int quantity, String username) {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CartItem(quantity, product_id, username) VALUES (?, ?, ?)");
            statement.setInt(1, quantity);
            statement.setInt(2, product_id);
            statement.setString(3, username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeProduct(int product_id, String username) {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CartItem WHERE product_id = ? AND username = ?");
            statement.setInt(1, product_id);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeProduct(int cartItemId) {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CartItem WHERE cartitem_id = ?");
            statement.setInt(1, cartItemId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<CartItem> getAllProducts(String username) {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM CartItem JOIN Product USING (product_id) WHERE username = ? AND order_id IS NULL");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CartItem> result = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String img = resultSet.getString("image");
                int quantity = resultSet.getInt("quantity");
                int total_quantity = resultSet.getInt("total_quantity");
                int cartItemId = resultSet.getInt("cartitem_id");
                double price = resultSet.getDouble("price");
                Product product = new Product(String.valueOf(productId), name, description, total_quantity, price);
                product.setImgSrc(img);
                CartItem item = new CartItem(product, quantity, username);
                item.setId(cartItemId);
                result.add(item);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
