package persistence;

import model.DateTime;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderDB implements OrderPersistence {
    @Override
    public ArrayList<Order> getAllOrderDB()
    {
        try(Connection connection = ConnectionDB.getInstance().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT order_id, username, date FROM \"Order\" JOIN \"User\" USING (user_id)");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Order> result = new ArrayList<>();
            while(resultSet.next())
            {
                String orderId = resultSet.getString("order_id");
                String username = resultSet.getString("username");
                String time = resultSet.getString("date");
                Order order = new Order(username, time);
                order.setOrder_id(orderId);
                result.add(order);
            }
            return result;
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new IllegalArgumentException("issue with connectivity");
        }
    }

    @Override
    public void addOrderDB(String username)
    {
        try(Connection connection = ConnectionDB.getInstance().getConnection())
        {
         PreparedStatement statement = connection.prepareStatement("INSERT ");
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

 /*   @Override
    public boolean removeOrderDB(String username) {
        return false;
    }*/
}
