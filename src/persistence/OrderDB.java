package persistence;

import model.DateTime;
import model.Order;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderDB implements OrderPersistence {

    private final Connection connection;

    public OrderDB() {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    public OrderDB(String url, String schemaName, String username, String password) {
        try {
            connection = ConnectionDB.getInstance().getConnection(url, schemaName, username, password);
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }


    @Override
    public ArrayList<Order> getAllOrderDB() {
        try (connection) {
            PreparedStatement statement = connection.prepareStatement("SELECT order_id, username, date FROM \"Order\" JOIN \"User\" USING (user_id)");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Order> result = new ArrayList<>();
            while (resultSet.next()) {
                String orderId = resultSet.getString("order_id");
                String username = resultSet.getString("username");
                String time = resultSet.getString("date");
                Order order = new Order(username, time);
                order.setOrder_id(orderId);
                result.add(order);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("issue with connectivity");
        }
    }

    @Override
    public int addOrderDB(String username) {
        try (connection) {
            // create new order in db and get its id
            PreparedStatement statement = connection.prepareStatement("INSERT INTO \"Order\" VALUES(DEFAULT, ?, CURRENT_DATE) RETURNING order_id");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) throw new SQLException("Something went wrong");
            return resultSet.getInt("order_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }
}
