package viewController;

import dataModel.DataSource;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Startup mysql connection
        new DataSource();

        mainStage = primaryStage;
        Parent loginRoot = FXMLLoader.load(getClass().getResource("loginWindow.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(loginRoot, 300, 150));
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

    public static Stage getMainStage() {
        return mainStage;
    }
}
