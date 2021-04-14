package model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ProductList productList;

    public Category(String name){
        this.name = name;
        productList = new ProductList();
    }

    public void buyProduct(String productName, int quantity){
        productList.buyProduct(productName, quantity);
    }

    public void addProduct(Product product){
        productList.addProduct(product);
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
        productList.getAllProducts().remove(productList.getProductById(id));
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


}
