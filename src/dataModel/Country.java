package dataModel;

import javafx.beans.property.SimpleStringProperty;

public class Country {
    private int _id;
    private SimpleStringProperty countryName = new SimpleStringProperty();

    public Country(String countryName) {
        this.countryName.set(countryName);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCountryName() {
        return countryName.get();
    }

    public SimpleStringProperty countryNameProperty() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }
}
