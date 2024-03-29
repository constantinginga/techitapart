package view;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

import java.io.IOException;

public class ViewHandler extends ViewCreator {
    private final Scene currentScene;
    private Stage primaryStage;
    private final ViewModelFactory viewModelFactory;
    private String title;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        currentScene = new Scene(new Region());
        title = "";
    }

    public void closeView() {
        primaryStage.close();
    }

    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        openView("EntryView.fxml");
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void openView(String id) throws IOException {
        Region root = super.getViewController(id).getRoot();
        currentScene.setRoot(root);
        // CSS file
        currentScene.getStylesheets().add("stylesheets/market.css");
        title = id;
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }

    public String getTitle() {
        return title;
    }

    @Override
    protected void initViewController(ViewController controller, Region root) throws InterruptedException {
        controller.init(this, viewModelFactory, root);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
