package persistence;

import model.*;

import java.util.ArrayList;

public class PersistentDB implements Persistence {
    AccountPersistence accountDB = new AccountDB();
    CartPersistence cartDB = new CartDB();
    CategoryPersistence categoryDB = new CategoryDB();
    OrderPersistence orderDB = new OrderDB();
    ProductPersistence productDB = new ProductDB();




    @Override
    public User registerNewUserDB(String fName, String lName, String email, String username, String password, Role role) {
        return accountDB.registerNewUserDB(fName, lName, email, username, password, role);
    }

    @Override
    public boolean loginDB(String username, String password) {
        return accountDB.loginDB(username, password);
    }

    @Override
    public void updateUserName(String currentUsername, String newUsername) {
        accountDB.updateUserName(currentUsername, newUsername);
    }

    @Override
    public void updateEmail(String userName, String newEmail) {
        accountDB.updateEmail(userName, newEmail);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        accountDB.updatePassword(username, newPassword);
    }

    @Override
    public void updateFName(String username, String newFName) {
        accountDB.updateFName(username, newFName);
    }

    @Override
    public void updateLName(String username, String newLName) {
            accountDB.updateLName(username, newLName);
    }

    @Override
    public void updateDetails(String username, String newUsername, String newPassword, String newFName, String newLName, String newEmail) {
            accountDB.updateDetails(username, newUsername, newPassword, newFName, newLName, newEmail);
    }

    @Override
    public ArrayList<CartItem> getOrderedProducts(int orderId) {
        return cartDB.getOrderedProducts(orderId);
    }

    @Override
    public void addProductToCart(int product_id, int quantity, String username) {
        cartDB.addProductToCart(product_id, quantity, username);
    }

    @Override
    public void updateCartItemQuantity(int product_id, int quantity, String username) {
        cartDB.updateCartItemQuantity(product_id, quantity, username);
    }

    @Override
    public void removeCartItem(int product_id, String username) {
        cartDB.removeCartItem(product_id, username);
    }

    @Override
    public void removeCartItem(int cartItemId) {
        cartDB.removeCartItem(cartItemId);
    }

    @Override
    public ArrayList<CartItem> getAllProductsInCart(String username) {
        return cartDB.getAllProductsInCart(username);
    }

    @Override
    public void setOrderId(int orderId, String username) {
            cartDB.setOrderId(orderId, username);
    }

    @Override
    public ArrayList<Category> getAllCategoryDB() {
        return categoryDB.getAllCategoryDB();
    }

    @Override
    public void addCategoryDB(String categoryName) {
     categoryDB.addCategoryDB(categoryName);
    }

    @Override
    public ArrayList<Order> getAllOrderDB() {
        return orderDB.getAllOrderDB();
    }

    @Override
    public int addOrderDB(String username) {
        return orderDB.addOrderDB(username);
    }

    @Override
    public Product addProductToCategoryDB(Product product, String categoryName) {
        return productDB.addProductToCategoryDB(product, categoryName);
    }

    @Override
    public void updateProductQuantityDB(String productId, int quantity) {
        productDB.updateProductQuantityDB(productId, quantity);
    }

    @Override
    public void updateProductPriceDB(double price, String productId) {
        productDB.updateProductPriceDB(price, productId);
    }

    @Override
    public void updateProductDescriptionDB(String description, String productId) {
        productDB.updateProductDescriptionDB(description, productId);
    }

    @Override
    public void decreaseProductQuantity(String productId, int quantity) {
            productDB.decreaseProductQuantity(productId, quantity);
    }

    @Override
    public void updateProductImageDB(String productId, String image) {
        productDB.updateProductImageDB(productId, image);
    }

    @Override
    public Product getProductByIdDB(String id) {
        return productDB.getProductByIdDB(id);
    }

    @Override
    public void removeProductByIdDB(String id) {
            productDB.removeProductByIdDB(id);
    }

    @Override
    public ArrayList<Product> getAllProductDB(String categoryName) {
        return productDB.getAllProductDB(categoryName);
    }
}
