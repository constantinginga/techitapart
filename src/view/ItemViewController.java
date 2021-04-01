package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.naming.Binding;
import javax.script.Bindings;

public class ItemViewController extends ViewController
{
  @FXML private Label productName;
  @FXML private Label price;
  @FXML private ImageView img;
  private Product selectedProduct;

  /*protected void init(Product products)
  {
    selectedProduct = products;
    productName.setText(selectedProduct.getName());
    price.setText("$" + selectedProduct.getPrice());
    Image image = new Image(getClass().getResourceAsStream(products.getImgSrc()));
    img.setImage(image);
  }*/

  @Override protected void init() throws InterruptedException
  {
    productName.textProperty().bind(super.getViewModelFactory().getItemViewModel().getProductName());
    price.textProperty().bind(super.getViewModelFactory().getItemViewModel().getPrice());
    Bindings.bindBidirectional(this.productImage.imageProperty(), GlobalModel.getInstance().getProject().getImageProperty());
  }

  @Override public void reset() throws InterruptedException
  {
    super.getViewModelFactory().getItemViewModel().reset();
  }
}
