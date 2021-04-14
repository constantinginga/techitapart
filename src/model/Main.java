package model;


import persistence.ProductDB;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {
        ProductDB productDB = new ProductDB();
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

        CategoryList cList = new CategoryList();


      //  Product product = new Product("test id product", "New phone",123,123.3);

     //   Product product1 =   productDB.addProductToCategoryDB(product, "General");


        System.out.println(productDB.getAllProductDB("General"));
       // categoryList.addProduct(product1, categoryName);

     /*   cList.addProduct(product, "General");

        System.out.println(cList.getProduct(product, "General")); //  cList.getProduct(product, "General");

        System.out.println(cList.getCategories());*/

    }
}
