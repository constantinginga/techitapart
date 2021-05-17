package model;

import com.google.gson.Gson;
import javafx.application.Platform;
import persistence.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeAction;
import utility.observer.subject.PropertyChangeProxy;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ModelManager implements Model {

    private final Persistence persistence;

    // private UserProfile userProfile;

    private final PropertyChangeAction<String, Integer> property;
    private final ArrayList<String> categories;
    private final Map<String, Category> map;


    public ModelManager() {
        property = new PropertyChangeProxy<>(this, true);
        persistence = new PersistentDB();
        categories = persistence.getAllCategoryDB();
        map = new HashMap<>();

        for (String s : categories) {
            Category category = new Category(s);
            for (Product product : persistence.getAllProductDB(s)) {
                if (product != null) category.addProduct(product);
            }
            map.put(s, category);
        }
        map.put("General", new Category("General"));
        map.get("General").getAllProduct().addAll(getAllProducts());
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
            UserProfile userProfile = UserProfile.getInstance(user.getUserName().getName());
            Cart cart = new Cart();
            userProfile.setCart(cart);
            return userProfile;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // how will this login method work in server client system?
    @Override
    public UserProfile login(String username, String password) {

        try {
            String role = persistence.loginDB(username, password);
            UserProfile userProfile = UserProfile.getInstance(username);
            userProfile.setRole(role);
            Cart cart = new Cart();
            cart.setCartItems(persistence.getAllProductsInCart(username));
            userProfile.setCart(cart);
            return userProfile;
            //   return userProfile;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Username not exist");

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

    @Override
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        for (String key : map.keySet()) {
            categories.add(map.get(key));
        }

        return categories;
    }

    @Override
    public Category getCategory(String name) {
        return map.get(name);
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
        return (categoryName.equals("General")) ? getAllProducts() : map.get(categoryName).getAllProduct();
    }


    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        for (String key : map.keySet()) {
            if (!key.equals("General")) products.addAll(map.get(key).getAllProduct());
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

        } catch (Exception e) {
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
    public void addProductToCart(Product product, int quantity, String username) {
        int quantityInDB = persistence.cartItemExists(Integer.parseInt(product.getId()), username);
        if (quantityInDB != -1) {
            if (quantityInDB + quantity > product.getTotal_quantity())
                throw new IllegalArgumentException("Quantity exceeds stock");
        }
        UserProfile userProfile = UserProfile.getInstance(username);
        persistence.addProductToCart(Integer.parseInt(product.getId()), quantity, userProfile.getUsername());

        for (CartItem cartItem : userProfile.getAllCartItem()) {
            if ((cartItem.getProduct()).getId().equals(product.getId())) {
                userProfile.updateCartItemQuantity(cartItem, quantity);
                System.out.println(product.getId() +"<<-------------------<<"+quantity);
                return;
            }
        }
        userProfile.addProductToCart(product, quantity);
        System.out.println(userProfile.getCart().getCartItems().toString());
    }

    @Override
    public ArrayList<CartItem> getProductsFromCart(String username) {
        UserProfile userProfile = UserProfile.getInstance(username);
        return (userProfile != null) ? userProfile.getAllCartItem() : null;
    }

    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity, String username) {
        UserProfile userProfile = UserProfile.getInstance(username);
        try {
            userProfile.updateCartItemQuantity(cartItem, quantity);
            persistence.updateCartItemQuantity(Integer.parseInt(cartItem.getProduct().getId()), quantity, username);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }


    @Override
    public void removeProductFromCart(CartItem cartItem, String username) {
        UserProfile userProfile = UserProfile.getInstance(username);
        userProfile.removeCartItem(cartItem);
        persistence.removeCartItem(Integer.parseInt(cartItem.getProduct().getId()), username);
    }

    @Override
    public void decreaseProductQuantity(String id, int quantity) {
        for(String category: categories){
            if (!category.equals("General")) {
                for (Product product: getCategory(category).getAllProduct()){
                    if (product.getId().equals(id)) product.decreaseQuantity(quantity);
                }
            }
        }
    }

    @Override
    public void buy(String username) {
        UserProfile userProfile = UserProfile.getInstance(username);
        for (CartItem cartItem : userProfile.getAllCartItem()) {
            try {
                decreaseProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
                persistence.decreaseProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
                Order order = new Order(persistence.addOrderDB(username), userProfile.getUsername());
                userProfile.addOrder(order);
                persistence.setOrderId(order.getOrder_id(), username);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
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

    @Override
    public boolean addListener(
            GeneralListener<String, Integer> listener, String... propertyNames) {
        return property.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(
            GeneralListener<String, Integer> listener, String... propertyNames) {
        return property.removeListener(listener, propertyNames);
    }
}
