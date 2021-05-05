package viewmodel;


import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.LocalModel;
import model.Product;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.time.LocalTime;

public class AddProductViewModel {
    private LocalModel model;
    private ViewState state;
    private StringProperty name, description, errorLabel;
    private IntegerProperty quantity;
    private DoubleProperty price;
    private FileChooser fileChooser;
    private File filePath;
    private String fileName;
    private ObjectProperty<javafx.scene.image.Image> imageProperty;
    private ObservableList<String> categoryList;
    private StringProperty selectedCategory;


    public AddProductViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.imageProperty = new SimpleObjectProperty<>();
        this.errorLabel = new SimpleStringProperty();
        this.selectedCategory = new SimpleStringProperty();
        this.categoryList = FXCollections.observableArrayList();
        try {
            categoryList.addAll(model.getAllCategory());
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
        categoryList.remove("General");
//        categoryList.setAll(model.getAllCategory());
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getDescription() {
        return description;
    }

    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    public IntegerProperty getQuantity() {
        return quantity;
    }

    public ObjectProperty<Image> getImageProperty() {
        return imageProperty;
    }

    public ObservableList<String> getCategoryList() {
        return categoryList;
    }

    public DoubleProperty getPrice() {
        return price;
    }

    public void updateSelectedCategory(String newValue) {
        selectedCategory.set(newValue);
    }

    public void reset() {
        name.set("");
        description.set("");
        quantity.set(1);
        price.set(1);
        errorLabel.set("");
        imageProperty.set(null);

    }

    public void chooseImage(Stage stage) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        filePath = fileChooser.showOpenDialog(stage);
//        try {
//            filePath = Files.copy(filePath.toPath(), new File("resources\\images\\" + filePath.getName()).toPath()).toFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String extension = "";

        int i = filePath.getName().lastIndexOf('.');
        if (i > 0) {
            extension = filePath.getName().substring(i + 1);
        }

        fileName = LocalTime.now().getNano() + "." + extension;
        model.uploadImage(filePath, fileName);
        Platform.runLater(() -> {
            try {
                System.out.println(fileName);
                BufferedImage bufferedImage = ImageIO.read(model.getImage(fileName));
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageProperty.set(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public boolean addProduct() {

        try {
            Product product = new Product(name.get(), description.get(), quantity.get(), price.get());
            if (filePath != null) {
                product.setImgSrc(fileName);
//            product.setImgSrc("/images/" + filePath.getName());}
            }
            model.addProduct(product, selectedCategory.get());
        } catch (Exception e) {
            errorLabel.set(e.getMessage());
            return false;
        }
        return true;
    }
}
