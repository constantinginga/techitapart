package persistence;

import model.Order;
import model.Product;

import java.util.ArrayList;

public interface OrderPersistence {
    ArrayList<Order> getAllOrderDB();
    int addOrderDB(String username);
}
