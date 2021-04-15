package model;

import java.util.ArrayList;

public interface Model {

    /** Account **/
    User registerUSer(String fName, String lName, String email, String username, String password, Role role );
    boolean login(String username, String password);

    /** Category **/
    void addCategory(String categoryName);

    /** Product **/
    void addProductList(ArrayList<Product> products, String categoryName);
    void addProduct(Product product, String categoryName);
    Product getProduct(String id, String categoryName);
  //  void buyProduct(String productName, int quantity, String categoryName, String username);
    ArrayList<Product> getAllProductsInCategory(String categoryName);
    ArrayList<Product> getAllProducts();
    void buyProduct(Product product, int quantity, String categoryName, String userName);
    public void addProductToBuy(Product product);
    public void removeProduct(String id, String categoryName);
    /** order **/
    void addOrder();
}
