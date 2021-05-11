package view;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CartItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CartItemViewController {
    private CartItem item;
    private ViewController controller;
    @FXML private ImageView img;
    @FXML private Label itemName, currentQuantity, price;

    protected void init(CartItem item, ViewController controller, File fileImage) {
        this.controller = controller;
        this.item = item;
        this.itemName.setText(item.getProduct().getName());
        this.currentQuantity.setText(String.valueOf(item.getQuantity()));
        this.price.setText(item.getProduct().getPrice() + " kr.");
        try {
            BufferedImage bufferedImage = ImageIO.read(fileImage);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            img.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cart: Could not load image");
        }
    }

    @FXML private void handleRemoveItem() {
        ((ShoppingCartViewController) controller).clearGrid(item.getProduct().getName());
    }

    @FXML private void decreaseQuantity() {

    }

    @FXML private void increaseQuantity() {

    }


    public void reset() throws InterruptedException {

    }
}
