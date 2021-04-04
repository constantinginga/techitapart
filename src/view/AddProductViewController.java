package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddProductViewController extends ViewController
{

  @FXML private TextField nameTextfield;

  @FXML private TextField priceTextfield;

  @FXML private TextField quantityTextfield;

  @FXML private ChoiceBox<?> categoryChoicebox;

  @FXML private TextArea decriptionTextArea;

  @FXML private Button addPhotoButton;

  @FXML private Button addProductButton;

  @FXML private Button mainButton;

  @Override protected void init() throws InterruptedException
  {
    nameTextfield.textProperty().bindBidirectional(
        super.getViewModelFactory().getAddProductViewModel().getName());
    priceTextfield.textProperty().bindBidirectional(
        super.getViewModelFactory().getAddProductViewModel().getPrice());
    quantityTextfield.textProperty().bindBidirectional(
        super.getViewModelFactory().getAddProductViewModel().getQuantity());
    //categoryChoicebox.
    decriptionTextArea.textProperty().bindBidirectional(
        super.getViewModelFactory().getAddProductViewModel().getDescription());
  }

  @FXML public void handleAddProductButton() throws IOException
  {
    super.getViewModelFactory().getAddProductViewModel().addProduct();
    super.getViewHandler().openView("MarketAdminView.fxml");
  }

  @FXML public void returnMain()
  {

  }

  @FXML public void addPhoto()
  {
    super.getViewModelFactory().getAddProductViewModel().reset();
  }

  @Override public void reset() throws InterruptedException
  {
    super.getViewModelFactory().getAddProductViewModel().reset();
  }
}
