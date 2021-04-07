package viewmodel;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import model.Product;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class AddProductViewModel {
    private Model model;
    private ViewState state;
    private StringProperty name, description, quantity, price;
    private FileChooser fileChooser;
    private File filePath;


    public AddProductViewModel(Model model, ViewState viewState) {
        this.model = model;
        this.state = viewState;
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.quantity = new SimpleStringProperty();
        this.price = new SimpleStringProperty();
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getDescription() {
        return description;
    }

    public StringProperty getQuantity() {
        return quantity;
    }

    public StringProperty getPrice() {
        return price;
    }

    public void reset() {
        name.set("");
        description.set("");
        quantity.set("");
        price.set("");

    }

    public void chooseImage(Stage stage) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        filePath = fileChooser.showOpenDialog(stage);
        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            filePath = Files.copy(filePath.toPath(), new File("src\\images\\" + filePath.getName()).toPath()).toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct() {
        Product product = new Product(name.get(), description.get(), Integer.parseInt(quantity.get()), Double.parseDouble(price.get()));
        if (filePath != null) {
            product.setImgSrc("/images/" + filePath.getName());
        }
        model.addProduct(product, "General");

    }
}
