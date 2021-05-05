package persistence;

import model.Category;

import java.util.ArrayList;

public interface CategoryPersistence {
    ArrayList<String> getAllCategoryDB();
    void addCategoryDB(String categoryName);
   // void removeCategoryDB(String categoryName);
  //  void updateCategoryDB(String categoryName);
}
