package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class AddProductViewController extends ViewController {

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField priceTextfield;

    @FXML
    private TextField quantityTextfield;

    @FXML
    private ChoiceBox<?> categoryChoicebox;

    @FXML
    private TextArea decriptionTextArea;


    @Override
    protected void init() throws InterruptedException {
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

    public void handleAddProductButton() throws IOException {
        super.getViewModelFactory().getAddProductViewModel().addProduct();
        super.getViewHandler().openView("MarketAdminView.fxml");
    }

    public void returnMain() throws IOException {
        super.getViewHandler().openView("MarketAdminView.fxml");
    }

    public void addPhotoButton() {
        Stage stage = (Stage) ((Node) this.getRoot()).getScene().getWindow();
        super.getViewModelFactory().getAddProductViewModel().chooseImage(stage);
    }

    @Override
    public void reset() throws InterruptedException {
        super.getViewModelFactory().getAddProductViewModel().reset();
    }
}
