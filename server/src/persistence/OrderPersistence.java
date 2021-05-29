package persistence;

import model.Order;

import java.util.ArrayList;

public interface OrderPersistence {
    ArrayList<Order> getAllOrderDB();

    int addOrderDB(String username);

    ArrayList<Order> getAllOrderByUsername(String username);
}
