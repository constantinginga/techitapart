package model;

import com.google.gson.Gson;
import persistence.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ModelManager implements Model
{

    private Persistence persistence;

    private UserProfile userProfile;

    private PropertyChangeAction<String, Integer> property;
    private ArrayList<String> categories;
    private Map<String, Category> map;



    public ModelManager() {
        property = new PropertyChangeProxy<>(this, true);
        persistence = new PersistentDB();
        categories = persistence.getAllCategoryDB();
        map = new HashMap<>();


        for (int i = 0; i < categories.size(); i++) {
            Category category = new Category(categories.get(i));
            for (Product product: persistence.getAllProductDB(categories.get(i))){
                if (product != null) category.addProduct(product);
            }
            map.put(categories.get(i), category);
        }

    }

    public Category getCategory(String Cname){
        return map.get(Cname);
    }


    /**
     * Register And Login
     **/
    // how will this login method work in server client system?
    @Override
    public UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role) {
        try {
            User user = persistence
                    .registerNewUserDB(fName, lName, email, username, password, role);
            userProfile = UserProfile.getInstance(user.getUserName().getName());
            Cart cart = new Cart();
            userProfile.setCart(cart);
            return userProfile;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // how will this login method work in server client system?
    @Override
    public boolean login(String username, String password) {
        UserName checkedUsername;
        Password checkedPassword;
        try {
            checkedUsername = new UserName(username);
            checkedPassword = new Password(password);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (persistence.loginDB(checkedUsername.getName(), checkedPassword.getPassword())) {
            userProfile = UserProfile.getInstance(checkedUsername.getName());
            Cart cart = new Cart();
            userProfile.setCart(cart);
            cart.setCartItems(persistence.getAllProductsInCart(checkedUsername.getName()));
            return true;
            //   return userProfile;
        } else {
            throw new IllegalArgumentException("Account already exists");
        }
        // return userProfile;
    }




    /**
     * Category
     **/
    @Override
    public ArrayList<String> getAllCategory() {
        return categories;
    }



    /**
     * Product
     **/
    @Override
    public void addProduct(Product product, String categoryName) {
        Product product1 = persistence
                .addProductToCategoryDB(product, categoryName);
        Gson gson = new Gson();
        String g1 = gson.toJson(product);
        System.out.println("AddProduct property change in ModelManager");
        getCategory(categoryName).addProduct(product1);
        property.firePropertyChange("addProduct", g1, null);
    }


    @Override
    public Product getProduct(String id, String categoryName) {
        // System.out.println(persistence.getProductByIdDB(id).getTotal_quantity());
        return getCategory(categoryName).getProduct(id);
        // Return persistence.getProductByIdDB(id);
    }


    @Override
    public ArrayList<Product> getAllProductsInCategory(String categoryName) {
        ArrayList<Product> list = new ArrayList<>();
        for(Product product: getCategory(categoryName).getAllProduct()){
            list.add(product);
        }
        return list;
    }


    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (String category : categories) {
            products.addAll(getAllProductsInCategory(category));
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
    public ArrayList<Product> searchForProducts(String productName) {
        return persistence.searchForProducts(productName);
    }


    @Override
    public void removeProduct(String id, String categoryName) {
        persistence.removeProductByIdDB(id);
        ///   categoryList.getCategory(categoryName).removeProduct(id);
        // categoryList.removeProduct(id, categoryName);
        System.out.println("Remove product property change in ModelManager");
        property.firePropertyChange("removeProduct", id, null);

        getCategory(categoryName).removeProductById(id);
    }


    @Override
    public void updateProductQuantity(String id, int quantity, String categoryName) {
        persistence.updateProductQuantityDB(id, quantity);
        //categoryList.getCategory(categoryName).getProductByID(id).setTotal_quantity(quantity);
        getCategory(categoryName).updateProductQuantity(id, quantity);
    }


    @Override
    public void updateProductPrice(String id, double price, String categoryName) {
        persistence.updateProductPriceDB(price, id);
        getCategory(categoryName).updateProductPrice(id, price);
    }


    /**
     * Cart shop
     **/
    // how will this login method work in server client system?
    @Override
    public void addProductToCart (Product product, int quantity) {
        userProfile.addProductToCart(product, quantity);
        persistence.addProductToCart(Integer.parseInt(product.getId()), quantity, userProfile.getUsername());
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
        Order order = new Order(persistence.addOrderDB(username), userProfile.getUsername());
        userProfile.addOrder(order);
        persistence.setOrderId(order.getOrder_id(), username);

        for (CartItem cartItem : userProfile.getAllCartItem()) {

            persistence
                    .decreaseProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
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
        // persistense.addOrderDB(userName);
        property.firePropertyChange("quantity", product.getId(), product.getTotal_quantity() - quantity);
        persistence.decreaseProductQuantity(product.getId(), quantity);
        getCategory(categoryName).decreaseProductQuantity(product.getId(), quantity);
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
