package model;

import persistence.*;


import java.util.ArrayList;


public class ModelManager implements Model {
    private CategoryList categoryList;

    private Persistense persistense ;

    private UserProfile userProfile;


    public ModelManager() {
        categoryList = new CategoryList();

        persistense = new PersistentDB();



       ArrayList<Category> categories1 =persistense.getAllCategoryDB();


        categoryList.setCategories(categories1);
        System.out.println(" Category \n"+ categories1.toString() +"\n");

        for (Category category : categoryList.getCategories()) {

            ArrayList<Product> products = persistense.getAllProductDB(category.getName());
            System.out.println(products.toString());

            category.addProductList(products);
        }




    }

    /**
     * Register And Login
     **/
    @Override
    public UserProfile registerUSer(String fName, String lName, String email, String username, String password, Role role) {
        User user = persistense.registerNewUserDB(fName, lName, email, username, password, role);
        userProfile = new UserProfile(user.getUserName().getName());
        Cart cart = new Cart();

        userProfile.setCart(cart);
        return userProfile;
    }

    @Override
    public boolean login(String username, String password) {
        if (persistense.loginDB(username, password)) {
            userProfile = new UserProfile(username);
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
    public void addCategory(String category) {
        persistense.addCategoryDB(category);
    }


    /**
     * Product
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


    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (Category category : categoryList.getCategories()) {
            products.addAll(category.getProductList());
        }

        return products;
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
    }


    @Override
    public void updateProductPrice(String id, double price, String categoryName) {
        persistense.updateProductPriceDB(price, id);
        categoryList.getCategory(categoryName).getProductByID(id).setPrice(price);
    }


    /**Cart shop**/

    @Override
    public void addProductToCart(Product product, int quantity) {
        userProfile.addProductToCart(product, quantity);
        persistense.addProductToCart(Integer.parseInt(product.getId()), quantity, userProfile.getUsername());

    }

    @Override
    public void updateCartItemQuantity(CartItem cartItem, int quantity, String username) {
        userProfile.updateCartItemQuantity(cartItem, quantity);


    }


    @Override
    public void removeProductFromCart(CartItem cartItem, String username) {
        userProfile.removeCartItem(cartItem);
    }

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

    @Override
    public void buyProduct(Product product, int quantity, String categoryName, String userName) {
        persistense.decreaseProductQuantity(product.getId(), quantity);
        categoryList.buyProduct(product.getName(), quantity, categoryName);
        persistense.addOrderDB(userName);
    }


}
