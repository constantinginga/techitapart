package viewmodel;

import javafx.application.Platform;
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
import java.rmi.RemoteException;
import java.time.LocalTime;

/**
 * The  Add product view model.
 */
public class AddProductViewModel {
    private final LocalModel model;
    private final StringProperty name, description, errorLabel;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private File filePath;
    private String fileName;
    private final ObjectProperty<javafx.scene.image.Image> imageProperty;
    private final ObservableList<String> categoryList;
    private final StringProperty selectedCategory;

    /**
     * Instantiates a new Add product view model.
     *
     * @param model     the model
     * @param viewState the view state
     */
    public AddProductViewModel(LocalModel model, ViewState viewState) {
        this.model = model;
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
    }

    /**
     * Gets name of product.
     *
     * @return the name
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * Gets description  of product.
     *
     * @return the description
     */
    public StringProperty getDescription() {
        return description;
    }

    /**
     * Gets error label.
     *
     * @return the error label
     */
    public StringProperty getErrorLabel() {
        return errorLabel;
    }

    /**
     * Gets quantity  of product.
     *
     * @return the quantity
     */
    public IntegerProperty getQuantity() {
        return quantity;
    }

    /**
     * Gets image property  of product.
     *
     * @return the image property
     */
    public ObjectProperty<Image> getImageProperty() {
        return imageProperty;
    }

    /**
     * Gets category  for product .
     *
     * @return the category list
     */
    public ObservableList<String> getCategoryList() {
        return categoryList;
    }

    /**
     * Gets price of product.
     *
     * @return the price
     */
    public DoubleProperty getPrice() {
        return price;
    }

    /**
     * Update selected category  for product.
     *
     * @param newValue the new value
     */
    public void updateSelectedCategory(String newValue) {
        selectedCategory.set(newValue);
    }

    /**
     * Reset the properties.
     */
    public void reset() {
        name.set("");
        description.set("");
        quantity.set(1);
        price.set(1);
        errorLabel.set("");
        imageProperty.set(null);

    }

    /**
     * Choose image for product.
     *
     * @param stage the stage
     */
    public void chooseImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        filePath = fileChooser.showOpenDialog(stage);
        String extension = "";

        int i = filePath.getName().lastIndexOf('.');
        if (i > 0) {
            extension = filePath.getName().substring(i + 1);
        }

        fileName = LocalTime.now().getNano() + "." + extension;
        model.uploadImage(filePath, fileName);
        Platform.runLater(() -> {
            try {
                BufferedImage bufferedImage = ImageIO.read(model.getImage(fileName));
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageProperty.set(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * Adds product.
     *
     * @return the success
     */
    public boolean addProduct() {

        try {
            Product product = new Product(name.get(), description.get(), quantity.get(), price.get());
            if (filePath != null) {
                product.setImgSrc(fileName);
            }
            model.addProduct(product, selectedCategory.get());
        } catch (Exception e) {
            errorLabel.set(e.getMessage());
            return false;
        }
        return true;
    }
}
