package dataModel;

import javafx.beans.property.SimpleStringProperty;

public class City {
    private int _id;
    private SimpleStringProperty cityName = new SimpleStringProperty();
    private Country country;

    public City(String cityName, String country) {
        this.cityName.set(cityName);
        this.country = new Country(country);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCityName() {
        return cityName.get();
    }

    public SimpleStringProperty cityNameProperty() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = new Country(country);
    }
}
