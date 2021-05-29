package view;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Category;


public class CategoryViewController {
    @FXML
    private AnchorPane background;
    @FXML
    private Label categoryName;
    private Category selectedCategory;
    private ViewController viewController;

    protected void init(Category categories, ViewController viewController) {
        this.viewController = viewController;
        selectedCategory = categories;
        categoryName.setText(selectedCategory.getCategoryName());
    }

    public void clicked() {
        Platform.runLater(() -> {
            try {
                if (viewController.getViewHandler().getTitle().equals("MarketAdminView.fxml")) {
                    viewController.getViewModelFactory().getViewState()
                            .setCategoryName(selectedCategory.getCategoryName());
                    viewController.getViewModelFactory().getMarketAdminViewModel().reset();
                    ((MarketAdminViewController) viewController).createGrid();
                } else if (viewController.getViewHandler().getTitle().equals("MarketUserView.fxml")) {
                    viewController.getViewModelFactory().getViewState()
                            .setCategoryName(selectedCategory.getCategoryName());
                    viewController.getViewModelFactory().getMarketUserViewModel().reset();
                    ((MarketUserViewController) viewController).createGrid();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}