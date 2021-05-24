package model;


import java.io.Serializable;

/**
 * Cart item .
 */
public class CartItem implements Serializable {
    private Product product;
    private int quantity;
    private int id;

    /**
     * Constructor a new Cart item.
     *
     * @param product  Product to be held by cart
     * @param quantity the quantity of product
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    /**
     * Sets id of the cartItem.
     *
     * @param id the id of cartItem
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id of the cartItem.
     *
     * @return the id of cart item
     */
    public int getId() {
        return id;
    }

    /**
     * Sets quantity of the cartItem..
     *
     * @param quantity the new quantity for product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets quantity of the cartItem.
     *
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets product for the CartItem.
     *
     * @param product the new Product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets product from CartItem.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }
}
