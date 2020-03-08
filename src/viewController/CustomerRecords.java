package viewController;

import dataModel.Customer;
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
                ArrayList<Customer> customers = (ArrayList<Customer>) DataSourceSourceMySql.getInstance().getAllCustomers();
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
            Customer retrievedCustomer = DataSourceSourceMySql.getInstance().getCustomer(customerID);
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
        String selectedRadio = selectedRadioButton.getText();
        if(selectedRadio.equals("Add")) {
            DataSourceSourceMySql.getInstance().addCustomer(customerName, customerAddress, customerPhoneNumber);
        } else if (selectedRadio.equals("Update")) {
            try {
                _id = customerRecordsView.getSelectionModel().getSelectedItem().get_id();
                DataSourceSourceMySql.getInstance().updateCustomer(_id, customerName, customerAddress, customerPhoneNumber);
            } catch (NullPointerException ignored) {}
        }
    }
}
