import dataModel.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new DataSource();
        Parent root = FXMLLoader.load(getClass().getResource("viewController/mainWindow.fxml"));
        primaryStage.setTitle("Login");
//        primaryStage.setScene(new Scene(root, 300, 150));
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getDb().closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
