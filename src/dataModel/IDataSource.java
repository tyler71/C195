package dataModel;

import java.text.SimpleDateFormat;
import java.util.List;

public interface IDataSource {
    int addConsultants();
    Consultant getConsultant(int consultantID);
    List<Consultant> getAllConsultants();

    int addCustomer(String name, String address, String phoneNumber);
    boolean updateCustomer(int customerID, String name, String address, String phoneNumber);
    boolean deleteCustomer(int customerID);
    Customer getCustomer(int customerID);
    List<Customer> getAllCustomers();


    int addAppointment(SimpleDateFormat appointmentDate, String appointmentType, int customerID, int consultantID);
    boolean updateAppointment(int appointmentID, SimpleDateFormat appointmentDate, String appointmentType, int customerID, int consultantID);
    boolean deleteAppointment(int appointmentID);
    Appointment getAppointment(int appointmentID);
    List<Appointment> getAllAppointments();
    List<Appointment> getConsultantAppointments(int consultantID);

    boolean validateLogin(String userName, String password);
}
