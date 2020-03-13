package viewController;

import dataModel.Consultant;
import dataModel.Customer;
import dataModel.DataSource;
import dataModel.IDataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;

public class CustomerRecords {

    @FXML
    private TableView<Customer> customerRecordsView;
    @FXML
    private ObservableList<Customer> observableCustomerRecords = FXCollections.observableList(new ArrayList<>());

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerCityField;
    @FXML
    private TextField customerPostalCode;
    @FXML
    private TextField customerCountry;
    @FXML
    private TextField customerPhoneNumberField;

    @FXML
    private ToggleGroup addUpdateToggle;

    @FXML
    private RadioButton radioAddCustomer;
    @FXML
    private RadioButton radioUpdateCustomer;

    @FXML
    private void addCustomer() {
    }
    @FXML
    private void updateCustomer() {
    }
    @FXML
    private void deleteCustomer() {
    }

    public void initialize() {
        Platform.runLater(() -> {
            try {
                ArrayList<Customer> customers = (ArrayList<Customer>) DataSource.getDb().getAllCustomers();
                observableCustomerRecords.addAll(customers);
                customerRecordsView.setItems(observableCustomerRecords);
            } catch (NullPointerException e) {
                System.out.println("No Customer Records available");
            }
        });
    }

    @FXML
    private void handleSelectCustomerRecord() {
        if(customerRecordsView.getSelectionModel().getSelectedItem() != null)
            radioUpdateCustomer.fire();
//        runs populateUpdateFields
    }

    @FXML
    private void populateUpdateFields() {
        try{
            Customer selectedCustomer = customerRecordsView.getSelectionModel().getSelectedItem();
            int customerID = selectedCustomer.get_id();
            Customer retrievedCustomer = DataSource.getDb().getCustomer(customerID);
        } catch (NullPointerException e) {
            System.out.println("No Customer Records available");
        }
    }

    @FXML
    private void handleSave() {
        int _id;
        Consultant currentConsultant = new Consultant("Tyler", "test");
//        TODO Load current consultant from somewhere
        String customerName = customerNameField.getText();
        String customerAddressName = customerAddressField.getText();
        String customerCityName = customerCityField.getText();
        String customerPostal = customerPostalCode.getText();
        String customerCountryName = customerCountry.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();

        RadioButton selectedRadioButton = (RadioButton) addUpdateToggle.getSelectedToggle();
        String selectedRadio = selectedRadioButton.getId();
        if(selectedRadio.equals("radioAddCustomer")) {
            Customer generated = new Customer(currentConsultant.getName(), customerName, customerAddressName, "null",
                    customerCityName, customerCountryName, customerPostal, customerPhoneNumber);
            DataSource.getDb().addCustomer(generated);
        } else if (selectedRadio.equals("radioUpdateCustomer")) {
            try {
                _id = customerRecordsView.getSelectionModel().getSelectedItem().get_id();
                Customer generated = new Customer(currentConsultant.getName(), customerName, customerAddressName, "null",
                        customerCityName, customerCountryName, customerPostal, customerPhoneNumber);
                DataSource.getDb().updateCustomer(_id, generated);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
