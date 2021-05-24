package viewmodel;

/**
 * The  View state.
 */
public class ViewState {
    /**
     * The Product id.
     */
    String productID;
    /**
     * The Category id.
     */
    String categoryID;
    /**
     * The Category name.
     */
    String categoryName;
    /**
     * The User id.
     */
    String userID;
    /**
     * The Role.
     */
    String role;

    /**
     * Instantiates a new View state.
     */
    public ViewState() {
        productID = "0";
        categoryID = "0";
        categoryName = "General";
        userID = "0";
        role = "";
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Sets product id.
     *
     * @param productID the product id
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public String getCategoryID() {
        return categoryID;
    }

    /**
     * Sets category id.
     *
     * @param categoryID the category id
     */
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets category name.
     *
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
