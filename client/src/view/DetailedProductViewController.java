package view;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DetailedProductViewController extends ViewController {
    @FXML
    private Label productName;
    @FXML
    private ImageView productImageView;
    @FXML
    private Label productPrice;
    @FXML
    private Label productQuantity;
    @FXML
    private Label errorLabel;
    @FXML
    private TextArea descriptionTextArea;

    @Override
    protected void init() throws InterruptedException {
        Platform.runLater(() -> {
            try {

                File file = new File("resources\\images\\" + super.getViewModelFactory().getDetailedProductViewModel().getImage());
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                productImageView.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        productName.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel().getProductName());
        productPrice.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel().getProductPrice());
        productQuantity.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductViewModel().getProductQuantity());
        errorLabel.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel().getErrorLabel());
        descriptionTextArea.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductViewModel().getDescription());
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getDetailedProductViewModel().reset();
        Platform.runLater(() -> {
            try {

                File file = super.getViewModelFactory().getDetailedProductViewModel().getImage();
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                productImageView.setImage(image);
                productImageView.setPreserveRatio(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void handleRemoveQuantity() {
        super.getViewModelFactory().getDetailedProductViewModel().removeQuantity();

    }

    public void handleAddQuantity() {
        super.getViewModelFactory().getDetailedProductViewModel().addQuantity();
    }

    public void handleOrderButton() {
        super.getViewModelFactory().getDetailedProductViewModel().orderProduct();

    }

    public void handleBackButton() throws IOException {
        super.getViewHandler().openView("MarketAdminView.fxml");
    }
}
