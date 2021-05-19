package persistence;

import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDB implements OrderPersistence {


    @Override
    public ArrayList<Order> getAllOrderDB() {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT order_id, username, date FROM \"Order\" JOIN \"User\" USING (username)");
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
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
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

    @Override
    public ArrayList<Order> getAllOrderByUsername(String username) {
        try (Connection connection = ConnectionDB.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT order_id, username, date FROM \"Order\" WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Order> result = new ArrayList<>();
            while (resultSet.next()) {
                String orderId = resultSet.getString("order_id");
                String userName = resultSet.getString("username");
                String time = resultSet.getString("date");
                Order order = new Order(userName, time);
                order.setOrder_id(orderId);
                result.add(order);

            }

            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new IllegalArgumentException("issue with connectivity");
        }
    }
}
