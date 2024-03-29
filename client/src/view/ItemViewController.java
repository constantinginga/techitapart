package view;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Product;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ItemViewController {
    @FXML
    private Label productName;
    @FXML
    private Label price;
    @FXML
    private ImageView img;
    private Product selectedProduct;
    private ViewController viewController;

    protected void init(Product products, ViewController viewController,
                        File fileImage) {
        this.viewController = viewController;
        selectedProduct = products;
        productName.setText(selectedProduct.getName());
        price.setText(selectedProduct.getPrice() + " kr.");
        System.out.println(fileImage.getPath());
        Platform.runLater(() -> {
            try {

                File file = fileImage;
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                img.setImage(image);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void reset() throws InterruptedException {

    }

    public void clicked() {
        Platform.runLater(() -> {
            try {
                if (viewController.getViewHandler().getTitle().equals("MarketAdminView.fxml")) {
                    viewController.getViewModelFactory().getViewState()
                            .setProductID(selectedProduct.getId());
                    viewController.getViewHandler().openView("DetailedProductAdminView.fxml");

                } else if (viewController.getViewHandler().getTitle().equals("MarketUserView.fxml")) {
                    viewController.getViewModelFactory().getViewState()
                            .setProductID(selectedProduct.getId());
                    viewController.getViewHandler().openView("DetailedProductView.fxml");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}