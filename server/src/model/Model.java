package model;

import utility.observer.subject.LocalSubject;

import java.io.File;
import java.util.ArrayList;

/**
 * The interface of  Model.
 */
public interface Model extends LocalSubject<String, Integer> {

    /**
     * Account  @param user to register user
     *
     * @return the new User
     */
    User registerUSer(User user);

    /**
     * Login user.
     *
     * @param username the username
     * @param password the password
     * @return the Logged in user
     */
    User login(String username, String password);

    /**
     * Gets user using @param.
     *
     * @param username the username of user
     * @return the user
     */
    User getUser(String username);

    /**
     * Update user.
     *
     * @param user the updated user
     */
    void updateUser(User user);

    /**
     * Gets all usernames.
     *
     * @return all usernames
     */
    ArrayList<String> getAllUsernames();


    /**
     * @return list of Categories
     */
    ArrayList<String> getAllCategory();

    /**
     * Gets category by @param.
     *
     * @param name the name of category
     * @return the Category
     */
    Category getCategory(String name);

    /**
     * Gets all categories.
     *
     * @return all categories
     */
    ArrayList<Category> getAllCategories();

    /**
     * Adds product in system
     *
     * @param categoryName the category name to add a product to
     */
    void addProduct(Product product, String categoryName);

    /**
     * Gets product using id and category.
     *
     * @param id           the id of product
     * @param categoryName the category name of product
     * @return the product
     */
    Product getProduct(String id, String categoryName);

    /**
     * Remove product from system.
     *
     * @param id           the id of product
     * @param categoryName the category name product is in
     */
    void removeProduct(String id, String categoryName);

    /**
     * Update quantity of product.
     *
     * @param id           the id of product
     * @param quantity     the new quantity of new product
     * @param categoryName the category name product is in
     */
    void updateProductQuantity(String id, int quantity, String categoryName);

    /**
     * Update products price.
     *
     * @param id           the id of product
     * @param price        the  new price
     * @param categoryName the category name product is in
     */
    void updateProductPrice(String id, double price, String categoryName);

    /**
     * Gets all products in category.
     *
     * @param categoryName the category name
     * @return the all products in category
     */
    ArrayList<Product> getAllProductsInCategory(String categoryName);

    /**
     * Gets all products in system.
     *
     * @return the all products
     */
    ArrayList<Product> getAllProducts();

    /**
     * Gets image.
     *
     * @param url the url
     * @return the image
     */
    File getImage(String url);

    /**
     * Uploads image.
     *
     * @param file     the image
     * @param fileName the file name
     */
    void uploadImage(File file, String fileName);

    /**
     * Search for products
     *
     * @param productName the product name
     * @return the array list of products
     */
    ArrayList<Product> searchForProducts(String productName);

    /**
     * update cart product quantity
     *
     * @param quantity the quantity
     * @param username the username
     */
    void addProductToCart(Product product, int quantity, String username);

    /**
     * Gets products from cart.
     *
     * @param username the username
     * @return the products from cart
     */
    ArrayList<CartItem> getProductsFromCart(String username);

    /**
     * Update cart item quantity.
     *
     * @param cartItem the cart item
     * @param quantity the quantity
     * @param username the username
     */
    void updateCartItemQuantity(CartItem cartItem, int quantity, String username);

    /**
     * Remove product from cart.
     *
     * @param cartItem the cart item
     * @param username the username
     */
    void removeProductFromCart(CartItem cartItem, String username);

    /**
     * Decrease product quantity.
     *
     * @param id       the product id
     * @param quantity the quantity
     */
    void decreaseProductQuantity(String id, int quantity);

    /**
     * Buy items in shopping cart.
     *
     * @param username the username
     */
    void buy(String username);


    /**
     * order  for the @param username
     *
     * @return the all orders by username
     */
    ArrayList<Order> getAllOrdersByUsername(String username);
}
