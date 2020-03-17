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
//        TODO Output to TextField and to file
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) DataSource.getDb().getAllAppointments();
        final Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();
        ADJUSTERS.put("month", TemporalAdjusters.firstDayOfMonth());

        Map<LocalDate, List<Appointment>> results = appointments.stream()
                .collect(Collectors.groupingBy(appt -> appt.getAppointmentStart().toLocalDate()
                        .with(ADJUSTERS.get("month"))));

        for(Map.Entry<LocalDate, List<Appointment>> entry : results.entrySet()) {
            System.out.println(entry.getKey().getMonth() + " " + entry.getKey().getYear());
                Map<String, List<Appointment>> mapByAppointmentType = entry.getValue().stream()
                        .collect(Collectors.groupingBy(Appointment::getAppointmentType));
                for(Map.Entry<String, List<Appointment>> apptTypeCollection : mapByAppointmentType.entrySet()) {
                    String apptTypeName = apptTypeCollection.getKey();
                    int count = apptTypeCollection.getValue().size();
                    System.out.println(apptTypeName + " " + count);
                }
            System.out.println();
        }
    }

    @FXML
    public void handleSchedulesPerConsultant() {
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) DataSource.getDb().getAllAppointments();
        Map<Integer, List<Appointment>> consultantsAppointments = appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getConsultantID));
        for(Map.Entry<Integer, List<Appointment>> consultantAppointments : consultantsAppointments.entrySet()) {
            String consultantName = DataSource.getDb().getConsultant(
                    consultantAppointments.getKey()).getName();
            System.out.println(consultantName);
            for(Appointment appt : consultantAppointments.getValue()) {
                System.out.println(appt.getAppointmentTitle() + " " + appt.getAppointmentStart());
            }
        }


    }

    @FXML
    public void handleReportCustomersPerConsultant() {

    }
}
