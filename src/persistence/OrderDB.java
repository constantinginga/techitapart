package persistence;

import model.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDB implements OrderPersistence {
    private Connection connection ;

    public OrderDB() {
        try {
            connection = ConnectionDB.getInstance().getConnection();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    public OrderDB(String url, String schemaName, String username, String password){
        try {
            connection = ConnectionDB.getInstance().getConnection(url, schemaName, username, password);
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    @Override
    public ArrayList<Order> getAllOrderDB() {
        return null;
    }

    @Override
    public void addOrderDB(String username) {

    }

 /*   @Override
    public boolean removeOrderDB(String username) {
        return false;
    }*/
}
