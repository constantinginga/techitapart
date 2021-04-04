package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Product;

import javax.naming.Binding;
import javax.script.Bindings;
import java.io.IOException;

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
    //    Image image = new Image(
    //        getClass().getResourceAsStream(products.getImgSrc()));
    //    img.setImage(image);
  }

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
