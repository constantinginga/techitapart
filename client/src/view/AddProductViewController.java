package view;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddProductViewController extends ViewController {

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField priceTextfield;

    @FXML
    private TextField quantityTextfield;

    @FXML
    private ChoiceBox<String> categoryChoicebox;

    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ImageView image;
    @FXML
    private Label errorLabel;


    @Override
    protected void init() throws InterruptedException {
        nameTextfield.textProperty().bindBidirectional(
                super.getViewModelFactory().getAddProductViewModel().getName());
        priceTextfield.textProperty().bindBidirectional(
                super.getViewModelFactory().getAddProductViewModel().getPrice(), new NumberStringConverter());
        quantityTextfield.textProperty().bindBidirectional(
                super.getViewModelFactory().getAddProductViewModel().getQuantity(), new NumberStringConverter());

        categoryChoicebox.setItems(super.getViewModelFactory().getAddProductViewModel().getCategoryList());
//        categoryChoicebox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//               AddProductViewController.super.getViewModelFactory().getAddProductViewModel().updateSelectedCategory(newValue);
//            }
//        });
        categoryChoicebox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            super.getViewModelFactory().getAddProductViewModel().updateSelectedCategory(newValue);
        });

        descriptionTextArea.textProperty().bindBidirectional(
                super.getViewModelFactory().getAddProductViewModel().getDescription());
        Bindings.bindBidirectional(this.image.imageProperty(), super.getViewModelFactory().getAddProductViewModel().getImageProperty());
        errorLabel.textProperty().bind(super.getViewModelFactory().getAddProductViewModel().getErrorLabel());
        priceTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,10})?")) {
                    priceTextfield.setText(oldValue);
                }
            }
        });
        quantityTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantityTextfield.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    public void handleAddProductButton() throws IOException {
        if (super.getViewModelFactory().getAddProductViewModel().addProduct()) {
            super.getViewHandler().openView("MarketAdminView.fxml");
        }
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
