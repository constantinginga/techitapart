package persistence;

import model.Category;

import java.util.ArrayList;

public interface CategoryPersistence {
    ArrayList<Category> getAllCategoryDB();
    void addCategoryDB(String categoryName);
   // void removeCategoryDB(String categoryName);
  //  void updateCategoryDB(String categoryName);
}
