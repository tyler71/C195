package dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.ZonedDateTime;

public class Appointment {
    private SimpleIntegerProperty _id;
    private ZonedDateTime appointmentDate;
    private SimpleStringProperty appointmentType;
    private SimpleIntegerProperty customerID;
    private SimpleIntegerProperty consultantID;

    public Appointment(int _id, ZonedDateTime appointmentDate, String appointmentType, int customerID, int consultantID) {
        this._id = new SimpleIntegerProperty();
        this.appointmentType = new SimpleStringProperty();
        this.customerID = new SimpleIntegerProperty();
        this.consultantID = new SimpleIntegerProperty();

        this._id.set(_id);
        this.appointmentDate = appointmentDate;
        this.appointmentType.set(appointmentType);
        this.customerID.set(customerID);
        this.consultantID.set(consultantID);
    }

    public int get_id() {
        return _id.get();
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }

    public ZonedDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(ZonedDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType.get();
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public int getConsultantID() {
        return consultantID.get();
    }

    public void setConsultantID(int consultantID) {
        this.consultantID.set(consultantID);
    }
}
