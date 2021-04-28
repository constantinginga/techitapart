package model;

import persistence.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;


public class ModelManager implements Model
{
    private CategoryList categoryList;

    private Persistence persistense;

    private UserProfile userProfile;

    private PropertyChangeProxy<String, Integer> property;

    public ModelManager() {
        categoryList = new CategoryList();

        persistense = new PersistentDB();

        property = new PropertyChangeProxy<>(this);

        ArrayList<Category> categories1 = persistense.getAllCategoryDB();


        categoryList.setCategories(categories1);
        System.out.println(" Category \n" + categories1.toString() + "\n");

        for (Category category : categoryList.getCategories()) {

            ArrayList<Product> products = persistense.getAllProductDB(category.getName());
            System.out.println(products.toString());

            category.addProductList(products);
        }


    }

    /**
     * Register And Login
     **/
    // how will this login method work in server client system?
    @Override
    public UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role) {
        User user = persistense.registerNewUserDB(fName, lName, email, username, password, role);
        userProfile = UserProfile.getInstance(username);
        Cart cart = new Cart();

        userProfile.setCart(cart);
        return userProfile;
    }

    // how will this login method work in server client system?
    @Override
    public boolean login(String username, String password) {
        if (persistense.loginDB(username, password)) {
            userProfile = UserProfile.getInstance(username);
            Cart cart = new Cart();
            userProfile.setCart(cart);
            cart.setCartItems(persistense.getAllProductsInCart(username));
            return true;
            //   return userProfile;
        } else {
            return false;
        }
        // return userProfile;
    }


    /**
     * Category
     **/

    @Override
    public ArrayList<String> getAllCategory() {
        return categoryList.getAddCategory();
    }


    /**
     * Product Admin
     **/
    @Override
    public void addProduct(Product product, String categoryName) {
        Product product1 = persistense.addProductToCategoryDB(product, categoryName);
        categoryList.addProduct(product1, categoryName);
    }


    @Override
    public Product getProduct(String id, String categoryName) {
        return categoryList.getProductById(id, categoryName);
    }


    @Override
    public ArrayList<Product> getAllProductsInCategory(String categoryName) {
        return categoryList.getCategory(categoryName).getProductList();
    }

//TODO Remove later after adding categories
    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (Category category : categoryList.getCategories()) {
            products.addAll(category.getProductList());
        }

        return products;
    }

    @Override
    public File getImage(String url) {
        return new File("server\\resources\\images\\" + url);
    }

    @Override
    public void uploadImage(File file, String fileName) {
        try {
            file = Files.copy(file.toPath(), new File("server\\resources\\images\\" + file.getName()).toPath()).toFile();
            file.renameTo(new File("server\\resources\\images\\" + fileName));

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void removeProduct(String id, String categoryName) {
        persistense.removeProductByIdDB(id);
        categoryList.getCategory(categoryName).removeProduct(id);

    }


    @Override
    public void updateProductQuantity(String id, int quantity, String categoryName) {
        persistense.updateProductQuantityDB(id, quantity);
        categoryList.getCategory(categoryName).getProductByID(id).setTotal_quantity(quantity);
        System.out.println("Model manager fire property");
    }


    @Override
    public void updateProductPrice(String id, double price, String categoryName) {
        persistense.updateProductPriceDB(price, id);
        categoryList.getCategory(categoryName).getProductByID(id).setPrice(price);
    }


    /**
     * Cart shop
     **/
    // how will this login method work in server client system?
    @Override
    public void addProductToCart(Product product, int quantity) {
        userProfile.addProductToCart(product, quantity);
        persistense.addProductToCart(Integer.parseInt(product.getId()), quantity, userProfile.getUsername());
    }

    // how will this login method work in server client system?
    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity, String username) {
        userProfile.updateCartItemQuantity(cartItem, quantity);
    }


    @Override
    public void removeProductFromCart(CartItem cartItem, String username) {
        userProfile.removeCartItem(cartItem);
    }

    // how will this login method work in server client system?
    @Override
    public void buy(String username) {
        Order order = new Order(persistense.addOrderDB(username), userProfile.getUsername());
        userProfile.addOrder(order);
        persistense.setOrderId(order.getOrder_id(), username);

        for (CartItem cartItem : userProfile.getAllCartItem()) {

            persistense.decreaseProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
        }

        userProfile.setCart(new Cart());
    }


    @Override
    public void addOrder() {

    }

    ///TODO remove later
    @Override
    public void buyProduct(Product product, int quantity, String categoryName, String userName) {
        //     persistense.registerNewUserDB("Farouk","user", "fdggrewf@dfgre.com","Bob","Comdnbd_12",Role.Consumer);
        // persistense.loginDB("Bob", "Comdnbd_12");
        persistense.decreaseProductQuantity(product.getId(), quantity);
        categoryList.buyProduct(product.getName(), quantity, categoryName);
        property.firePropertyChange("quantity", product.getId(), product.getTotal_quantity());
        System.out.println("Model Manager: ->>>>>>>>>>>>>>>>>>>"+product.getTotal_quantity());

        // persistense.addOrderDB(userName);
    }


    @Override public boolean addListener(
        GeneralListener<String, Integer> listener, String... propertyNames)
    {
        return property.addListener(listener, propertyNames);
    }

    @Override public boolean removeListener(
        GeneralListener<String, Integer> listener, String... propertyNames)
    {
        return property.removeListener(listener, propertyNames);
    }
}
