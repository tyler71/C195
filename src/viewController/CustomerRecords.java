package viewController;

import dataModel.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleSelectCustomerRecord() {
        if(customerRecordsView.getSelectionModel().getSelectedItem() != null) {
            radioUpdateCustomer.fire();
            populateUpdateFields();
        }
    }

    @FXML
    private void populateUpdateFields() {
        try{
            Customer selectedCustomer = customerRecordsView.getSelectionModel().getSelectedItem();
            int customerID = selectedCustomer.get_id();
            Customer retrievedCustomer = DataSource.getDb().getCustomer(customerID);
            customerNameField.setText(retrievedCustomer.getName());
            customerPhoneNumberField.setText(retrievedCustomer.getAddress().getPhone());
            customerAddressField.setText(retrievedCustomer.getAddress().getAddress());
            customerCityField.setText(retrievedCustomer.getAddress().getCity().getCityName());
            customerPostalCode.setText(retrievedCustomer.getAddress().getPostalCode());
            customerCountry.setText(retrievedCustomer.getAddress().getCity().getCountry().getCountryName());

        } catch (NullPointerException e) {
            System.out.println("No Customer Records available");
        }
    }

    @FXML
    private void handleSave() {
        int _id;
        Consultant currentConsultant = DataSource.getDb().getConsultant(LoginWindow.getConsultantID());

        String customerName = customerNameField.getText().trim();
        String customerAddressName = customerAddressField.getText().trim();
        String customerCityName = customerCityField.getText().trim();
        String customerPostal = customerPostalCode.getText().trim();
        String customerCountryName = customerCountry.getText().trim();
        String customerPhoneNumber = customerPhoneNumberField.getText().trim();

        List<String> inputFields = Arrays.asList(
                customerName,
                customerAddressName,
                customerCityName,
                customerPostal,
                customerCountryName,
                customerPhoneNumber
        );
//        TODO - Completed
//          Exception controls for invalid or missing customer input
//          Uses mechanism of if conditional and throwing a error if it matches
        for(String field : inputFields) {
            if(field.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(Main.getMainStage());
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("A field was empty or invalid. Save cancelled");
                alert.showAndWait();
                throw new NumberFormatException("Invalid or Empty Input");
            }
        }

        RadioButton selectedRadioButton = (RadioButton) addUpdateToggle.getSelectedToggle();
        String selectedRadio = selectedRadioButton.getId();
        if(selectedRadio.equals("radioAddCustomer")) {
            Customer generated = new Customer(currentConsultant.getName(), customerName, customerAddressName, "null",
                    customerCityName, customerCountryName, customerPostal, customerPhoneNumber);
            int customerID = DataSource.getDb().addCustomer(generated);
            generated.set_id(customerID);
            observableCustomerRecords.add(generated);
            customerRecordsView.getSelectionModel().select(generated);
        } else if (selectedRadio.equals("radioUpdateCustomer")) {
            try {
                Customer selectedCustomer = customerRecordsView.getSelectionModel().getSelectedItem();
                _id = selectedCustomer.get_id();
                Customer generatedCustomer = new Customer(currentConsultant.getName(), customerName, customerAddressName, "null",
                        customerCityName, customerCountryName, customerPostal, customerPhoneNumber);
                generatedCustomer.set_id(_id);
                generatedCustomer.getAddress().set_id(selectedCustomer.getAddress().get_id());
                generatedCustomer.getAddress().getCity().set_id(selectedCustomer.getAddress().getCity().get_id());
                generatedCustomer.getAddress().getCity().getCountry().set_id(selectedCustomer.getAddress().getCity().getCountry().get_id());
                DataSource.getDb().updateCustomer(_id, generatedCustomer);
                int selectedCustomerIndexView = observableCustomerRecords.indexOf(selectedCustomer);
                observableCustomerRecords.remove(selectedCustomerIndexView);
                observableCustomerRecords.add(selectedCustomerIndexView, generatedCustomer);
                customerRecordsView.getSelectionModel().select(generatedCustomer);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private boolean handleDelete() {
        Customer customer = customerRecordsView.getSelectionModel().getSelectedItem();
        int customerID = customer.get_id();
        boolean success = DataSource.getDb().deleteCustomer(customerID);
        if(success) {
            observableCustomerRecords.remove(customer);
            return true;
        }
        return false;
    }
}
