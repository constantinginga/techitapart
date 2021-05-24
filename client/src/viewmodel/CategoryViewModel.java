package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;


/**
 * The  Category view model.
 */
public class CategoryViewModel
{
    private LocalModel model;
    private ViewState state;
    private StringProperty categoryName;

    /**
     * Instantiates a new Category view model.
     *
     * @param model     the model
     * @param viewState the view state
     */
    public CategoryViewModel(LocalModel model, ViewState viewState){
        this.model= model;
        this.state = viewState;

        categoryName = new SimpleStringProperty();
    }

    /**
     * Getter for category name property.
     *
     * @return the string property
     */
    public StringProperty categoryNameProperty()
    {
        return categoryName;
    }

    /**
     * Resets the category view.
     */
    public void reset(){
        try{
            categoryName.set(model.getCategory(state.getCategoryName()).getCategoryName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}