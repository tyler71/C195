package dataModel;

import java.util.List;

public interface IDataSource {
    List<Consultant> getAllConsultants();
    List<Customer> getAllCustomers();
    List<Consultant> getValidLogins();
    List<Appointment> getConsultantAppointments(int consultantID);
}
