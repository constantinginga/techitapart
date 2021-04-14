package persistence;

import model.Product;

import java.util.ArrayList;

public interface ProductPersistence {
    Product addProductToCategoryDB(Product product, String categoryName);

    void updateProductQuantityDB(String productId, int quantity);
    void updateProductPriceDB(double price, String productId);
    void updateProductDescriptionDB(String description, String productId);

    Product getProductByIdDB(String id);

    void removeProductByIdDB(String id);

    ArrayList<Product> getAllProductDB(String categoryName);

}
