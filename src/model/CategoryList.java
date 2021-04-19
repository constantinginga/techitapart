package model;


import persistence.CategoryDB;
import persistence.CategoryPersistence;

import java.util.ArrayList;



public class CategoryList {

    private ArrayList<Category> categories;

    public CategoryList(){
        this.categories = new ArrayList<>();

    }


    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
    }

    public Category getCategory(String name){
        for (Category category: categories){
            if (category.getName().equals(name)) return category;
        }

        return null;
    }



    public Category getCategory(int index){
        return categories.get(index);
    }

    public void addProductList(ArrayList<Product> products, String categoryName)
    {
        for (Category category: categories){
            if (categoryName.equals(category.getName())) category.addProductList(products);
        }
    }

    public void addProduct(Product product, String categoryName){


        for (Category category1: categories){
            if (categoryName.equals(category1.getName())){
                category1.addProduct(product);
            }
        }


    }

    public void buyProduct(String productName, int quantity, String categoryName){
        for (Category category1: categories){
            if (categoryName.equals(category1.getName())){
                category1.buyProduct(productName, quantity);
            }
        }
    }

    public Product getProductByName(String name, String categoryName){
        for (Category  category: categories){
            if (category.getName().equals(categoryName)){
                return category.getProductByName(name);
            }
        }

        return null;
    }

    public Product getProduct(Product product, String categoryName){
        for (Category  category: categories){
            if (category.getName().equals(categoryName)){
                return category.getProduct(product);
            }
        }

        return null;
    }

    public Product getProductById(String id, String categoryName){
        for (Category  category: categories){
            if (category.getName().equals(categoryName)){
                return category.getProductByID(id);
            }
        }

        return null;
    }

    public void removeProduct(String id, String categoryName){

        for (Category  category: categories){
            if (category.getName().equals(categoryName)){
                category.removeProduct(id);
            }
        }
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }
}
