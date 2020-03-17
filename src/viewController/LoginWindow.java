package viewController;

// TODO Track user logins if enabled

import dataModel.Appointment;
import dataModel.Consultant;
import dataModel.DataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.prefs.Preferences;

public class LoginWindow {
    public static final String LOGIN_FILE = "userlogins.txt";
    public final String LOGIN_STRING = "%s | User %s login: %s\n";
    public static final Locale SWAHILI_KENYAN = new Locale("sw", "KE");
    private final ResourceBundle rb = ResourceBundle.getBundle("lan", SWAHILI_KENYAN);
    private final ResourceBundle rbDefault = ResourceBundle.getBundle("lan", Locale.ENGLISH);
    private List<String> locales;
    private String systemLanguage;

    private static int consultantID;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameValue;
    @FXML
    private PasswordField passwordValue;

    @FXML
    private Button loginButton;
    @FXML
    private Button clearButton;

    public void initialize() {
            locales = Arrays.asList(
                    "sw"
            );
            systemLanguage = Locale.getDefault().getLanguage();
//            systemLanguage = "sw";
            if(locales.contains(systemLanguage)) {
                Main.getMainStage().setTitle(rb.getString("login_title"));
                usernameLabel.setText(rb.getString("login"));
                passwordLabel.setText(rb.getString("password"));

                loginButton.setText(rb.getString("submit"));
                clearButton.setText(rb.getString("clear"));
            } else {
                Main.getMainStage().setTitle(rbDefault.getString("login_title"));
                usernameLabel.setText(rbDefault.getString("login"));
                passwordLabel.setText(rbDefault.getString("password"));

                loginButton.setText(rbDefault.getString("submit"));
                clearButton.setText(rbDefault.getString("clear"));
            }
        };

    @FXML
    public void checkUpcomingAppointment(int consultantID) {
        ArrayList<Appointment> consultantAppointments = (ArrayList<Appointment>) DataSource.getDb().getConsultantAppointments(consultantID);
        ZonedDateTime now = ZonedDateTime.now();
        for(Appointment a : consultantAppointments) {
            if(a.getAppointmentStart().isBefore(now.plusMinutes(15)) && a.getAppointmentStart().isAfter(now)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (locales.contains(systemLanguage)) {
                    alert.setTitle(rb.getString("alert_title"));
                    alert.setHeaderText(rb.getString("upcoming_appointment"));
                } else {
                    alert.setTitle(rbDefault.getString("alert_title"));
                    alert.setHeaderText(rbDefault.getString("upcoming_appointment"));
                }
                alert.showAndWait();
                break;
            }
        }
    }

    @FXML
    public void logUserLogin(boolean validLogin) {
        Preferences prefs = Main.getProgramPrefs();
        boolean loggingEnabled = prefs.getBoolean(Main.LOGIN_ENABLED, false);
        if (loggingEnabled) {
            try {
                final boolean APPEND = true;
                FileWriter userLoginFile = new FileWriter(LOGIN_FILE, APPEND);
                if (validLogin) {
                    userLoginFile.append(String.format(LOGIN_STRING, Instant.now() + "[UTC]", usernameValue.getText(), "SUCCESS"));
                } else {
                    userLoginFile.append(String.format(LOGIN_STRING, Instant.now() + "[UTC]", usernameValue.getText(), "FAILED"));
                }
                userLoginFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleLogin() throws IOException {
        Stage mainStage = Main.getMainStage();
        String username = usernameValue.getText();
        String password = passwordValue.getText();
        boolean validLogin = DataSource.getDb().validateLogin(username, password);
        if(validLogin) {
            logUserLogin(true);
            Consultant retrievedConsultant = DataSource.getDb().searchConsultantName(username);
            consultantID = retrievedConsultant.get_id();
            checkUpcomingAppointment(consultantID);
        Parent mainRoot = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        mainStage.setScene(new Scene(mainRoot, 900, 500));
        } else {
            logUserLogin(false);
            showLoginAlert();
        }
    }

    @FXML
    private void showLoginAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(Main.getMainStage());
        if (locales.contains(systemLanguage)) {
            alert.setTitle(rb.getString("alert_title"));
            alert.setHeaderText(rb.getString("invalid_login"));
        } else {
            alert.setTitle(rbDefault.getString("alert_title"));
            alert.setHeaderText(rbDefault.getString("invalid_login"));
        }
        alert.showAndWait();
    }

    @FXML
    public void handleClear() {
        usernameValue.clear();
        passwordValue.clear();
    }

    public static int getConsultantID() {
        return consultantID;
    }
}

