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


public class ModelManager implements Model {

    private final Persistence persistence;
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
    @Override
    public User registerUSer(User user) {
        try {
            User user1 = persistence.registerNewUserDB(user);
            UserProfile userProfile = UserProfile.getInstance(user.getUsername());
            Cart cart = new Cart();
            userProfile.setCart(cart);
            return user1;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public User login(String username, String password) {

        try {
            String role = persistence.loginDB(username, password);
            UserProfile userProfile = UserProfile.getInstance(username);
            Cart cart = new Cart();
            cart.setCartItems(persistence.getAllProductsInCart(username));
            userProfile.setCart(cart);
            return new User(username, role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());

        }
    }

    @Override
    public User getUser(String username) {
        return persistence.getUser(username);
    }

    @Override
    public void updateUser(User user) {
        persistence.updateDetails(user.getUsername(),
                user.getUsername(), user.getPassword(),
                user.getfName(), user.getlName(), user.getEmail());
    }

    @Override
    public ArrayList<String> getAllUsernames() {
        return persistence.getAllUsernames();
    }


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

    @Override
    public void addProduct(Product product, String categoryName) {
        Product product1 = persistence.addProductToCategoryDB(product, categoryName);
        Gson gson = new Gson();
        String g1 = gson.toJson(product);
        getCategory(categoryName).addProduct(product1);
        getCategory("General").addProduct(product1);
        property.firePropertyChange("addProduct", g1, -1);
    }


    @Override
    public Product getProduct(String id, String categoryName) {
        return getCategory(categoryName).getProduct(id);
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
        String category = getProductCategory(id);
        // removes from general
        getCategory(categoryName).removeProductById(id);
        // removes from specific category
        getCategory(category).removeProductById(id);
        property.firePropertyChange("removeProduct", id, -1);
    }

    private String getProductCategory(String id) {
        for (String key : map.keySet()) {
            if (!key.equals("General")) {
                for (Product p : map.get(key).getAllProduct()) {
                    if (p.getId().equals(id)) return key;
                }
            }
        }
        return null;
    }


    @Override
    public void updateProductQuantity(String id, int quantity, String categoryName) {
        persistence.updateProductQuantityDB(id, quantity);
        getCategory(categoryName).updateProductQuantity(id, quantity);
    }


    @Override
    public void updateProductPrice(String id, double price, String categoryName) {
        persistence.updateProductPriceDB(price, id);
        getCategory(categoryName).updateProductPrice(id, price);
    }

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
                return;
            }
        }
        userProfile.addProductToCart(product, quantity);
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
        for (String category : categories) {
            if (!category.equals("General")) {
                for (Product product : getCategory(category).getAllProduct()) {
                    if (product.getId().equals(id)) product.decreaseQuantity(quantity);
                }
            }
        }
    }

    @Override
    public void buy(String username) {
        UserProfile userProfile = UserProfile.getInstance(username);
        Order order = new Order(persistence.addOrderDB(username), userProfile.getUsername());
        for (CartItem cartItem : userProfile.getAllCartItem()) {
            try {
                decreaseProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
                persistence.decreaseProductQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
                persistence.setOrderId(order.getOrder_id(), username);
                property.firePropertyChange("quantity", cartItem.getProduct().getId(), cartItem.getProduct().getTotal_quantity() - cartItem.getQuantity());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        userProfile.addOrder(order);
        userProfile.setCart(new Cart());
    }

    @Override
    public ArrayList<Order> getAllOrdersByUsername(String username) {
        return persistence.getAllOrderByUsername(username);
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
