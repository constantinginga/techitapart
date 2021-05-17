import javafx.application.Application;
import javafx.stage.Stage;
import model.LocalModel;
import model.LocalModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

import java.rmi.NoSuchObjectException;

public class MyApplication extends Application
{

  private LocalModel model;

  @Override public void start(Stage stage) throws Exception
  {
    model = new LocalModelManager();
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler view = new ViewHandler(viewModelFactory);
    view.start(stage);
  }

  @Override
  public void stop() throws NoSuchObjectException {
    model.close();
    System.exit(0);
  }
}
