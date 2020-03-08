package dataModel;

import java.text.SimpleDateFormat;

public class Appointment {
    private int _id;
    private SimpleDateFormat appointmentDate;
    private String appointmentType;
    private int customerID;
    private int consultantID;

    public Appointment(int _id, SimpleDateFormat appointmentDate, String appointmentType, int customerID, int consultantID) {
        this._id = _id;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.customerID = customerID;
        this.consultantID = consultantID;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public SimpleDateFormat getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(SimpleDateFormat appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getConsultantID() {
        return consultantID;
    }

    public void setConsultantID(int consultantID) {
        this.consultantID = consultantID;
    }
}
