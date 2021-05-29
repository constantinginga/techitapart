package persistence;

import java.util.ArrayList;

public interface CategoryPersistence {
    ArrayList<String> getAllCategoryDB();

    void addCategoryDB(String categoryName);
}
