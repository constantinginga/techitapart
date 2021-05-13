package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.CartItem;

import java.io.IOException;
import java.rmi.RemoteException;

public class ShoppingCartViewController extends ViewController {

    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;
    @FXML private Label quantityOfItemsCart, quantityOfItemsOrder, totalPrice;

    @Override
    protected void init() throws InterruptedException {
        quantityOfItemsCart.textProperty().bind(super.getViewModelFactory().getShoppingCartViewModel().getTotalItems());
        quantityOfItemsOrder.textProperty().bind(super.getViewModelFactory().getShoppingCartViewModel().getTotalItems());
        reset();
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getShoppingCartViewModel().reset();
        createGrid();
    }

    @FXML
    private void handleCheckoutButton() {

    }

    @FXML
    private void handleBackButton() throws IOException {
        super.getViewHandler().openView("MarketUserView.fxml");
    }

    public void clearGrid(String name) {
        grid.getChildren().remove(super.getViewModelFactory().getShoppingCartViewModel().getItemIndex(name));

    }

    private void createGrid() {
        Platform.runLater(() -> {
            grid.getChildren().clear();
            grid.getChildren().removeAll();
            int rows = 1, columns = 0;
            try {
                for (int i = 0; i < super.getViewModelFactory().getShoppingCartViewModel().getItems().size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("CartItemView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    CartItemViewController cartItemViewController = fxmlLoader.getController();
                    cartItemViewController.init(super.getViewModelFactory().getShoppingCartViewModel().getItems().get(i), this, super.getViewModelFactory().getShoppingCartViewModel().getImage(i));
                    if (columns == 1) {
                        columns = 0;
                        rows++;
                    }

                    grid.add(anchorPane, columns++, rows);
                    //set grid width
                    grid.setMinWidth(Region.USE_PREF_SIZE);
                    grid.setPrefWidth(Region.USE_PREF_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
