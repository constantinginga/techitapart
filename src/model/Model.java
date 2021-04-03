package model;

import java.util.ArrayList;

public interface Model {
    void addCategory(Category category);
    void addProductList(ArrayList<Product> products, String categoryName);
    void addProduct(Product product, String categoryName);
    Product getProduct(String id, String categoryName);
    void buyProduct(String productName, int quantity, String categoryName, String username);

}
