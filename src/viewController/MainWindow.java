package viewController;

import dataModel.Appointment;
import dataModel.DataSourceSourceMySql;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;

public class MainWindow {
    private ObservableList<Appointment> observableAppointments = FXCollections.observableList(new ArrayList<>());

    @FXML
    private TableView<Appointment> appointmentView;

    @FXML
    private CheckBox enableLoggingCheck;
    @FXML
    private CheckBox calendarMonthViewCheck;

    @FXML
    private TextField consultantIDField;
    @FXML
    private DatePicker consultantAppointmentDate;
    @FXML
    private TextField consultantAppointmentTimeHour;
    @FXML
    private TextField consultantAppointmentTimeMinute;
    @FXML
    private TextField consultantAppointmentDuration;
    @FXML
    private RadioButton businessRadio;
    @FXML
    private RadioButton PersonalRadio;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextArea consultantAppointmentDescription;

    @FXML
    private BorderPane mainWindow;

    public void initialize() {
        Platform.runLater(() -> {
            appointmentView.setItems(observableAppointments);
            ArrayList<Appointment> consultantAppointsments = (ArrayList<Appointment>) DataSourceSourceMySql.getInstance().getConsultantAppointments(1);
//            TODO Removed hard coded consultID, retrieve from login
            observableAppointments.addAll(consultantAppointsments);
        });
    }



    @FXML
    private void addAppointment() {
    }

    @FXML
    private void updateAppointment() {
    }

    @FXML
    private void deleteAppointment() {
    }

    @FXML
    private void loadCustomerDialog() throws IOException {
        Dialog<BorderPane> dialog = new Dialog<>();
//        TODO Figure out why initOwner broken
//        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Customer Records");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("customerRecords.fxml"));
        dialog.getDialogPane().setContent(fxmlLoader.load());

        ButtonType closeButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.showAndWait();
    }

    @FXML
    private void loadReportDialog() throws IOException {
        Dialog<BorderPane> dialog = new Dialog<>();

        dialog.setTitle("Customer Records");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("reportsWindow.fxml"));
        dialog.getDialogPane().setContent(fxmlLoader.load());

        ButtonType closeButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.showAndWait();
    }
}
