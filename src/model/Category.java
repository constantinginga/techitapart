package model;

import persistence.ProductDB;
import persistence.ProductPersistence;

import java.util.ArrayList;

public class Category {
    private String name;
    private ProductList productList;
    private ProductPersistence productDB;

    public Category(String name){
        productDB = new ProductDB();
        this.name = name;
        productList = new ProductList();
        productList.set(productDB.getAllProductDB(name));
    }

    public void buyProduct(String productName, int quantity){
        productList.buyProduct(productName, quantity);
    }

    public void addProduct(Product product) {

        productList.addProduct(product);
    }

    public void addProductList(ArrayList<Product> products){
        for (Product product: products){
            productList.addProduct(product);
        }
    }

    public Product getProductByName(String name){
        return productList.getProductByName(name);
    }

    public Product getProduct(Product product){
        return productList.getProduct(product);
    }

    public Product getProductByID(String id){
        return productList.getProductById(id);
    }

    public void removeProduct(String id){
        productList.removeProduct(id);
    }

    public String getName() {
        return name;
    }

    public ProductList getProducts() {
        return productList;
    }

    public ArrayList<Product> getProductList() {
        return productList.getAllProducts();
    }

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public String toString()
    {
        return "Category{" + "name='" + name + '\'' + ", productList="
            + productList + ", productDB=" + productDB + '}';
    }
}
