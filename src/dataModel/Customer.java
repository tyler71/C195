package dataModel;

import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private int _id;
    private boolean active = true;
    private Address address;
    private SimpleStringProperty consultantName = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();

    public Customer(String consultantName, String name, String mainAddress, String address2, String cityName, String countryName, String postalCode, String phone) {
        setAddress(mainAddress, address2, cityName, countryName, postalCode, phone);
        setName(name);
        setConsultantName(consultantName);
        this.phone = address.phoneProperty();
        System.out.println(this.phone);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(String address, String address2, String cityName, String countryName, String postalCode, String phone) {
        this.address = new Address(address, address2, cityName, countryName, postalCode, phone);
    }

    public String getConsultantName() {
        return consultantName.get();
    }

    public SimpleStringProperty consultantNameProperty() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName.set(consultantName);
    }
}


