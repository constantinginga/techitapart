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
import javax.naming.Binding;
import javax.script.Bindings;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ItemViewController
{
  @FXML private Label productName;
  @FXML private Label price;
  @FXML private ImageView img;
  private Product selectedProduct;
  private ViewController viewController;

  protected void init(Product products,
      ViewController viewController)
  {
    this.viewController = viewController;
    selectedProduct = products;
    productName.setText(selectedProduct.getName());
    price.setText("$" + selectedProduct.getPrice());
    Platform.runLater(() -> {
    try {
      img.setImage(new Image(products.getImgSrc()));
    } catch (Exception e){
      e.printStackTrace();
    }});
    }
    //    Image image = new Image(
    //        getClass().getResourceAsStream(products.getImgSrc()));
    //    img.setImage(image);

//  @Override protected void init() throws InterruptedException
//  {
//    //    productName.setsuper.getViewModelFactory().getItemViewModel().getProductName());
//    //    price.textProperty().bind(super.getViewModelFactory().getItemViewModel().getPrice());
//    //    Bindings.bindBidirectional(this.img.imageProperty(), GlobalModel.getInstance().getProject().getImageProperty());
//  }

   public void reset() throws InterruptedException
  {

  }

  public void clicked(MouseEvent mouseEvent) throws IOException
  {
    Platform.runLater(() -> {
      try
      {
        viewController.getViewModelFactory().getViewState().setProductID(selectedProduct.getId());
        viewController.getViewHandler()
            .openView("DetailedProductView.fxml");
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    });
  }
}
