package model;


import persistence.CategoryDB;
import persistence.ProductDB;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {

       // jdbc:postgresql://localhost:5432/postgres?currentSchema=techitapart","postgres", "farouk_12"
        ProductDB productDB = new ProductDB("jdbc:postgresql://localhost:5432/postgres?currentSchema=","techitapart","postgres","farouk_12");
   //     CategoryDB categoryDB = new CategoryDB();
     //   categoryDB.addCategoryDB("General");

     /*   try {
            User user = new User("Farouk", " ", "farouk@gmail.com",new UserName("farouk"), new Password("Farouk_"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
*/

    //   MyDB myDB =  MyDB.getInstance();

      //  System.out.println(myDB.getProduct(1));

     //   Registry registry = new Registry();
/*
        try {
            System.out.println(registry.registerUser("Jakub", "Jakub", "jakub@gmail.com","newUser12345667", "VeryAngry_12345").toString());
        }catch (Exception e){
            System.out.println("FROM MAIN: " + e.getMessage());
        }*/

      //  System.out.println("is logged: "+registry.login("john", "bigdick123"));

      //  CategoryList cList = new CategoryList();

        //System.out.println(productDB.getProductByIdDB("4"));



        Product product = new Product(" new product  product", "New phone",123,123.3);
        System.out.println(product);
       // productDB.updateProductQuantityDB("3",200);
      //  productDB.updateProductPriceDB(20, "3");

     //   productDB.removeProductByIdDB("3");

        Product product1 =   productDB.addProductToCategoryDB(product, "Computer");

        System.out.println(product1);
     //  System.out.println(productDB.getAllProductDB("General"));
       // categoryList.addProduct(product1, categoryName);

     /*   cList.addProduct(product, "General");

        System.out.println(cList.getProduct(product, "General")); //  cList.getProduct(product, "General");

        System.out.println(cList.getCategories());*/

    }
}
