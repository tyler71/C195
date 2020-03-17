package viewController;

import dataModel.Appointment;
import dataModel.Customer;
import dataModel.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reports {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    public static final String REPORT_APPOINTMENT_TYPES_FILE = "appointment_types.txt";
    public static final String REPORT_CONSULTANT_SCHEDULES = "consultant_schedules.txt";
    public static final String REPORT_CONSULTANT_CUSTOMERS = "consultant_customers.txt";

    @FXML
    private TextArea reportPreviewTextArea;

    private void populateReportPreview(String text) {
        reportPreviewTextArea.clear();
        reportPreviewTextArea.setText(text);
    }

    private void writeToFile(String fileName, String text) throws IOException {
        FileWriter reportFile = new FileWriter(fileName);
        reportFile.write(text);
        reportFile.close();
    }

    @FXML
    public void handleAppointmentTypesMonth() {
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) DataSource.getDb().getAllAppointments();
        final Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
        ADJUSTERS.put("month", TemporalAdjusters.firstDayOfMonth());

        Map<LocalDate, List<Appointment>> results = appointments.stream()
                .collect(Collectors.groupingBy(appt -> appt.getAppointmentStart().toLocalDate()
                        .with(ADJUSTERS.get("month"))));

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<LocalDate, List<Appointment>> entry : results.entrySet()) {
            sb.append(entry.getKey().getMonth() + " " + entry.getKey().getYear());
            sb.append("\n===\n");
            Map<String, List<Appointment>> mapByAppointmentType = entry.getValue().stream()
                    .collect(Collectors.groupingBy(Appointment::getAppointmentType));
            for (Map.Entry<String, List<Appointment>> apptTypeCollection : mapByAppointmentType.entrySet()) {
                String apptTypeName = apptTypeCollection.getKey();
                int count = apptTypeCollection.getValue().size();
                sb.append(apptTypeName + " " + count + '\n');
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
        populateReportPreview(sb.toString());
        try {
            writeToFile(REPORT_APPOINTMENT_TYPES_FILE, sb.toString());
        } catch (IOException e) {
            System.out.println("Unable to write to file");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSchedulesPerConsultant() {
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) DataSource.getDb().getAllAppointments();
        Map<Integer, List<Appointment>> consultantsAppointments = appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getConsultantID));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, List<Appointment>> consultantAppointments : consultantsAppointments.entrySet()) {
            String consultantName = DataSource.getDb().getConsultant(
                    consultantAppointments.getKey()).getName();
            sb.append(consultantName + "\n===\n");
            for (Appointment appt : consultantAppointments.getValue()) {
                ZonedDateTime apptDate = appt.getAppointmentStart();
                if (apptDate.isAfter(ZonedDateTime.now())) {
                    String title = appt.getAppointmentTitle();
                    String time = appt.getAppointmentStart().format(dateTimeFormatter);
                    String formattedAppointmentTime = String.format(
                            "Appointment %s is at %s\n",
                            title, time);
                    sb.append(formattedAppointmentTime);
                }
            }
        }
        sb.append('\n');
        System.out.println(sb.toString());
        populateReportPreview(sb.toString());
        try {
            writeToFile(REPORT_CONSULTANT_SCHEDULES, sb.toString());
        } catch (IOException e) {
            System.out.println("Unable to write to file");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReportCustomersPerConsultant() {
        ArrayList<Customer> customers = (ArrayList<Customer>) DataSource.getDb().getAllCustomers();
        Map<String, List<Customer>> consultantCustomers = customers
                .stream()
                .collect(Collectors.groupingBy(Customer::getConsultantName));

        StringBuilder sb = new StringBuilder();

        for(Map.Entry<String, List<Customer>> consultant : consultantCustomers.entrySet()) {
            String consultantName = consultant.getKey();
            String customerName;
            sb.append(consultantName + "\n===\n");
            for(Customer customer : consultant.getValue()) {
                customerName = customer.getName();
                sb.append(customerName + '\n');
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
        populateReportPreview(sb.toString());
        try {
            writeToFile(REPORT_CONSULTANT_CUSTOMERS, sb.toString());
        } catch (IOException e) {
            System.out.println("Unable to write to file");
            e.printStackTrace();
        }
    }
}
