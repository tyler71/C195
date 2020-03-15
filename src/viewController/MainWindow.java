package viewController;

import dataModel.Appointment;
import dataModel.DataSource;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.time.*;
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
    public RadioButton radioUpdateAppointment;
    @FXML
    public ToggleGroup addUpdateToggle;

    @FXML
    private TextField consultantIDField;
    @FXML
    private ToggleGroup appointmentTypeToggle;
    @FXML
    private TextField customerIDField;
    @FXML
    private DatePicker consultantAppointmentDate;
    @FXML
    private TextField consultantAppointmentTimeHour;
    @FXML
    private TextField consultantAppointmentTimeMinute;
    @FXML
    private TextField consultantAppointmentDuration;
    @FXML
    private TextArea consultantAppointmentDescription;


    public void initialize() {
        Platform.runLater(() -> {
            try {
                int consultant_id = LoginWindow.getConsultantID();
                ArrayList<Appointment> consultantAppointsments = (ArrayList<Appointment>) DataSource.getDb().getConsultantAppointments(consultant_id);
                observableAppointments.addAll(consultantAppointsments);
                appointmentView.setItems(observableAppointments);
                appointmentView.getSelectionModel().selectFirst();

            } catch (NullPointerException e) {
                System.out.println("No Appointments available");
            }

            enableLoggingCheck.setSelected(Main.getProgramPrefs().getBoolean(Main.LOGIN_ENABLED, false));
            enableLoggingCheck.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                enableLoggingCheck.setSelected(t1);
                if(t1) {
                    Main.getProgramPrefs().putBoolean(Main.LOGIN_ENABLED, true);
                } else {
                    Main.getProgramPrefs().putBoolean(Main.LOGIN_ENABLED, false);
                }
            });
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
        loadDialog("Customer Records", "customerRecords.fxml");
    }

    @FXML
    private void loadReportDialog() throws IOException {
        loadDialog("Reports", "reportsWindow.fxml");
    }

    private void loadDialog(String title, String fmxlFile) throws IOException {
        Dialog<BorderPane> dialog = new Dialog<>();

        dialog.setTitle(title);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fmxlFile));
        dialog.getDialogPane().setContent(fxmlLoader.load());

        ButtonType closeButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.showAndWait();
    }

    @FXML
    private void handleSave() {
//        TODO Logic for Datetime
//        TODO Logic for appointment type
        try {
            int consultantID = Integer.parseInt(consultantIDField.getText());
            String appointmentType = ((RadioButton) addUpdateToggle.getSelectedToggle()).getText();
            int customerID = Integer.parseInt(customerIDField.getText());
            int appointmentYear = consultantAppointmentDate.getValue().getYear() ;
            Month appointmentMonth = consultantAppointmentDate.getValue().getMonth();
            int appointmentDay = consultantAppointmentDate.getValue().getDayOfMonth();
            int appointmentHour = Integer.parseInt(consultantAppointmentTimeHour.getText());
            int appointmentMinute = Integer.parseInt(consultantAppointmentTimeMinute.getText());
            int appointmentDuration = Integer.parseInt(consultantAppointmentDuration.getText());
            String appointmentDescription = consultantAppointmentDescription.getText();
            String lastUpdateBy = DataSource.getDb().getConsultant(LoginWindow.getConsultantID()).getName();

            LocalDateTime parsedLocalDateTime = LocalDateTime.of(appointmentYear, appointmentMonth,
                    appointmentDay, appointmentHour, appointmentMinute);

            ZonedDateTime appointmentStart = parsedLocalDateTime.atZone(ZoneOffset.systemDefault());
            ZonedDateTime appointmentStop = parsedLocalDateTime
                    .plusMinutes(appointmentDuration)
                    .atZone(ZoneOffset.systemDefault());

            int appointmentID;

            RadioButton selectedAddUpdateRadioButton = (RadioButton) addUpdateToggle.getSelectedToggle();
            String selectedRadioAddUpdate = selectedAddUpdateRadioButton.getId();
            if(selectedRadioAddUpdate.equals("radioAddAppointment")) {
                Appointment tempAppointment = new Appointment(customerID, consultantID, "null title",
                        appointmentDescription, appointmentType, appointmentStart, appointmentStop, lastUpdateBy, lastUpdateBy);
                appointmentID  = DataSource.getDb().addAppointment(appointmentStart, appointmentStop,
                            appointmentType, customerID, consultantID);
                observableAppointments.add();
            } else if (selectedRadioAddUpdate.equals("radioUpdateAppointment")) {
                appointmentID = appointmentView.getSelectionModel().getSelectedItem().get_id();
                DataSource.getDb().updateAppointment(appointmentID, appointmentStart, appointmentStop,
                        appointmentType, customerID, consultantID);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }

    }

    @FXML
    private void handleSelectCustomerRecord() {
        if(appointmentView.getSelectionModel().getSelectedItem() != null) {
            radioUpdateAppointment.fire();
            populateUpdateFields();
        }
    }

    @FXML
    private void populateUpdateFields() {
        try{
            Appointment selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
            int appointmentID = selectedAppointment.get_id();
            Appointment retrievedAppointment = DataSource.getDb().getAppointment(appointmentID);
        } catch (NullPointerException e) {
            System.out.println("No Customer Records available");
        }
    }

}
