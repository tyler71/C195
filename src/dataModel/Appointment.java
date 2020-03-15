package dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.ZonedDateTime;

public class Appointment {
    private SimpleIntegerProperty _id = new SimpleIntegerProperty();
    private SimpleIntegerProperty CustomerID = new SimpleIntegerProperty();
    private SimpleIntegerProperty consultantID = new SimpleIntegerProperty();
    private SimpleStringProperty appointmentTitle = new SimpleStringProperty();
    private SimpleStringProperty appointmentDescription = new SimpleStringProperty();
    private SimpleStringProperty appointmentType = new SimpleStringProperty();
    private ZonedDateTime appointmentStart;
    private ZonedDateTime appointmentEnd;
    private SimpleStringProperty createdBy = new SimpleStringProperty();
    private SimpleStringProperty lastUpdateBy = new SimpleStringProperty();

    public Appointment(int customerID, int consultantID, String appointmentTitle, String appointmentDescription,
                       String appointmentType, ZonedDateTime appointmentStart, ZonedDateTime appointmentEnd,
                       String createdBy, String lastUpdateBy) {

        set_id(customerID);
        setConsultantID(consultantID);
        setAppointmentTitle(appointmentTitle);
        setAppointmentDescription(appointmentDescription);
        setAppointmentType(appointmentType);
        setAppointmentStart(appointmentStart);
        setAppointmentEnd(appointmentEnd);
        setCreatedBy(createdBy);
        setLastUpdateBy(lastUpdateBy);
    }

    public int get_id() {
        return _id.get();
    }

    public SimpleIntegerProperty _idProperty() {
        return _id;
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }

    public int getCustomerID() {
        return CustomerID.get();
    }

    public SimpleIntegerProperty customerIDProperty() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        this.CustomerID.set(customerID);
    }

    public int getConsultantID() {
        return consultantID.get();
    }

    public SimpleIntegerProperty consultantIDProperty() {
        return consultantID;
    }

    public void setConsultantID(int consultantID) {
        this.consultantID.set(consultantID);
    }

    public String getAppointmentTitle() {
        return appointmentTitle.get();
    }

    public SimpleStringProperty appointmentTitleProperty() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle.set(appointmentTitle);
    }

    public String getAppointmentDescription() {
        return appointmentDescription.get();
    }

    public SimpleStringProperty appointmentDescriptionProperty() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription.set(appointmentDescription);
    }

    public String getAppointmentType() {
        return appointmentType.get();
    }

    public SimpleStringProperty appointmentTypeProperty() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    public ZonedDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(ZonedDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public ZonedDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(ZonedDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public SimpleStringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public String getLastUpdateBy() {
        return lastUpdateBy.get();
    }

    public SimpleStringProperty lastUpdateByProperty() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy.set(lastUpdateBy);
    }
}
