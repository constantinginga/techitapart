package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import persistence.ConnectionDB;

import java.sql.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ModelManagerTest
{

    private ModelManager model;

    private void resetTables()
    {
        try (Connection connection = ConnectionDB.getInstance().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT tablename FROM pg_tables WHERE schemaname = 'techitapart'");
            ResultSet resultSet = statement.executeQuery();
            String result = "";
            while (resultSet.next())
            {
                String tableName = resultSet.getString("tablename");
                if (!tableName.toLowerCase().equals("category"))
                    if (tableName.equals("Order") || tableName.equals("User"))
                    {
                        result += "\"" + tableName + "\"" + ",";
                    }
                    else
                    {
                        result += tableName + ",";
                    }
            }
            result = result.substring(0, result.length() - 1);
            PreparedStatement statement1 = connection
                    .prepareStatement("TRUNCATE " + result + " CASCADE");
            statement1.executeUpdate();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            throw new IllegalArgumentException("issue with connectivity");
        }
    }

    @org.junit.jupiter.api.BeforeEach void setUp()
    {
        System.out.println("--> setUp()");
        resetTables();
        model = new ModelManager();
    }

    @AfterEach void tearDown()
    {
        System.out.println("<-- tearDown()");
    }



    @org.junit.jupiter.api.Test void registerUSerSuccessfullNull()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User(null, null, null, null, null));
        });
    }

    @org.junit.jupiter.api.Test void registerUSerSuccessfullOne()
    {
        //throws exception if already created account
        try
        {
            model.registerUSer(
                    new User("Jakubs", "Platzek", "jakub.platzek@gmail.com",
                            "Kompera_SVK", "yoMamah69"));
        }
        catch (Exception e)
        {
            System.out.println("Account already exists");
        }
        User user = model.getUser("Kompera_SVK");
        assertEquals("Kompera_SVK", user.getUsername());
        assertEquals(0, model.getAllOrdersByUsername(user.getUsername()).size());
    }


    @org.junit.jupiter.api.Test void loginNull()
    {
        assertThrows(IllegalArgumentException.class, () -> {

            User user = model.login(null, null);
        });
    }


    @org.junit.jupiter.api.Test void getAllCategoryMany()
    {
        assertEquals(4, model.getAllCategory().size());
    }


    @org.junit.jupiter.api.Test void addProductZero()
    {
        assertEquals(0, model.getAllProductsInCategory("RAM").size());
        assertEquals(0, model.getAllProducts().size());
    }

    @org.junit.jupiter.api.Test void addProductOne()
    {

        model.addProduct(new Product("Marshall", "Marshall headphone"),
                "RAM");
        assertEquals(1, model.getAllProductsInCategory("RAM").size());
        assertEquals(1, model.getAllProducts().size());
    }

    @org.junit.jupiter.api.Test void addProductMany()
    {
        model.addProduct(new Product("Niva", "Niva headphone"), "RAM");
        model.addProduct(new Product("SDX", "SDX headphone"), "RAM");
        assertEquals(2, model.getAllProductsInCategory("RAM").size());
        assertEquals(2, model.getAllProducts().size());
    }


    @org.junit.jupiter.api.Test void addProductTooMany()
    {
        // error if the products already exist
        for (int i = 0; i < 997; i++)
        {
            model.addProduct(new Product("Debil" + i, "Debilak" + i), "RAM");
        }
        assertEquals(997, model.getAllProductsInCategory("RAM").size());
        assertEquals(997, model.getAllProducts().size());
    }

    

    @org.junit.jupiter.api.Test void getProductCategoryNameEqualsGeneralButProductIsNull()
    {
        assertThrows(NullPointerException.class, () -> {

            Product product = model.getProduct(null, "general");
        });
    }

    @org.junit.jupiter.api.Test void getProductCategoryNameEqualsNull()
    {
        assertThrows(NullPointerException.class, () -> {
            model.getProduct("25", null);
        });
    }


    @org.junit.jupiter.api.Test void getImage()
    {
        assertEquals(false, model.getImage("709786100.jpg") == null);
        assertEquals(false, model.getImage("7097861003.jpg") == null);
    }

    @org.junit.jupiter.api.Test void removeProduct()
    {
        model.addProduct(new Product("Product1", "Idk", 123, 23), "RAM");
        Product product = new Product("Product1", "Idk", 123, 23);
        for (Product product1 : model.getAllProducts())
        {
            if (product1.getName().equals("Product1"))
            {
                product = product1;
            }
        }
        int currentSize = model.getAllProducts().size();
        model.removeProduct(product.getId(), "RAM");
        assertEquals(currentSize - 1, model.getAllProducts().size());

    }

    @org.junit.jupiter.api.Test void updateProductQuantity()
    {
        model.addProduct(new Product("Product1", "Idk", 123, 232), "RAM");
        Product product = new Product("Product1", "Idk", 123, 232);
        for (Product product1 : model.getAllProducts())
        {
            if (product1.getName().equals("Product1"))
            {
                product = product1;
            }
        }
        //        product.setTotal_quantity(1);
        model.updateProductQuantity(product.getId(), 12, "RAM");
        assertEquals(12,
                model.getProduct(product.getId(), "RAM").getTotal_quantity());
        Product finalProduct = product;
        assertThrows(Exception.class, () -> {
            model.updateProductQuantity(finalProduct.getId(), -1, "RAM");

        });

    }

    @org.junit.jupiter.api.Test void updateProductPrice()
    {
        Product product = new Product("Product1", "Idk", 123, 232);
        model.addProduct(new Product("Product1", "Idk", 123, 232), "RAM");

        for (Product product1 : model.getAllProducts())
        {
            if (product1.getName().equals("Product1"))
            {
                product = product1;
            }
        }
        assertEquals(232.0, model.getProduct(product.getId(), "RAM").getPrice());
        model.updateProductPrice(product.getId(), 1, "RAM");
        assertEquals(1, model.getProduct(product.getId(), "RAM").getPrice());

        Product finalProduct = product;
        assertThrows(IllegalArgumentException.class, ()->{
            model.updateProductPrice(finalProduct.getId(), -1, "RAM");
        });

    }

    /// White box testing
    @org.junit.jupiter.api.Test void registerUserWhiteBox()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User(null, "bob", "kkasdasd@gmasd.com",
                    String.valueOf(LocalTime.now().getNano()), "sdfsdffFF4324FF"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User("Test", null, "kkasdasd@gmasd.com",
                    String.valueOf(LocalTime.now().getNano()), "sdfsdffFF4324FF"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User("Test", "bob", null,
                    String.valueOf(LocalTime.now().getNano()), "sdfsdffFF4324FF"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User("TEST", "bob", "kkasdasd",
                    String.valueOf(LocalTime.now().getNano()), "sdfsdffFF4324FF"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User("TEST", "bob", "kkasdasd@gmasd.com", null,
                    "sdfsdffFF4324FF"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            model.registerUSer(new User("TEST", "bob", "kkasdasd@gmasd.com",
                    String.valueOf(LocalTime.now().getNano()), null));
        });
        User user = model.registerUSer(new User("TEST", "bob", "kkasdasd@gmasd.com",
                String.valueOf(LocalTime.now().getNano()), "Demlgj12"));
        assertEquals("TEST", user.getfName());
    }

    @org.junit.jupiter.api.Test void loginWhiteBox()
    {
        //loging in sets only username and password?
        final String username = String.valueOf(LocalTime.now().getNano());
        User user = model.registerUSer(
                new User("TEST", "bob", "kkasdasd@gmasd.com", username, "Demlgj12"));
        assertThrows(IllegalArgumentException.class, () -> {
            model.login(String.valueOf(LocalTime.now().getNano()), null);
        });
        assertEquals(true, user.getUsername()
                .equals(model.login(username, "Demlgj12").getUsername()));
    }

    @org.junit.jupiter.api.Test void updateUserWhiteBox()
    {
        final String username = String.valueOf(LocalTime.now().getNano());
        model.registerUSer(
                new User("TEST", "bob", "kkasdasd@gmasd.com", username, "Dsfsdf12"));
        User user = model.getUser(username);
        assertThrows(IllegalArgumentException.class, () -> {
            user.setfName(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            user.setlName(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("asdad");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword(null);
        });
        user.setfName("fname");
        user.setlName("lname");
        user.setEmail("tester@gsdg.com");
        user.setPassword("password123");
        model.updateUser(user);
        User user1 = model.getUser(username);
        assertEquals("fname", user1.getfName());
        assertEquals("lname", user1.getlName());
        assertEquals("tester@gsdg.com", user1.getEmail());
        assertEquals("password123", user1.getPassword());

    }

    @org.junit.jupiter.api.Test void addProductWhiteBox()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("", "d");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("asd", "");
        });
    }

    @Test void addProductToCartWhiteBox()
    {
        Product product = new Product("SSD1", "T", 26, 69);
        model.addProduct(product, "RAM");
        Product product1 = model.getAllProducts().get(0);
        model.registerUSer(
                new User("Jakub", "Platzek", "jakub.platzek@gmail.com", "Jakuba",
                        "Debilak69"));
        //Technically it's fine as GUI doesn't let user choose more than is available
    /*assertThrows(IllegalArgumentException.class, () -> {
      model.addProductToCart(product1, 27, "Jakuba");
    });*/
        model.addProductToCart(product1, 26, "Jakuba");
        assertEquals(26 , model.getProduct(product1.getId(), "RAM").getTotal_quantity());
        model.buy("Jakuba");
        assertEquals(0 , model.getProduct(product1.getId(), "RAM").getTotal_quantity());
    }


//
    @Test void updateCartItemQuantityWhiteBox(){
        Product product = new Product("SSD1", "T", 26, 69);
        model.addProduct(product, "RAM");
        Product product1 = model.getAllProducts().get(0);
        model.registerUSer(
                new User("Jakub", "Platzek", "jakub.platzek@gmail.com", "Jakuba",
                        "Debilak69"));
        model.addProductToCart(product1, 26, "Jakuba");

        //update cartItem quantity with negative quantity or quantity that exceed the total quantity in stock.
        assertThrows(IllegalArgumentException.class, ()->{
            model.updateCartItemQuantity(new CartItem(product1, 26), 28, "Jakuba");
        });

        assertThrows(IllegalArgumentException.class, ()->{
            model.updateCartItemQuantity(new CartItem(product1, 26), -69, "Jakuba");
        });
    }

    @Test void removeProductFromCartWhiteBox(){
        Product product = new Product("SSD1", "T", 26, 69);
        model.addProduct(product, "RAM");
        Product product1 = model.getAllProducts().get(0);
        model.registerUSer(
                new User("Jakub", "Platzek", "jakub.platzek@gmail.com", "Jakuba",
                        "Debilak69"));
        CartItem cartItem = new CartItem(product1, 26);
        model.addProductToCart(product1, 26, "Jakuba");
        model.removeProductFromCart(cartItem, "Jakuba");
    }

    @Test void decreaseProductQuantityWhiteBox(){
        Product product = new Product("SSD1", "T", 26, 69);
        model.addProduct(product, "RAM");
        Product product1 = model.getAllProducts().get(0);
        model.decreaseProductQuantity(product1.getId(), 1);
        assertEquals(25, product1.getTotal_quantity());
        model.decreaseProductQuantity(product1.getId(), 25);
        assertEquals(0, product1.getTotal_quantity());
        assertThrows(IllegalArgumentException.class, ()->{
            model.decreaseProductQuantity(product1.getId(), 25);
        });
        model.decreaseProductQuantity(product1.getId(), -25);
        assertEquals(25, product1.getTotal_quantity());
    }

    @Test void buyWhiteBox(){
        Product product = new Product("SSD1", "T", 26, 69);
        model.addProduct(product, "RAM");
        Product product1 = model.getAllProducts().get(0);
        model.registerUSer(
                new User("Jakub", "Platzek", "jakub.platzek@gmail.com", "Jakuba",
                        "Debilak69"));
        model.addProductToCart(product1, 0, "Jakuba");
        model.buy("Jakuba");
        assertEquals(26, product1.getTotal_quantity());
        model.addProductToCart(product1, 12, "Jakuba");
        model.buy("Jakuba");
        assertEquals(14, product1.getTotal_quantity());
        model.addProductToCart(product1, 14, "Jakuba");
        model.buy("Jakuba");
        assertEquals(0, product1.getTotal_quantity());
        //Not possible to happen in GUI, but still works
        assertThrows(IllegalArgumentException.class, ()->{
            model.addProductToCart(product1, 69, "Jakuba");
            model.buy("Jakuba");
        });
    }



}
