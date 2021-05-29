package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewCreator {
    private final Map<String, ViewController> map;

    public ViewCreator() {
        this.map = new HashMap<>();
    }

    public ViewController getViewController(String id) throws IOException {
        ViewController viewController = map.get(id);

        if (viewController == null) {
            viewController = loadFromFXML(id);
            map.put(id, viewController);
        }
        try {
            viewController.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewController;
    }

    protected abstract void initViewController(ViewController controller, Region root) throws InterruptedException;

    private ViewController loadFromFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        ViewController viewController = loader.getController();
        try {
            initViewController(viewController, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewController;
    }
}
