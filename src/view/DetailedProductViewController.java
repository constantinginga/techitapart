package view;

import javafx.fxml.FXML;

import javax.script.Bindings;
import javax.swing.text.html.ImageView;
import java.awt.*;

public class DetailedProductViewController extends ViewController
{
 @FXML private Label productName;
 @FXML private ImageView productImage;
 @FXML private Label productPrice;
 @FXML private Label productQuantity;
 @FXML private Label errorLabel;
 @FXML private TextArea descriptionTextArea;
 private int quantity;

 @Override protected void init() throws InterruptedException
 {
  quantity = 0;
  productName.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel.);
  Bindings.bindBidirectional(this.productImage.imageProperty(), GlobalModel.getInstance().getProject().getImageProperty());
  productPrice.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel.);
  Bindings
  productQuantity.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductViewModel.);
  errorLabel.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel.);
  descriptionTextArea.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductViewModel.);
 }

 @Override public void reset() throws InterruptedException
 {
  super.getViewModelFactory().getDetailedProductViewModel.reset();
 }

  public void handleRemoveQuantity()
  {
   quantity--;
   String newValue = Integer.toString(quantity);
   productQuantity.setText(newValue);
  }

  public void handleAddQuantity()
  {
    quantity++;
    String newValue = Integer.toString(quantity);
    productQuantity.setText(newValue);
  }

  public void handleOrderButton()
  {
    super.getViewModelFactory().getDetailedProductViewModel.;
  }

  public void handleBackButton()
  {
    super.getViewHandler().closeView();
  }
}
