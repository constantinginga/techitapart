package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarketUserViewController extends ViewController {
    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Button photo;
    @FXML
    private TextField searchField;

    private List<Product> Products = new ArrayList<>();

//  private List<Product> getData(){
//    List<Product> Products = new ArrayList<>();
//    Product product;
//
//    for(int i = 0; i <= 20; i++)
//    {
//      product = new Product("Phone", "Iphone 12 256gb", 199, 100);
//      product.setImgSrc("../default1.jpg");
//      Products.add(product);
//    }
//    return Products;
//  }

    @Override
    protected void init() throws InterruptedException {
        searchField.textProperty().bindBidirectional(super.getViewModelFactory().getMarketUserViewModel().searchBarProperty());
        reset();
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getMarketUserViewModel().reset();
        createGrid();
    }

    public void searchButton(ActionEvent actionEvent) {
        super.getViewModelFactory().getMarketUserViewModel().search();
        createGrid();
    }

    public void createGrid() {
        System.out.println(super.getViewModelFactory().getMarketUserViewModel().getProducts().size() + " size");
        Platform.runLater(() -> {
            grid.getChildren().clear();
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < super.getViewModelFactory().getMarketUserViewModel()
                        .getProducts().size(); i++) {
                    super.getViewModelFactory().getViewState().setCategoryID("General");
                    super.getViewModelFactory().getViewState().setProductID(
                            super.getViewModelFactory().getMarketUserViewModel().getProducts()
                                    .get(i).getId());
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ItemView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemViewController itemController = fxmlLoader.getController();
                    itemController.init(super.getViewModelFactory().getMarketUserViewModel().getProducts().get(i), this, super.getViewModelFactory().getMarketUserViewModel().getImage(super.getViewModelFactory().getMarketUserViewModel().getProducts().get(i).getImgSrc()));


                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row);
                    //set grid width
                    grid.setMinWidth(Region.USE_PREF_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        });
    }

    public void searchEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            super.getViewModelFactory().getMarketUserViewModel().search();
            createGrid();
        }
    }
}
