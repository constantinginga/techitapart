package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Category object that holds  list of products.
 */
public class Category implements Serializable {
    private final ArrayList<Product> productList;
    private final String categoryName;

    /**
     * Constructor a new Category.
     *
     * @param newCategoryName the new category name
     */
    public Category(String newCategoryName) {
        productList = new ArrayList<>();
        categoryName = newCategoryName;
    }

    /**
     * Returns the name of category.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * gets  all product from this catagory.
     *
     * @return the array list
     */
    public ArrayList<Product> getAllProduct() {
        return productList;
    }

    /**
     * Adds product to category.
     *
     * @param newProduct the new product to be added
     */
    public void addProduct(Product newProduct) {
        productList.add(newProduct);
    }

    /**
     * Removes  product from this category.
     *
     * @param product the product to remove
     */
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    /**
     * Remove product by id from catagory
     *
     * @param id the id of prodcut
     */
    public void removeProductById(String id) {

        productList.removeIf(product -> product.getId().equals(id));
    }

    /**
     * Get product product by id.
     *
     * @param id the id of product
     * @return the product
     */
    public Product getProduct(String id) {

        for (Product product : productList) {
            if (product.getId().equals(id))
                return product;
        }

        return null;
    }

    /**
     * Update products quantity.
     *
     * @param id       the id of product
     * @param quantity the new quantity of product
     */
    public void updateProductQuantity(String id, int quantity) {

        for (Product product : productList) {
            if (product.getId().equals(id))
                product.setTotal_quantity(quantity);
        }
    }

    /**
     * Update products price.
     *
     * @param id    the id of product
     * @param price the new price
     */
    public void updateProductPrice(String id, double price) {
        for (Product product : productList) {
            if (product.getId().equals(id))
                product.setPrice(price);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(" ");

        for (Product productString : productList) {
            s.append(productString).append(">>> \n");
        }

        return "Category{" +
                s +
                '}';
    }
}
