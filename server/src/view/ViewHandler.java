package view;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

import java.io.IOException;

public class ViewHandler extends ViewCreator
{
  private Scene currentScene;
  private Stage primaryStage;
  private ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
    currentScene = new Scene(new Region());
  }

  public void closeView(){
    primaryStage.close();
  }

  public void start(Stage primaryStage) throws IOException
  {
    this.primaryStage = primaryStage;
    openView("MarketAdminView.fxml");
  }

  public void openView(String id) throws IOException {
    Region root = super.getViewController(id).getRoot();
    currentScene.setRoot(root);
    // CSS files
    if (id.toLowerCase().contains("market")) {
      currentScene.getStylesheets().add("stylesheets/market.css");
    } else if (id.toLowerCase().contains("addproduct")) {
      currentScene.getStylesheets().add("stylesheets/addProduct.css");
    } else if (id.toLowerCase().contains("detailedproduct")) {
      currentScene.getStylesheets().add("stylesheets/detailedProduct.css");
    }

    String title = "";
    System.out.println(id);
    if (root.getUserData() != null) {
      title += root.getUserData();
    }
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.setWidth(root.getPrefWidth());
    primaryStage.setHeight(root.getPrefHeight());
    primaryStage.show();
  }

  @Override
  protected void initViewController(ViewController controller, Region root) throws InterruptedException {
    controller.init(this,viewModelFactory,root);
  }
}
