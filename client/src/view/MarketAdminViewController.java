package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import utility.observer.event.ObserverEvent;
import utility.observer.listener.LocalListener;
import java.io.IOException;

public class MarketAdminViewController extends ViewController
    implements LocalListener<String, Integer>
{
  @FXML private ScrollPane scroll;
  @FXML private ScrollPane categoryScroll;
  @FXML private GridPane grid;
  @FXML private GridPane categoryGrid;

  @FXML private Button photo;
  @FXML private TextField searchField;

  @Override protected void init() throws InterruptedException
  {
    searchField.textProperty().bindBidirectional(
        super.getViewModelFactory().getMarketAdminViewModel()
            .searchBarProperty());
    super.getViewModelFactory().getMarketAdminViewModel().reset();
    reset();
  }

  @Override public void reset() throws InterruptedException
  {
    super.getViewModelFactory().getMarketAdminViewModel().reset();
    createGrid();
    createGridForCategory();
  }

  public void searchButton(ActionEvent actionEvent)
  {
    super.getViewModelFactory().getMarketAdminViewModel().search();
    createGrid();
  }

  public void addProduct(ActionEvent actionEvent) throws IOException
  {
    super.getViewHandler().openView("AddProductView.fxml");
  }

  @Override public void propertyChange(ObserverEvent<String, Integer> event)
  {
    Platform.runLater(() -> {
      try
      {
        reset();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    });
  }

  public void createGrid()
  {
    Platform.runLater(() -> {
      grid.getChildren().clear();
      int column = 0;
      int row = 1;
      try
      {
        for (int i = 0;
             i < super.getViewModelFactory().getMarketAdminViewModel()
                 .getProducts().size(); i++)
        {
          super.getViewModelFactory().getViewState().setProductID(
              super.getViewModelFactory().getMarketAdminViewModel()
                  .getProducts().get(i).getId());
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("ItemView.fxml"));
          AnchorPane anchorPane = fxmlLoader.load();

          ItemViewController itemController = fxmlLoader.getController();
          itemController.init(
              super.getViewModelFactory().getMarketAdminViewModel()
                  .getProducts().get(i), this,
              super.getViewModelFactory().getMarketAdminViewModel().getImage(
                  super.getViewModelFactory().getMarketAdminViewModel()
                      .getProducts().get(i).getImgSrc()));

          if (column == 3)
          {
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
      }
      catch (IOException e)
      {
        e.printStackTrace();

      }
    });
  }

  public void createGridForCategory()
  {
    Platform.runLater(() -> {
      categoryGrid.getChildren().clear();
      int column = 0;
      int row = 1;
      try
      {
        for (int i = 0;
             i < super.getViewModelFactory().getMarketAdminViewModel()
                 .getCategories().size(); i++)
        {
          super.getViewModelFactory().getViewState().setCategoryID("General");
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("CategoryView.fxml"));
          AnchorPane anchorPane = fxmlLoader.load();

          CategoryViewController categoryController = fxmlLoader
              .getController();
          categoryController.init(
              super.getViewModelFactory().getMarketAdminViewModel()
                  .getCategories().get(i), this);

          if (column == 1)
          {
            column = 0;
            row++;
          }

          categoryGrid.add(anchorPane, column++, row);
          //set grid width
          categoryGrid.setMinWidth(Region.USE_PREF_SIZE);
          categoryGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
          categoryGrid.setMaxWidth(Region.USE_PREF_SIZE);

          //set grid height
          categoryGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
          categoryGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
          categoryGrid.setMaxHeight(Region.USE_PREF_SIZE);

          GridPane.setMargin(anchorPane, new Insets(10));
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();

      }
    });
  }

  public void searchEnter(KeyEvent keyEvent)
  {
    if (keyEvent.getCode().equals(KeyCode.ENTER))
    {
      super.getViewModelFactory().getMarketAdminViewModel().search();
      createGrid();
    }
  }
}
