//package viewmodel;
//
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import model.LocalModel;
//
//public class CartItemViewModel {
//    private LocalModel model;
//    private ViewState state;
//    private final StringProperty itemName;
//    private final IntegerProperty price;
//    private final IntegerProperty currentQuantity;
//
//    public CartItemViewModel(LocalModel model, ViewState state) {
//        this.model = model;
//        this.state = state;
//        this.itemName = new SimpleStringProperty();
//        this.price = new SimpleIntegerProperty();
//        this.currentQuantity = new SimpleIntegerProperty();
//    }
//
//    public void reset() {
//
//    }
//
//    public void decreaseQuantity() {
//
//    }
//
//    public void increaseQuantity() {
//
//    }
//
//    public StringProperty getItemName() {
//        return itemName;
//    }
//
//    public IntegerProperty getPrice() {
//        return price;
//    }
//
//    public IntegerProperty getCurrentQuantity() {
//        return currentQuantity;
//    }
//}
