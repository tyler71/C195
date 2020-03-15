package viewController;

// TODO Track user logins if enabled

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginWindow {
    public static final Locale SWAHILI_KENYAN = new Locale("sw", "KE");
    private final ResourceBundle rb = ResourceBundle.getBundle("lan", SWAHILI_KENYAN);
    private final ResourceBundle rbDefault = ResourceBundle.getBundle("lan", Locale.ENGLISH);
    private List<String> locales;

    private int consultantID;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameValue;
    @FXML
    private TextField passwordValue;

    @FXML
    private Button loginButton;
    @FXML
    private Button clearButton;

    public void initialize() {
            locales = Arrays.asList(
                    "sw"
            );
            String systemLanguage = Locale.getDefault().getLanguage();
//            systemLanguage = "sw";
            if(locales.contains(systemLanguage)) {
                usernameLabel.setText(rb.getString("login"));
                passwordLabel.setText(rb.getString("password"));

                loginButton.setText(rb.getString("submit"));
                clearButton.setText(rb.getString("clear"));
            } else {
                usernameLabel.setText(rbDefault.getString("login"));
                passwordLabel.setText(rbDefault.getString("password"));

                loginButton.setText(rbDefault.getString("submit"));
                clearButton.setText(rbDefault.getString("clear"));
            }
        };

    @FXML
    public void checkUpcomingAppointment() {

    }

    @FXML
    public void handleLogin() {
        String username =

    }

    @FXML
    public void handleClear() {

    }

    public int getConsultantID() {
        return consultantID;
    }
}

