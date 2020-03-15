package dataModel;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface IDataSource {

    boolean openConnection();
    boolean closeConnection();

    Consultant getConsultant(int consultantID);
    Consultant searchConsultantName(String consultantUserName);
    boolean validateLogin(String userName, String password);

    int addCustomer(Customer customer);
    boolean updateCustomer(int customerID, Customer customer);
    boolean deleteCustomer(int customerID);
    Customer getCustomer(int customerID);
    List<Customer> getAllCustomers();

    DateTimeFormatter getDateTimeFormatter();
    ZonedDateTime parseSqlTime(String time);
    String convertToSqlTime(ZonedDateTime dateTimeObject);
    int addAppointment(Appointment appointment);
    boolean updateAppointment(int appointmentID, Appointment appointment);
    boolean deleteAppointment(int appointmentID);
    Appointment getAppointment(int appointmentID);
    List<Appointment> getAllAppointments();
    List<Appointment> getConsultantAppointments(int consultantID);

}
