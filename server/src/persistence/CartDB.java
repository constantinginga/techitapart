package persistence;

import model.CartItem;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDB implements CartPersistence {



    @Override
    public ArrayList<CartItem> getOrderedProducts(int orderId) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {

          PreparedStatement  statement = connection.prepareStatement("SELECT * FROM CartItem JOIN Product USING (product_id) WHERE order_id = ?");
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
                CartItem item = new CartItem(product, quantity);
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
    public int cartItemExists(int product_id, String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT quantity FROM CartItem WHERE product_id = ? AND username = ? AND order_id IS NULL");
            statement.setInt(1, product_id);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt("quantity");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    @Override
    public void addProductToCart(int product_id, int quantity, String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            // add item to cart if it doesn't exist, otherwise increase its quantity
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO CartItem(quantity, product_id, username) VALUES (?, ?, ?) ON CONFLICT (product_id, username) DO UPDATE SET quantity = EXCLUDED.quantity + CartItem.quantity");
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
    public void updateCartItemQuantity(int product_id, int quantity, String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE cartItem SET quantity = quantity + ?  WHERE product_id =? AND username = ?  ");
            statement.setInt(1, quantity);
            statement.setInt(2, product_id);
            statement.setString(3, username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("DB issue with connection ");
        }
    }

    @Override
    public void removeCartItem(int product_id, String username) {
        try  (Connection connection = ConnectionDB.getInstance().getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CartItem WHERE product_id = ? AND username = ?");
            statement.setInt(1, product_id);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeCartItem(int cartItemId) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CartItem WHERE cartitem_id = ?");
            statement.setInt(1, cartItemId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ArrayList<CartItem> getAllProductsInCart(String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()){
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
                CartItem item = new CartItem(product, quantity);
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
    public void setOrderId(int orderId, String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE CartItem SET order_id=? WHERE username = ? AND order_id IS NULL");
            statement.setInt(1, orderId);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
