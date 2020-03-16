package viewController;

import dataModel.Appointment;
import dataModel.DataSource;
import dataModel.IDataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

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
    private RadioButton personalRadio;
    @FXML
    private RadioButton businessRadio;
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
//        TODO - Completed
//        Lambda used as only one callable needs to be overriden. runLater prevents this code
//        from being run until the window (MainWindow) is opened.
        Platform.runLater(() -> {
            boolean loggingEnabled = Main.getProgramPrefs().getBoolean(Main.LOGIN_ENABLED, false);
            boolean monthViewEnabled = Main.getProgramPrefs().getBoolean(Main.MONTH_VIEW_ENABLED, false);

            int consultant_id = LoginWindow.getConsultantID();
            IDataSource db =  DataSource.getDb();
            ArrayList<Appointment> filteredConsultantAppointments = new ArrayList<>();
            try {
                if (monthViewEnabled) {
                    observableAppointments.addAll(filterAppointments(true, (ArrayList<Appointment>) db.getConsultantAppointments(consultant_id)));
                } else {
                    observableAppointments.addAll(filterAppointments(false, (ArrayList<Appointment>) db.getConsultantAppointments(consultant_id)));
                }
                appointmentView.setItems(observableAppointments);
                appointmentView.getSelectionModel().selectFirst();
                populateUpdateFields();

            } catch (NullPointerException e) {
                System.out.println("No Appointments available");
            }

            enableLoggingCheck.setSelected(loggingEnabled);
            enableLoggingCheck.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                enableLoggingCheck.setSelected(t1);
                if (t1)
                    Main.getProgramPrefs().putBoolean(Main.LOGIN_ENABLED, true);
                else
                    Main.getProgramPrefs().putBoolean(Main.LOGIN_ENABLED, false);
            });
            calendarMonthViewCheck.setSelected(monthViewEnabled);
            calendarMonthViewCheck.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
                        calendarMonthViewCheck.setSelected(t1);
                observableAppointments.clear();
                if (t1) {
                    Main.getProgramPrefs().putBoolean(Main.MONTH_VIEW_ENABLED, true);
                    observableAppointments.addAll(filterAppointments(true, (ArrayList<Appointment>) db.getConsultantAppointments(consultant_id)));
                } else {
                    Main.getProgramPrefs().putBoolean(Main.MONTH_VIEW_ENABLED, false);
                    observableAppointments.addAll(filterAppointments(false, (ArrayList<Appointment>) db.getConsultantAppointments(consultant_id)));
                }
                }));
            });
        }

        private ArrayList<Appointment> filterAppointments(boolean byMonth, ArrayList<Appointment> appointments) {
            ArrayList<Appointment> filteredAppointments = new ArrayList<>();
            ZonedDateTime now = ZonedDateTime.now();
            if(byMonth) {
                for(Appointment appointment : appointments) {
                    ZonedDateTime start = appointment.getAppointmentStart();
                    ZonedDateTime oneWeekAhead = now.plusMonths(1);
                    if (start.isBefore(oneWeekAhead) && start.isAfter(now)) {
                        filteredAppointments.add(appointment);
                    }
                }
            } else {
                for(Appointment appointment : appointments) {
                    ZonedDateTime start = appointment.getAppointmentStart();
                    ZonedDateTime oneWeekAhead = now.plusWeeks(1);
                    if (start.isBefore(oneWeekAhead) && start.isAfter(now)) {
                        filteredAppointments.add(appointment);
                    }
                }
            }
            return filteredAppointments;
        }

        @FXML
        private void loadCustomerDialog () throws IOException {
            loadDialog("Customer Records", "customerRecords.fxml");
        }

        @FXML
        private void loadReportDialog () throws IOException {
            loadDialog("Reports", "reportsWindow.fxml");
        }

        private void loadDialog (String title, String fmxlFile) throws IOException {
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
        private void handleSave () {
            try {
                int appointmentID;
                int consultantID = Integer.parseInt(consultantIDField.getText());
                String appointmentType = ((RadioButton) appointmentTypeToggle.getSelectedToggle()).getText();
                int customerID = Integer.parseInt(customerIDField.getText());
                int appointmentYear = consultantAppointmentDate.getValue().getYear();
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


                RadioButton selectedAddUpdateRadioButton = (RadioButton) addUpdateToggle.getSelectedToggle();
                String selectedRadioAddUpdate = selectedAddUpdateRadioButton.getId();
                if (selectedRadioAddUpdate.equals("radioAddAppointment")) {
                    Appointment tempAppointment = new Appointment(customerID, consultantID, "null title",
                            appointmentDescription, appointmentType, appointmentStart, appointmentStop, lastUpdateBy, lastUpdateBy);
                    appointmentID = DataSource.getDb().addAppointment(tempAppointment);
                    tempAppointment.set_id(appointmentID);

                    observableAppointments.add(tempAppointment);
                } else if (selectedRadioAddUpdate.equals("radioUpdateAppointment")) {
                    Appointment selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
                    appointmentID = selectedAppointment.get_id();
                    Appointment tempAppointment = new Appointment(customerID, consultantID, "null title",
                            appointmentDescription, appointmentType, appointmentStart, appointmentStop, lastUpdateBy, lastUpdateBy);
                    tempAppointment.set_id(appointmentID);
                    DataSource.getDb().updateAppointment(appointmentID, tempAppointment);

                    int selectedAppointmentLocation = observableAppointments.indexOf(selectedAppointment);
                    observableAppointments.remove(selectedAppointment);
                    observableAppointments.add(selectedAppointmentLocation, tempAppointment);
                    appointmentView.getSelectionModel().select(tempAppointment);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }

        }

        @FXML
        private void handleDeleteCustomerRecord () {
            Appointment selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
            if (selectedAppointment != null) {
                DataSource.getDb().deleteAppointment(selectedAppointment.get_id());
                observableAppointments.remove(selectedAppointment);
            }

        }

        @FXML
        private void handleSelectCustomerRecord () {
            if (appointmentView.getSelectionModel().getSelectedItem() != null) {
                radioUpdateAppointment.fire();
                populateUpdateFields();
            }
        }

        @FXML
        private void populateUpdateFields () {
            try {
                Appointment selectedAppointment = appointmentView.getSelectionModel().getSelectedItem();
                int appointmentID = selectedAppointment.get_id();
                Appointment retrievedAppointment = DataSource.getDb().getAppointment(appointmentID);
                ZonedDateTime start = retrievedAppointment.getAppointmentStart();
                ZonedDateTime end = retrievedAppointment.getAppointmentEnd();
                Duration appointmentDuration = Duration.between(retrievedAppointment.getAppointmentStart(), retrievedAppointment.getAppointmentEnd());
                long appointmentDurationMinutes = appointmentDuration.getSeconds() / 60;
                String appointmentType = retrievedAppointment.getAppointmentType();

                consultantIDField.setText(String.valueOf(retrievedAppointment.getConsultantID()));
                customerIDField.setText(String.valueOf(retrievedAppointment.getCustomerID()));
                consultantAppointmentDate.setValue(retrievedAppointment.getAppointmentStart().toLocalDate());
                consultantAppointmentTimeHour.setText(String.valueOf(retrievedAppointment.getAppointmentStart().getHour()));
                consultantAppointmentTimeMinute.setText(String.valueOf(retrievedAppointment.getAppointmentStart().getMinute()));
                consultantAppointmentDuration.setText(String.valueOf(appointmentDurationMinutes));
                consultantAppointmentDescription.setText(retrievedAppointment.getAppointmentDescription());
                switch (appointmentType.toLowerCase()) {
                    case "personal":
                        personalRadio.fire();
                        break;
                    case "business":
                        businessRadio.fire();
                        break;
                    default:
                        personalRadio.fire();
                }
            } catch (NullPointerException e) {
                System.out.println("No Customer Records available");
            }
        }

    }
