package persistence;

import model.Order;

import java.util.ArrayList;

public interface OrderPersistence {
    ArrayList<Order> getAllOrderDB();
    void addOrderDB(String username);

   // boolean removeOrderDB(String username);
}
