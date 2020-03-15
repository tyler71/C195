package dataModel;

import java.time.ZonedDateTime;
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

    ZonedDateTime parseSqlTime(String time);
    String convertToSqlTime(ZonedDateTime dateTimeObject);
    int addAppointment(ZonedDateTime appointmentDate, double appointmentDurationMinutes,
                       String appointmentType, int customerID, int consultantID);
    boolean updateAppointment(int appointmentID, ZonedDateTime appointmentDate, double appointmentDurationMinutes,
                              String appointmentType, int customerID, int consultantID);
    boolean deleteAppointment(int appointmentID);
    Appointment getAppointment(int appointmentID);
    List<Appointment> getAllAppointments();
    List<Appointment> getConsultantAppointments(int consultantID);

}
