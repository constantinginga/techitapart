package viewmodel;

public class ViewState
{
  String productID;
  String categoryID;
  String categoryName;
  String userID;

  public ViewState()
  {
    productID = "0";
    categoryID = "0";
    categoryName ="General";
    userID = "0";

  }

  public String getProductID()
  {
    return productID;
  }

  public void setProductID(String productID)
  {
    this.productID = productID;
  }

  public String getCategoryID()
  {
    return categoryID;
  }

  public void setCategoryID(String categoryID)
  {
    this.categoryID = categoryID;
  }

  public String getCategoryName()
  {
    return categoryName;
  }

  public void setCategoryName(String categoryName)
  {
    this.categoryName = categoryName;
  }

  public String getUserID()
  {
    return userID;
  }

  public void setUserID(String userID)
  {
    this.userID = userID;
  }
}
