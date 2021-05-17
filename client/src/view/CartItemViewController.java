package view;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML private Button decreaseButton, increaseButton;

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
            System.out.println("Cart: Could not load image");
        }
    }

    @FXML private void handleRemoveItem() {
            ((ShoppingCartViewController) controller).removeCartItem(item);
            ((ShoppingCartViewController) controller).clearGrid(item.getProduct().getName());
    }

    @FXML private void decreaseQuantity() {
        int currentPQuantity = ((ShoppingCartViewController) controller).getCurrentQuantity(item.getProduct().getId());
        System.out.println("(BEFORE) SET NEW QUANTITY TO: " + currentPQuantity);
        System.out.println("ITEM.GETQUANTITY() " + item.getQuantity());
        if (item.getQuantity() > currentPQuantity) {
            int previousQuantity = item.getQuantity();
            item.setQuantity(currentPQuantity);
            currentQuantity.setText(String.valueOf(currentPQuantity));
            ((ShoppingCartViewController) controller).updateCartItemQuantity(item, -(previousQuantity - currentPQuantity));
            ((ShoppingCartViewController) controller).setCheckoutDisabled(false);
            ((ShoppingCartViewController) controller).setError("");
            System.out.println("SET NEW QUANTITY TO: " + currentPQuantity);
            return;
        }
        try {
                ((ShoppingCartViewController) controller).updateCartItemQuantity(item, -1);
                item.setQuantity(item.getQuantity() - 1);
                currentQuantity.setText(String.valueOf(item.getQuantity()));
                if (increaseButton.disableProperty().get()) increaseButton.disableProperty().setValue(false);
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("low")) {
                    decreaseButton.disableProperty().setValue(true);
                }
            }

    }

    @FXML private void increaseQuantity() {
            try {
                int currentPQuantity = ((ShoppingCartViewController) controller).getCurrentQuantity(item.getProduct().getId());
                if (item.getQuantity() + 1 == currentPQuantity) increaseButton.disableProperty().setValue(true);
                ((ShoppingCartViewController) controller).updateCartItemQuantity(item, 1);
                System.out.println("item.getQuantity() increase: " + item.getQuantity());
                item.setQuantity(item.getQuantity() + 1);
                currentQuantity.setText(String.valueOf(item.getQuantity()));
                if (decreaseButton.disableProperty().get()) decreaseButton.disableProperty().setValue(false);
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("high")) {
                    increaseButton.disableProperty().setValue(true);
                }
            }
    }


    public void reset() throws InterruptedException {

    }
}
