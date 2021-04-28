package model;

import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

class ModelManagerTest {

    private ModelManager mm;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("--> setUp()");
        mm = new ModelManager();
    }

    @AfterEach void tearDown(){
        System.out.println("<-- tearDown()");
    }
    @org.junit.jupiter.api.Test
    void registerUSerSuccessfullNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            mm.registerUSer(null, null, null, null, null, null);
        });
    }

    @org.junit.jupiter.api.Test
    void registerUSerSuccessfullOne() {
        UserProfile user = mm.registerUSer("Jakub", "Platzek", "jakub.platzek@gmail.com", "Kompera_SVK", "yoMamah69", Role.Consumer);
        assertEquals("Kompera_SVK", user.getUsername());
        assertEquals(0, user.getOrderList().getAllOrders().size());
    }

    @org.junit.jupiter.api.Test
    void loginNull() {
        Boolean user = mm.login(null, null);
        assertEquals(false, user.booleanValue());
    }

    @org.junit.jupiter.api.Test
    void loginSuccesfull() {
        Boolean user = mm.login("Kompera_SVK", "yoMamah69");
        assertEquals(true, user.booleanValue());
    }

    @org.junit.jupiter.api.Test
    void getAllCategoryMany() {
        assertEquals(4 , mm.getAllCategory().size());
    }

    @org.junit.jupiter.api.Test
    void addProductZero() {
        assertEquals(0, mm.getAllProductsInCategory("Headphones").size());
        assertEquals(0, mm.getAllProducts().size());
    }

    @org.junit.jupiter.api.Test
    void addProductOne() {
        mm.addProduct(new Product("Marshall", "Marshall headphone"), "Headphones");
        assertEquals(1, mm.getAllProductsInCategory("Headphones").size());
        assertEquals(1, mm.getAllProducts().size());
    }

    @org.junit.jupiter.api.Test
    void addProductMany() {
        mm.addProduct(new Product("Niva", "Niva headphone"), "Headphones");
        mm.addProduct(new Product("SDX", "SDX headphone"), "Headphones");
        assertEquals(3, mm.getAllProductsInCategory("Headphones").size());
        assertEquals(3, mm.getAllProducts().size());
    }

    @org.junit.jupiter.api.Test
    void addProductTooMany() {
        for(int i = 0; i < 997; i++){
            mm.addProduct(new Product("Debil" +i, "Debilak" +i), "Headphones");
        }
        assertEquals(1000, mm.getAllProductsInCategory("Headphones").size());
        assertEquals(1000, mm.getAllProducts().size());
    }

    @org.junit.jupiter.api.Test
    void getProductNull() {
        Product product = mm.getProduct(null, "Headphones");
        assertEquals(null, product);
    }

    @org.junit.jupiter.api.Test
    void getProductCategoryNameEqualsGeneralButProductIsNull() {
        Product product = mm.getProduct(null, "general");
        assertEquals(null, product);
    }

    @org.junit.jupiter.api.Test
    void getProductCategoryNameEqualsGeneral() {
        Product product = mm.getProduct("25", "general");
        assertEquals("Debil21", product.getName());
    }

    @org.junit.jupiter.api.Test
    void getProductCategoryNameEqualsNull() {
        assertThrows(NullPointerException.class, () -> {
            mm.getProduct("25", null);
        });
    }

    @org.junit.jupiter.api.Test
    void getProductOne() {
        Product product = mm.getProduct("25", "Headphones");
        assertEquals("Debil21", product.getName());
    }


    @org.junit.jupiter.api.Test
    void getAllProducts() {
        mm.getAllProducts();
        assertEquals(1000, mm.getAllProducts().size());
    }

}