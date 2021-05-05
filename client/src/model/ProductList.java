package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductList implements Serializable
{
    ArrayList<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    public void set(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){

        products.add(product);
    }

    public void buyProduct(String productName, int quantity){
        for (Product product: products){
            if (product.getName().equals(productName)) product.buyProduct(quantity);
        }
    }

    public void removeProduct(String id){
        for (Product product: products){
          if(product.getId().equals(id))  products.remove(product);
        }
    }

    public Product getProductById(String id){
        for (Product product: products){
            if (product.getId().equals(id) ) return product;
        }
        return null;
    }

    public Product getProductByName(String name){
        for (int i=0; i < products.size(); i++){
            if (products.get(i).getName().equals(name)) return products.get(i);
        }

        return null;
    }

    public Product getProduct(Product product){
        for (int i=0; i < products.size(); i++){
            if (products.get(i).equals(product)) return products.get(i);
        }

        return null;
    }

    public ArrayList<Product> getAllProducts(){
        return products;
    }

    @Override
    public String toString(){
        String s ="";
        for (Product product: products){
            s += product.toString() +"-----";
        }

        return s;
    }
}
