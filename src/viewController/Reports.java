package viewController;

import dataModel.Appointment;
import dataModel.DataSource;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reports {

    @FXML
    public void handleAppointmentTypesMonth() {
//        TODO  number of appointment types by month
//        For every month, show how many appointments of each type
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) DataSource.getDb().getAllAppointments();
        final Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
        ADJUSTERS.put("month", TemporalAdjusters.firstDayOfMonth());

        Map<LocalDate, List<Appointment>> results = appointments.stream()
                .collect(Collectors.groupingBy(appt -> appt.getAppointmentStart().toLocalDate()
                        .with(ADJUSTERS.get("month"))));

        for(Map.Entry<LocalDate, List<Appointment>> entry : results.entrySet()) {
            System.out.println(entry.getKey().getMonth());
            for(Appointment appt : entry.getValue()) {
                String.format()
                System.out.println(appt);
            }
        }
    }

    @FXML
    public void handleSchedulesPerConsultant() {

    }

    @FXML
    public void handleReportCustomersPerConsultant() {

    }
}
