package dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty _id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private boolean active = true;
    private Address address;
    private Consultant consultant;

    public Customer(Consultant consultant, String name, String address, String address2, String cityName, String countryName, String postalCode, String phone) {
        setAddress(address, address2, cityName, countryName, postalCode, phone);
        setName(name);
        this.consultant = consultant;
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(String address, String address2, String cityName, String countryName, String postalCode, String phone) {
        this.address = new Address(address, address2, cityName, countryName, postalCode, phone);
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }
}

class Address {
    private int _id;
    private String address;
    private String address2;
    private City city;
    private String postalCode;
    private String phone;

    public Address(String address, String address2, String cityName, String countryName, String postalCode, String phone) {
        this.address = address;
        this.address2 = address2;
        this.city = new City(cityName, countryName);
        this.postalCode = postalCode;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return address + " " + address2 + "\n" + city + " " + city.getCountry() + " " + postalCode + " " + phone;
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
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

class City {
    private int _id;
    private String cityName;
    private Country country;

    public City(String cityName, String country) {
        this.cityName = cityName;
        this.country = new Country(country);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = new Country(country);
    }
}
class Country {
    private int _id;
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}