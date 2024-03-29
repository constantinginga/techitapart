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

public class DetailedProductAdminViewController extends ViewController {
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
    @FXML
    private Label totalQuantity;

    @Override
    protected void init() throws InterruptedException {
        Platform.runLater(() -> {
            try {

                File file = super.getViewModelFactory().getDetailedProductViewModel().getImage();
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                productImageView.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        productName.textProperty().bind(super.getViewModelFactory().getDetailedProductAdminViewModel().getProductName());
        productPrice.textProperty().bind(super.getViewModelFactory().getDetailedProductAdminViewModel().getProductPrice());
        productQuantity.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductAdminViewModel().getProductQuantity());
        errorLabel.textProperty().bind(super.getViewModelFactory().getDetailedProductAdminViewModel().getErrorLabel());
        descriptionTextArea.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductAdminViewModel().getDescription());
        totalQuantity.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductAdminViewModel().getTotalQuantity());
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getDetailedProductAdminViewModel().reset();
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
        super.getViewModelFactory().getDetailedProductAdminViewModel().removeQuantity();
    }

    public void handleAddQuantity() {
        super.getViewModelFactory().getDetailedProductAdminViewModel().addQuantity();
    }

    @FXML
    public void handleDeleteButton() throws IOException {
        super.getViewModelFactory().getDetailedProductAdminViewModel().removeProduct();
        super.getViewHandler().openView("MarketAdminView.fxml");
    }

    public void handleBackButton() throws IOException {
        super.getViewHandler().openView("MarketAdminView.fxml");
    }
}
