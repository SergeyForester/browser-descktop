import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(final String[] args) throws Exception {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Browser");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}