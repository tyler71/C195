package dataModel;

import javafx.beans.property.SimpleStringProperty;

public class Address {
    private int _id;
    private String address;
    private String address2;
    private City city;
    private SimpleStringProperty postalCode = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();

    public Address(String address, String address2, String cityName, String countryName, String postalCode, String phone) {
        this.address = address;
        this.address2 = address2;
        this.city = new City(cityName, countryName);
        this.postalCode.set(postalCode);
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        String returnString = address + " " + address2 + "\n" + city.getCityName() + " " + city.getCountry() + " " + postalCode + " " + phone;
        return returnString;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public City getCity() {
        return city;
    }

    public void setCity(String cityName, String country) {
        this.city = new City(cityName, country);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public SimpleStringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
