package viewController;

import dataModel.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class Main extends Application {

    private static Stage mainStage;
    private static Preferences programPrefs = Preferences.userNodeForPackage(Main.class);
    public static final String LOGIN_ENABLED = "LOGIN_ENABLED";

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Startup mysql connection
        new DataSource();

        mainStage = primaryStage;
        Parent loginRoot = FXMLLoader.load(getClass().getResource("loginWindow.fxml"));
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

    public static Preferences getProgramPrefs() {
        return programPrefs;
    }
}
