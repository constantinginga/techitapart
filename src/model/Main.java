package model;


import persistence.CartDB;
import persistence.ProductDB;

import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException {
        //ProductDB productDB = new ProductDB();

//        CategoryDB categoryDB = new CategoryDB();
//        System.out.println(categoryDB.getAllCategoryDB());
//

        //categoryDB.addCategoryDB("Laptop");
   //     productDB.addProductToCategoryDB(new Product("123123123", "adfsafsdafsd", 100, 650), "Laptop");
     //   System.out.println(productDB.getAllProductDB("Laptop"));
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



    //    Product product = new Product("test id product", "New phone",123,123.3);
   //     Product product1 = new Product("product2 tets", "New phone",123,123.3);
    //    Product product2 = new Product("test id product3", "New phone",123,123.3);

        Model model = new ModelManager();

        Product product = new Product( "1",  "123123123",  "adfsafsdafsd",   100, 650.0);

        System.out.println(model.login("newUser12345667", "VeryAngry_12345"));
      //  model.registerUSer("Jakub", "Jakub", "jakub@gmail.com","newUser12345667", "VeryAngry_12345", Role.Consumer);
        model.addProductToCart(product, 2);
        CartDB cartDB = new CartDB();
        System.out.println( cartDB.getAllProductsInCart("newUser12345667").toString());

     //   ProductDB productDB = new ProductDB();

  //      System.out.println(productDB.getAllProductDB("Laptop"));
     // model.addCategory("General");

   //     model.addProduct(product,"General");
 //       model.addProduct(product1,"General");
  //      model.addProduct(product2,"General");

       // System.out.println(model.removeProduct(););


      //  System.out.println(productDB.getAllProductDB("General"));
       // categoryList.addProduct(product1, categoryName);

     /*   cList.addProduct(product, "General");

        System.out.println(cList.getProduct(product, "General")); //  cList.getProduct(product, "General");

        System.out.println(cList.getCategories());*/

    }
}
