package dataModel;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSourceSourceMySql implements IDataSource {
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/C195";
    public static final String CONNECTION_USERNAME = "C195";
    public static final String CONNECTION_PASSWORD = "3O316HGTm9EO1oKW";

    public static final String TABLE_USER = "user";
    public static final String USER_COLUMN_ID = "userId";
    public static final String USER_COLUMN_USERNAME = "userName";
    public static final String USER_COLUMN_PASSWORD = "password";

    public static final String TABLE_CUSTOMER = "customer";
    public static final String CUSTOMER_COLUMN_ID = "customerId";
    public static final String CUSTOMER_COLUMN_NAME = "customerName";
    public static final String CUSTOMER_COLUMN_ADDRESS_ID = "addressId";
    public static final String CUSTOMER_COLUMN_ACTIVE = "active";
    public static final String CUSTOMER_COLUMN_CREATE_DATE = "createDate";
    public static final String CUSTOMER_COLUMN_CREATED_BY = "createdBy";
    public static final String CUSTOMER_COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_CITY = "city";
    public static final String CITY_COLUMN_ID = "cityId";
    public static final String CITY_COLUMN_NAME = "city";
    public static final String CITY_COLUMN_COUNTRY_ID = "countryId";
    public static final String CITY_COLUMN_CREATE_DATE = "createDate";
    public static final String CITY_COLUMN_CREATED_BY = "createdBy";
    public static final String CITY_COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_COUNTRY = "country";
    public static final String COUNTRY_COLUMN_ID = "countryId";
    public static final String COUNTRY_COLUMN_NAME = "country";
    public static final String COUNTRY_COLUMN_CREATE_DATE = "createDate";
    public static final String COUNTRY_COLUMN_CREATED_BY = "createdBy";
    public static final String COUNTRY_COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_ADDRESS = "address";
    public static final String ADDRESS_COLUMN_ADDRESS = "address";
    public static final String ADDRESS_COLUMN_ADDRESS2 = "address2";
    public static final String ADDRESS_COLUMN_CITY_ID = "cityId";
    public static final String ADDRESS_COLUMN_POSTAL_CODE = "postalCode";
    public static final String ADDRESS_COLUMN_PHONE = "phone";
    public static final String ADDRESS_COLUMN_CREATE_DATE = "createDate";
    public static final String ADDRESS_COLUMN_CREATED_BY = "createdBy";
    public static final String ADDRESS_COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String GET_CONSULTANT_QUERY_START = String.format(
            "SELECT %s, %s "
            + "FROM %s WHERE %s = ?",
            USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD,
            TABLE_USER, USER_COLUMN_ID);

    public static final String VALIDATE_LOGIN_QUERY_START = String.format(
            "SELECT %s, %s FROM %s WHERE %s = ?",
            USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD, TABLE_USER, USER_COLUMN_USERNAME);


    public static final String INSERT_COUNTRY_START = String.format(
            "INSERT INTO %s(%s, %s, %s, %s) " +
                    "VALUES(?, NOW(), ?, ?)",
            TABLE_COUNTRY, COUNTRY_COLUMN_NAME, COUNTRY_COLUMN_CREATE_DATE, COUNTRY_COLUMN_CREATED_BY, COUNTRY_COLUMN_LAST_UPDATE_BY);
    public static final String INSERT_CITY_START = String.format(
            "INSERT INTO %s(%s, %s, %s, %s, %s) " +
                    "VALUES(?, ?, NOW(), ?, ?)",
            TABLE_CITY, CITY_COLUMN_NAME, CITY_COLUMN_COUNTRY_ID,
            CITY_COLUMN_CREATE_DATE, CITY_COLUMN_CREATED_BY, CITY_COLUMN_LAST_UPDATE_BY);
    public static final String INSERT_ADDRESS_START = String.format(
            "INSERT INTO %s(%s, %s, %s, %s, %s, " +
                    "%s, %s, %s) " +
                "VALUES(?, ?, ?, ?, ?, " +
                    "NOW(), ?, ?)",
            TABLE_ADDRESS, ADDRESS_COLUMN_ADDRESS, ADDRESS_COLUMN_ADDRESS2, ADDRESS_COLUMN_CITY_ID,
            ADDRESS_COLUMN_POSTAL_CODE, ADDRESS_COLUMN_PHONE, ADDRESS_COLUMN_CREATE_DATE,
            ADDRESS_COLUMN_CREATED_BY, ADDRESS_COLUMN_LAST_UPDATE_BY);
    public static final String INSERT_CUSTOMER_START = String.format(
            "INSERT INTO %s(%s, %s, %s, %s, " +
                    "%s, %s) " +
                    "VALUES(?, ?, ?, NOW(), ?, ?)",
            TABLE_CUSTOMER, CUSTOMER_COLUMN_NAME, CUSTOMER_COLUMN_ADDRESS_ID, CUSTOMER_COLUMN_ACTIVE,
            CUSTOMER_COLUMN_CREATE_DATE, CUSTOMER_COLUMN_CREATED_BY, CUSTOMER_COLUMN_LAST_UPDATE_BY);


    private Connection conn;
    private PreparedStatement queryGetConsultant;
    private PreparedStatement queryValidateLogin;

    private PreparedStatement insertCustomerCountry;
    private PreparedStatement insertCustomerCity;
    private PreparedStatement insertCustomerAddress;
    private PreparedStatement insertCustomer;

    @Override
    public boolean openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USERNAME, CONNECTION_PASSWORD);

            queryGetConsultant = conn.prepareStatement(GET_CONSULTANT_QUERY_START, Statement.RETURN_GENERATED_KEYS);
            queryValidateLogin = conn.prepareStatement(VALIDATE_LOGIN_QUERY_START, Statement.RETURN_GENERATED_KEYS);

            insertCustomerCountry = conn.prepareStatement(INSERT_COUNTRY_START, Statement.RETURN_GENERATED_KEYS);
            insertCustomerCity = conn.prepareStatement(INSERT_CITY_START, Statement.RETURN_GENERATED_KEYS);
            insertCustomerAddress = conn.prepareStatement(INSERT_ADDRESS_START, Statement.RETURN_GENERATED_KEYS);
            insertCustomer = conn.prepareStatement(INSERT_CUSTOMER_START, Statement.RETURN_GENERATED_KEYS);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean closeConnection() {
        try {
            List<PreparedStatement> queries = Arrays.asList(
                    queryGetConsultant,
                    queryValidateLogin,
                    insertCustomerCountry,
                    insertCustomerCity,
                    insertCustomerAddress,
                    insertCustomer
            );
            for (PreparedStatement query : queries) {
                if(query != null)
                    query.close();
            }

            if(conn != null)
                conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Consultant getConsultant(int consultantID) {
        try {
            queryGetConsultant.setString(1, String.valueOf(consultantID));
            ResultSet results = queryGetConsultant.executeQuery();
            int indexConsultantID = 1;
            int indexConsultantUsername = 2;
            int indexConsultantPassword = 3;
            Consultant consultant = new Consultant(results.getString(indexConsultantUsername), results.getString(indexConsultantPassword));
            return consultant;
        } catch (SQLException e) {
            System.out.println("Unable to find Consultant");
            return null;
        }
    }

    @Override
    public boolean validateLogin(String userName, String password) {
        try {
            queryValidateLogin.setString(1, userName);
            queryValidateLogin.setString(2, password);
            ResultSet results = queryValidateLogin.executeQuery();
            int indexConsultantUsername = 1;
            int indexConsultantPassword = 2;
            while(results.next()) {
                if (userName.equals(results.getString(indexConsultantUsername))
                        && password.equals(results.getString(indexConsultantPassword))) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int addCustomer(Customer customer) {

        String updatedBy = customer.getConsultant().getName();
        Address ca = customer.getAddress();
        String customerCountry = ca.getCity().getCountry().getCountryName();
        String customerCity = ca.getCity().getCityName();

        ResultSet results;
        int countryID = -1;
        int cityID = -1;
        int addressID = -1;
        int customerID = -1;
        int customerActive = 1;

        try{
            conn.setAutoCommit(false);

            insertCustomerCountry.setString(1, customerCountry);
            insertCustomerCountry.setString(2, updatedBy);
            insertCustomerCountry.setString(3, updatedBy);
            insertCustomerCountry.executeUpdate();
            results = insertCustomerCountry.getGeneratedKeys();
            if(results.next())
                countryID = results.getInt(1);
            insertCustomerCity.setString(1, customerCity);
            insertCustomerCity.setString(2, String.valueOf(countryID));
            insertCustomerCity.setString(3, updatedBy);
            insertCustomerCity.setString(4, updatedBy);
            insertCustomerCity.executeUpdate();
            results = insertCustomerCity.getGeneratedKeys();
            if(results.next())
                cityID = results.getInt(1);
            insertCustomerAddress.setString(1, ca.getAddress());
            insertCustomerAddress.setString(2, ca.getAddress2());
            insertCustomerAddress.setString(3, String.valueOf(cityID));
            insertCustomerAddress.setString(4, ca.getPostalCode());
            insertCustomerAddress.setString(5, ca.getPhone());
            insertCustomerAddress.setString(6, updatedBy);
            insertCustomerAddress.setString(7, updatedBy);
            insertCustomerAddress.executeUpdate();
            results = insertCustomerAddress.getGeneratedKeys();
            if(results.next())
                addressID = results.getInt(1);
            insertCustomer.setString(1, customer.getName());
            insertCustomer.setString(2, String.valueOf(addressID));
            insertCustomer.setString(3, String.valueOf(customerActive));
            insertCustomer.setString(4, updatedBy);
            insertCustomer.setString(5, updatedBy);
            insertCustomer.executeUpdate();
            results = insertCustomer.getGeneratedKeys();
            if(results.next())
                customerID = results.getInt(1);

            return customerID;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                System.out.println("Rolling back...");
                conn.rollback();
                return -1;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return -1;
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateCustomer(int customerID, String name, String address, String phoneNumber) {
        return false;
    }

    @Override
    public boolean deleteCustomer(int customerID) {
        return false;
    }

    @Override
    public Customer getCustomer(int customerID) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public int addAppointment(ZonedDateTime appointmentDate, double appointmentDurationMinutes, String appointmentType, int customerID, int consultantID) {
        return 0;
    }

    @Override
    public boolean updateAppointment(int appointmentID, ZonedDateTime appointmentDate, double appointmentDurationMinutes, String appointmentType, int customerID, int consultantID) {
        return false;
    }

    @Override
    public boolean deleteAppointment(int appointmentID) {
        return false;
    }

    @Override
    public Appointment getAppointment(int appointmentID) {
        return null;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return null;
    }

    @Override
    public List<Appointment> getConsultantAppointments(int consultantID) {
//        TODO Use collections filters
        return null;
    }
}
