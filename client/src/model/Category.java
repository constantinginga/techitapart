package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Category implements Serializable {
    private ArrayList<Product> productList;
    private String categoryName;


    public Category(String newCategoryName){
        productList = new ArrayList<>();
        categoryName = newCategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<Product> getAllProduct(){
        return productList;
    }


    public void addProduct(Product newProduct){
       productList.add(newProduct);
    }

    public void removeProduct(Product newProduct){
        productList.remove(newProduct);
    }

    public void removeProductById(String id){

        productList.removeIf(product -> product.getId().equals(id));
    }

    public ArrayList<Product> getAllProductComponentsForCategory() {
        return productList;
    }


    public Product getProduct(String id){

        for (Product product: productList){
            if (product.getId().equals(id))
                return product;
        }

        return null;
    }


    public void updateProductQuantity(String id, int quantity){

        for (Product product: productList){
            if (product.getId().equals(id))
                product.setTotal_quantity(quantity);
        }
    }

    public void updateProductPrice(String id, double price){
        for (Product product: productList){
            if (product.getId().equals(id))
                product.setPrice(price);
        }
    }

    public void decreaseProductQuantity(String id, int quantity){
        for (Product product: productList){
            if (product.getId().equals(id))
                product.decreaseQuantity(quantity);
        }
    }


    @Override
    public String toString() {
        String s = " ";

        Iterator productIteraor = productList.iterator();

        while (productIteraor.hasNext()){
            Product productString = (Product) productIteraor.next();
            s += productString +">>> \n";
        }

        return "Category{" +
                            s +
                '}';
    }
}
