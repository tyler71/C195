package dataModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class DataSourceSourceMySql implements IDataSource {


    @Override
    public Consultant getConsultant(int consultantID) {
        return null;
    }

    @Override
    public boolean validateLogin(String userName, String password) {
        return false;
    }

    @Override
    public int addCustomer(String name, String address, String phoneNumber) {
        return 0;
    }

    @Override
    public boolean updateCustomer(int customerID, String name, String address, String phoneNumber) {
        return false;
    }

    @Override
    public boolean deleteCustomer(int customerID) {
        return false;
    }

    @Override
    public Customer getCustomer(int customerID) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public int addAppointment(SimpleDateFormat appointmentDate, double appointmentDurationMinutes, String appointmentType, int customerID, int consultantID) {
        return 0;
    }

    @Override
    public boolean updateAppointment(int appointmentID, SimpleDateFormat appointmentDate, double appointmentDurationMinutes, String appointmentType, int customerID, int consultantID) {
        return false;
    }

    @Override
    public boolean deleteAppointment(int appointmentID) {
        return false;
    }

    @Override
    public Appointment getAppointment(int appointmentID) {
        return null;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return null;
    }

    @Override
    public List<Appointment> getConsultantAppointments(int consultantID) {
//        TODO Use collections filters
        return null;
    }
}
