package model;

import java.util.ArrayList;

public class ModelManager implements Model{
   private CategoryList categoryList;
   private UserList users;

   public ModelManager(){
       categoryList = new CategoryList();
       users = new UserList();
       categoryList.addCategory(new Category("General"));
       for (int i = 0 ; i<20; i++){
         categoryList.addProduct(new Product("Iphone 12" + i,"Epic iphone 12",12,2),"General");
       }
   }

    @Override
    public void addCategory(Category category) {
        categoryList.addCategory(category);
    }

    @Override
    public void addProductList(ArrayList<Product> products, String categoryName) {
           // categoryList.
    }

    @Override
    public void addProduct(Product product, String categoryName) {
            categoryList.addProduct(product, categoryName);
    }

    @Override
    public Product getProduct(String id, String categoryName) {
      return   categoryList.getProductById(id, categoryName);
    }

    @Override
    public ArrayList<Product> getAllProducts(){
     ArrayList<Product> products = new ArrayList<>();
     for (Category category: categoryList.getCategories()){
       products.addAll(category.getProductList());
     }

     return products;
    }
    @Override
    public void buyProduct(String productName, int quantity, String categoryName, String username) {
            categoryList.buyProduct(productName,quantity,categoryName);
            users.getUserByName(username).addOrder(new Order(username));
    }

}


























