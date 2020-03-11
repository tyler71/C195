package dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty _id;
    private SimpleStringProperty name;
    private SimpleStringProperty address;
    private SimpleStringProperty phoneNumber;

    public Customer(int _id, String name, String address, String phoneNumber) {
        this._id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();

        this._id.set(_id);
        this.name.set(name);
        this.address.set(address);
        this.phoneNumber.set(phoneNumber);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int get_id() {
        return _id.get();
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
