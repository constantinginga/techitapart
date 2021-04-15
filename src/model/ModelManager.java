package model;

import persistence.*;


import java.util.ArrayList;


public class ModelManager implements Model{
   private CategoryList categoryList;
    private CategoryPersistence categoryDB;
    private AccountPersistence accountDB;
    private ProductPersistence productDB;
    private OrderPersistence orderDB;
    private ArrayList<Product> productListToBuy;
    private OrderList orderList;

   public ModelManager()  {
       categoryList = new CategoryList();
       categoryDB = new CategoryDB();
       /**
        if you want to connect to your local db
         categoryDB = new ProductDB("jdbc:postgresql://localhost:5432/postgres?currentSchema=","techitapart","postgres","password");

        * **/
       productDB = new ProductDB();
       /**
        if you want to connect to your local db
         productDB = new ProductDB("jdbc:postgresql://localhost:5432/postgres?currentSchema=","techitapart","postgres","password");

        * **/
       orderDB = new OrderDB();
       /**
        if you want to connect to your local db
        orderDB  = new ProductDB("jdbc:postgresql://localhost:5432/postgres?currentSchema=","techitapart","postgres","password");

        * **/

       productListToBuy = new ArrayList<>();
       orderList = new OrderList();

   }

   /** Register And Login**/
    @Override
    public User registerUSer(String fName, String lName, String email, String username, String password, Role role) {
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    /**Category **/
    @Override
    public void addCategory(String category) {
        categoryDB.addCategoryDB(category);
    }


    /** Product **/
    @Override
    public void addProductList(ArrayList<Product> products, String categoryName) {
           categoryList.addProductList(products, categoryName);
    }


    @Override
    public void addProduct(Product product, String categoryName)
    {
        Product product1 =   productDB.addProductToCategoryDB(product, categoryName);
        categoryList.addProduct(product1, categoryName);
    }



    @Override
    public Product getProduct(String id, String categoryName)
    {
      return   categoryList.getProductById(id, categoryName);
    }

    @Override
    public ArrayList<Product> getAllProductsInCategory(String categoryName) {
        return null;
    }


    @Override
    public ArrayList<Product> getAllProducts()
    {
     ArrayList<Product> products = new ArrayList<>();
     for (Category category: categoryList.getCategories()){
       products.addAll(category.getProductList());
     }

     return products;
    }


    @Override
    public void addProductToBuy(Product product){
        productListToBuy.add(product);
    }

    @Override
    public void removeProduct(String id) {

    }

    @Override
    public void addOrder() {

    }

    @Override
    public void removeProduct(String id, String categoryName) {
        categoryList.getCategory(categoryName).removeProduct(id);
    }

    @Override
    public void addOrder() {

    }

    @Override
    public void buyProduct(Product product, int quantity, String categoryName, String userName)
    {
        productDB.updateProductQuantityDB(product.getId(), quantity);
        categoryList.buyProduct(product.getName(), quantity,categoryName);
        orderDB.addOrderDB(userName);
    }


}
