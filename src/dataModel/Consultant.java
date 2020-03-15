package dataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Consultant {
    private SimpleIntegerProperty _id;
    private SimpleStringProperty name;
    private SimpleStringProperty password;

    public Consultant(String name, String password) {
        this._id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.password = new SimpleStringProperty();

        this.name.set(name);
        this.password.set(password);
    }

    public int get_id() {
        return _id.get();
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
