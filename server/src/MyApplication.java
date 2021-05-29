import javafx.application.Application;
import javafx.stage.Stage;
import mediator.RemoteModel;
import mediator.RemoteModelManager;
import model.Model;
import model.ModelManager;

public class MyApplication extends Application {
    private RemoteModel server;

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new ModelManager();
        server = new RemoteModelManager(model);
    }
}
