package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LocalModel;

public class CategoryViewModel
{
  private LocalModel model;
  private ViewState state;
  private StringProperty categoryName;

  public CategoryViewModel(LocalModel model, ViewState viewState){
    this.model= model;
    this.state = viewState;

    categoryName = new SimpleStringProperty();
  }

  public StringProperty categoryNameProperty()
  {
    return categoryName;
  }

  public void reset(){
    try{
      categoryName.set(model.getCategory(state.getCategoryName()).getName());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
