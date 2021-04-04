package view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;

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
    productName.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel().getProductName());
    //  Bindings.bindBidirectional(this.productImage.imageProperty(), GlobalModel.getInstance().getProject().getImageProperty());
    productPrice.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel().getProductPrice());
    //  Bindings
    productQuantity.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductViewModel().getProductQuantity());
    errorLabel.textProperty().bind(super.getViewModelFactory().getDetailedProductViewModel().getErrorLabel());
    descriptionTextArea.textProperty().bindBidirectional(super.getViewModelFactory().getDetailedProductViewModel().getDescription());
  }

  @Override public void reset() throws InterruptedException
  {
 super.getViewModelFactory().getDetailedProductViewModel().reset();
  }

  public void handleRemoveQuantity()
  {
    super.getViewModelFactory().getDetailedProductViewModel().removeQuantity();

  }

  public void handleAddQuantity()
  {
    super.getViewModelFactory().getDetailedProductViewModel().addQuantity();
  }

  public void handleOrderButton()
  {
    super.getViewModelFactory().getDetailedProductViewModel().orderProduct();

  }

  public void handleBackButton() throws IOException
  {
    super.getViewHandler().openView("MarketAdminView.fxml");
  }
}
