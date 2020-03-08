package viewController;

import dataModel.Customer;
import dataModel.DataSource;
import dataModel.DataSourceSourceMySql;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String customerPhoneNumber = customerPhoneNumberField.getText();

        RadioButton selectedRadioButton = (RadioButton) addUpdateToggle.getSelectedToggle();
        String selectedRadio = selectedRadioButton.getId();
        if(selectedRadio.equals("radioAddCustomer")) {
            DataSource.getDb().addCustomer(customerName, customerAddress, customerPhoneNumber);
        } else if (selectedRadio.equals("radioUpdateCustomer")) {
            try {
                _id = customerRecordsView.getSelectionModel().getSelectedItem().get_id();
                DataSource.getDb().updateCustomer(_id, customerName, customerAddress, customerPhoneNumber);
            } catch (NullPointerException ignored) {}
        }
    }
}
