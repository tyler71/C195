package dataModel;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSourceSourceMySql implements IDataSource {
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/C195";
    private static final String CONNECTION_USERNAME = "C195";
    private static final String CONNECTION_PASSWORD = "3O316HGTm9EO1oKW";

private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    public static final String TABLE_APPOINTMENT = "appointment";
    public static final String APPOINTMENT_COLUMN_ID = "appointmentId";
    public static final String APPOINTMENT_COLUMN_CUSTOMER_ID = "customerId";
    public static final String APPOINTMENT_COLUMN_USER_ID = "userId";
    public static final String APPOINTMENT_COLUMN_TITLE = "title";
    public static final String APPOINTMENT_COLUMN_DESCRIPTION = "description";
    public static final String APPOINTMENT_COLUMN_TYPE = "type";
    public static final String APPOINTMENT_COLUMN_START = "start";
    public static final String APPOINTMENT_COLUMN_END = "end";
    public static final String APPOINTMENT_COLUMN_CREATED_BY = "createdBy";
    public static final String APPOINTMENT_COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String TABLE_USER = "user";
    public static final String USER_COLUMN_ID = "userId";
    public static final String USER_COLUMN_USERNAME = "userName";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_ACTIVE = "active";

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
    public static final String ADDRESS_COLUMN_ID = "addressId";
    public static final String ADDRESS_COLUMN_ADDRESS = "address";
    public static final String ADDRESS_COLUMN_ADDRESS2 = "address2";
    public static final String ADDRESS_COLUMN_CITY_ID = "cityId";
    public static final String ADDRESS_COLUMN_POSTAL_CODE = "postalCode";
    public static final String ADDRESS_COLUMN_PHONE = "phone";
    public static final String ADDRESS_COLUMN_CREATE_DATE = "createDate";
    public static final String ADDRESS_COLUMN_CREATED_BY = "createdBy";
    public static final String ADDRESS_COLUMN_LAST_UPDATE_BY = "lastUpdateBy";

    public static final String QUERY_GET_ALL_APPOINTMENT = String.format(
            "SELECT %s, %s, %s, %s, %s, " +
                    "%s, %s, %s, %s, %s " +
                    "FROM %s",
            APPOINTMENT_COLUMN_ID, APPOINTMENT_COLUMN_CUSTOMER_ID, APPOINTMENT_COLUMN_USER_ID,
            APPOINTMENT_COLUMN_TITLE, APPOINTMENT_COLUMN_DESCRIPTION, APPOINTMENT_COLUMN_TYPE,
            APPOINTMENT_COLUMN_START, APPOINTMENT_COLUMN_END, APPOINTMENT_COLUMN_CREATED_BY,
            APPOINTMENT_COLUMN_LAST_UPDATE_BY,
            TABLE_APPOINTMENT);
    public static final String QUERY_GET_USER_APPOINTMENT_START = String.format(
            QUERY_GET_ALL_APPOINTMENT + " WHERE " + APPOINTMENT_COLUMN_USER_ID + " = ?"
    );

    public static final String QUERY_CONSULTANT_START = String.format(
            "SELECT %s, %s "
            + "FROM %s WHERE %s = ?",
            USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD,
            TABLE_USER, USER_COLUMN_ID);
    public static final String QUERY_CONSULTANT_NAME = String.format(
            "SELECT %s FROM %s WHERE %s = ?",
            USER_COLUMN_ID, TABLE_USER, USER_COLUMN_USERNAME);
    public static final String QUERY_ALL_CUSTOMER_START = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "%s.%s, %s.%s, %s.%s FROM %s " +
                    "INNER JOIN %s ON %s.%s = %s.%s " +
                    "INNER JOIN %s ON %s.%s = %s.%s " +
                    "INNER JOIN %s ON %s.%s = %s.%s",
            TABLE_CUSTOMER, CUSTOMER_COLUMN_ID, TABLE_ADDRESS, ADDRESS_COLUMN_ID, TABLE_CITY, CITY_COLUMN_ID, TABLE_COUNTRY,
            COUNTRY_COLUMN_ID, TABLE_CUSTOMER, CUSTOMER_COLUMN_LAST_UPDATE_BY,
            TABLE_CUSTOMER, CUSTOMER_COLUMN_NAME, TABLE_ADDRESS, ADDRESS_COLUMN_ADDRESS,
            TABLE_ADDRESS, ADDRESS_COLUMN_ADDRESS2, TABLE_CITY, CITY_COLUMN_NAME,
            TABLE_COUNTRY, COUNTRY_COLUMN_NAME, TABLE_ADDRESS, ADDRESS_COLUMN_POSTAL_CODE,
            TABLE_ADDRESS, ADDRESS_COLUMN_PHONE, TABLE_CUSTOMER,
            TABLE_ADDRESS, TABLE_CUSTOMER, ADDRESS_COLUMN_ID, TABLE_ADDRESS, ADDRESS_COLUMN_ID,
            TABLE_CITY, TABLE_ADDRESS, ADDRESS_COLUMN_CITY_ID, TABLE_CITY, CITY_COLUMN_ID,
            TABLE_COUNTRY, TABLE_COUNTRY, COUNTRY_COLUMN_ID, TABLE_CITY, CITY_COLUMN_COUNTRY_ID);

    public static final String QUERY_GET_CUSTOMER_START = QUERY_ALL_CUSTOMER_START
            + " WHERE " + TABLE_CUSTOMER + "." + CUSTOMER_COLUMN_ID + " = ?";


    public static final String VALIDATE_LOGIN_QUERY_START = String.format(
            "SELECT %s, %s FROM %s WHERE %s = ? && %s = ?",
            USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD, TABLE_USER, USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD);


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

    public static final String UPDATE_CUSTOMER_COUNTRY = String.format(
            "UPDATE %s SET %s=?, %s=? WHERE %s = ?",
            TABLE_COUNTRY, COUNTRY_COLUMN_NAME, COUNTRY_COLUMN_LAST_UPDATE_BY, COUNTRY_COLUMN_ID);
    public static final String UPDATE_CUSTOMER_CITY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=? WHERE %s = ?",
            TABLE_CITY, CITY_COLUMN_NAME, CITY_COLUMN_COUNTRY_ID, CITY_COLUMN_LAST_UPDATE_BY, CITY_COLUMN_ID);
    public static final String UPDATE_CUSTOMER_ADDRESS = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=?," +
                    "%s=?, %s=?, %s=?" +
                "WHERE %s = ?",
            TABLE_ADDRESS, ADDRESS_COLUMN_ADDRESS, ADDRESS_COLUMN_ADDRESS2, ADDRESS_COLUMN_CITY_ID,
            ADDRESS_COLUMN_POSTAL_CODE, ADDRESS_COLUMN_PHONE, ADDRESS_COLUMN_LAST_UPDATE_BY,
            ADDRESS_COLUMN_ID);
    public static final String UPDATE_CUSTOMER = String.format(
            "UPDATE %s " +
                    "SET %s=?, %s=?, %s=?, %s=?" +
                    "WHERE %s = ?",
            TABLE_CUSTOMER,
            CUSTOMER_COLUMN_NAME, CUSTOMER_COLUMN_ADDRESS_ID, CUSTOMER_COLUMN_ACTIVE, CUSTOMER_COLUMN_LAST_UPDATE_BY,
            CUSTOMER_COLUMN_ID);
    public static final String DELETE_CUSTOMER = String.format(
            String.format("DELETE FROM %s WHERE %s = ?",
                    TABLE_CUSTOMER, CUSTOMER_COLUMN_ID));

//    DELETE from customer WHERE customerId = 0;

    private Connection conn;
    private PreparedStatement queryGetConsultant;
    private PreparedStatement queryConsultantName;
    private PreparedStatement queryValidateLogin;
    private PreparedStatement queryAllCustomers;
    private PreparedStatement queryGetCustomer;
    private PreparedStatement queryGetAllAppointment;

    private PreparedStatement insertCustomerCountry;
    private PreparedStatement insertCustomerCity;
    private PreparedStatement insertCustomerAddress;
    private PreparedStatement insertCustomer;

    private PreparedStatement updateCustomerCountry;
    private PreparedStatement updateCustomerCity;
    private PreparedStatement updateCustomerAddress;
    private PreparedStatement updateCustomer;

    private PreparedStatement deleteCustomer;

    @Override
    public boolean openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USERNAME, CONNECTION_PASSWORD);

            queryGetConsultant = conn.prepareStatement(QUERY_CONSULTANT_START);
            queryConsultantName = conn.prepareStatement(QUERY_CONSULTANT_NAME);
            queryValidateLogin = conn.prepareStatement(VALIDATE_LOGIN_QUERY_START);
            queryAllCustomers = conn.prepareStatement(QUERY_ALL_CUSTOMER_START);
            queryGetCustomer = conn.prepareStatement(QUERY_GET_CUSTOMER_START);
            queryGetAllAppointment = conn.prepareStatement(QUERY_GET_ALL_APPOINTMENT);

            insertCustomerCountry = conn.prepareStatement(INSERT_COUNTRY_START, Statement.RETURN_GENERATED_KEYS);
            insertCustomerCity = conn.prepareStatement(INSERT_CITY_START, Statement.RETURN_GENERATED_KEYS);
            insertCustomerAddress = conn.prepareStatement(INSERT_ADDRESS_START, Statement.RETURN_GENERATED_KEYS);
            insertCustomer = conn.prepareStatement(INSERT_CUSTOMER_START, Statement.RETURN_GENERATED_KEYS);

            updateCustomerCountry = conn.prepareStatement(UPDATE_CUSTOMER_COUNTRY);
            updateCustomerCity = conn.prepareStatement(UPDATE_CUSTOMER_CITY);
            updateCustomerAddress = conn.prepareStatement(UPDATE_CUSTOMER_ADDRESS);
            updateCustomer = conn.prepareStatement(UPDATE_CUSTOMER);

            deleteCustomer = conn.prepareStatement(DELETE_CUSTOMER);

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
                    queryAllCustomers,
                    queryGetAllAppointment,
                    insertCustomerCountry,
                    insertCustomerCity,
                    insertCustomerAddress,
                    insertCustomer,
                    updateCustomerCountry,
                    updateCustomerCity,
                    updateCustomerAddress,
                    updateCustomer,
                    deleteCustomer
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
            System.out.println(queryGetConsultant);
            ResultSet results = queryGetConsultant.executeQuery();
            if(! results.next()) {
                throw new SQLException("No consultant found");
            }
            int indexConsultantUsername = 1;
            int indexConsultantPassword = 2;
            Consultant consultant = new Consultant(results.getString(indexConsultantUsername), results.getString(indexConsultantPassword));
            consultant.set_id(consultantID);
            return consultant;
        } catch (SQLException e) {
            System.out.println("Unable to find Consultant");
            return null;
        }
    }
    public Consultant searchConsultantName(String consultantUserName) {
        try {
            queryConsultantName.setString(1, consultantUserName);
            ResultSet results = queryConsultantName.executeQuery();

            if(results.next()) {
                int consultantID = results.getInt(1);
                return getConsultant(consultantID);
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
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

    private int addCustomerCountry(Customer customer) throws SQLException {
        String countryName = customer.getAddress().getCity().getCountry().getCountryName();
        int countryID = -1;
        String updatedBy = customer.getConsultantName();
        ResultSet results;
//        TODO Query first
        try {
            insertCustomerCountry.setString(1, countryName);
            insertCustomerCountry.setString(2, updatedBy);
            insertCustomerCountry.setString(3, updatedBy);
            insertCustomerCountry.executeUpdate();
            results = insertCustomerCountry.getGeneratedKeys();
            if (results.next())
                countryID = results.getInt(1);
            return countryID;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to insert Country");
        }
    }

    private int addCustomerCity(Customer customer, int countryID) throws SQLException {
        String cityName = customer.getAddress().getCity().getCityName();
        int cityID = -1;
        String updatedBy = customer.getConsultantName();
        ResultSet results;
        try {
            insertCustomerCity.setString(1, cityName);
            insertCustomerCity.setString(2, String.valueOf(countryID));
            insertCustomerCity.setString(3, updatedBy);
            insertCustomerCity.setString(4, updatedBy);
            insertCustomerCity.executeUpdate();
            results = insertCustomerCity.getGeneratedKeys();
            if(results.next())
                cityID = results.getInt(1);
            return cityID;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Unable to insert City");
        }
    }

    private int addCustomerAddress(Customer customer, int cityID) throws SQLException {
        ResultSet results;
        int addressID = -1;

        Address ca = customer.getAddress();
        String updatedBy = customer.getConsultantName();
        try {
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
            return addressID;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to insert Address");
        }
    }

    @Override
    public int addCustomer(Customer customer) {

        String updatedBy = customer.getConsultantName();

        ResultSet results;
        int countryID;
        int cityID;
        int addressID;
        int customerID = -1;
        int customerActive = 1;

        try{
            conn.setAutoCommit(false);

            countryID = addCustomerCountry(customer);
            cityID = addCustomerCity(customer, countryID);
            addressID = addCustomerAddress(customer, cityID);

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
    public boolean updateCustomer(int CustomerID, Customer customer) {
        String updatedBy = customer.getConsultantName();
        Country country = customer.getAddress().getCity().getCountry();
        City city = customer.getAddress().getCity();
        Address address = customer.getAddress();
        String addressID = String.valueOf(address.get_id());
        String countryID = String.valueOf(country.get_id());
        String cityID = String.valueOf(city.get_id());

        int affectedRecords;

        try {
            conn.setAutoCommit(false);

            updateCustomerCountry.setString(1, country.getCountryName());
            updateCustomerCountry.setString(2, updatedBy);
            updateCustomerCountry.setString(3, countryID);
            affectedRecords = updateCustomerCountry.executeUpdate();
            if(affectedRecords != 1)
                throw new SQLException("More then one record affected");
            updateCustomerCity.setString(1, city.getCityName());
            updateCustomerCity.setString(2, String.valueOf(country.get_id()));
            updateCustomerCity.setString(3, updatedBy);
            updateCustomerCity.setString(4, cityID);
            affectedRecords = updateCustomerCity.executeUpdate();
            if(affectedRecords != 1)
                throw new SQLException("More then one record affected");
            updateCustomerAddress.setString(1, customer.getAddress().getAddress());
            updateCustomerAddress.setString(2, customer.getAddress().getAddress2());
            updateCustomerAddress.setString(3, cityID);
            updateCustomerAddress.setString(4, customer.getAddress().getPostalCode());
            updateCustomerAddress.setString(5, customer.getAddress().getPhone());
            updateCustomerAddress.setString(6, updatedBy);
            updateCustomerAddress.setString(7, addressID);
            affectedRecords = updateCustomerAddress.executeUpdate();
            if(affectedRecords != 1)
                throw new SQLException("More then one record affected");

        } catch (SQLException e) {
            try {
                System.out.println("Rolling back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteCustomer(int customerID) {
//        TODO Improve delete to remove country, city and address records... maybe
        try {
            deleteCustomer.setInt(1, customerID);
            conn.setAutoCommit(false);
            int affectedRows = deleteCustomer.executeUpdate();
            if(affectedRows != 1) {
                throw new SQLException("More then one record selected to be deleted!");
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                e.printStackTrace();
                return false;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Customer getCustomer(int customerID) {
        int addressID;
        int cityID;
        int countryID;
        String consultantName;
        String customerName;
        String address;
        String address2;
        String city;
        String country;
        String postalCode;
        String phoneNumber;

        try {
            Statement statement = conn.createStatement();

            queryGetCustomer.setString(1, String.valueOf(customerID));

            ResultSet results = queryGetCustomer.executeQuery();
            if(!results.next())
                throw new SQLException("No Customer Found");
            addressID = results.getInt(2);
            cityID = results.getInt(3);
            countryID = results.getInt(4);
            consultantName = results.getString(5);
            customerName = results.getString(6);
            address = results.getString(7);
            address2 = results.getString(8);
            city = results.getString(9);
            country = results.getString(10);
            postalCode = results.getString(11);
            phoneNumber = results.getString(12);

            Customer tempCustomer = new Customer(
                    consultantName,
                    customerName,
                    address,
                    address2,
                    city,
                    country,
                    postalCode,
                    phoneNumber);
            tempCustomer.set_id(customerID);
            tempCustomer.getAddress().set_id(addressID);
            tempCustomer.getAddress().getCity().set_id(cityID);
            tempCustomer.getAddress().getCity().getCountry().set_id(countryID);
            return tempCustomer;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        int customerID;
        int addressID;
        int cityID;
        int countryID;
        String consultantName;
        String customerName;
        String address;
        String address2;
        String city;
        String country;
        String postalCode;
        String phoneNumber;

        ArrayList<Customer> generatedCustomers = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(QUERY_ALL_CUSTOMER_START);
            while(results.next()) {
                customerID = results.getInt(1);
                addressID = results.getInt(2);
                cityID = results.getInt(3);
                countryID = results.getInt(4);
                consultantName = results.getString(5);
                customerName = results.getString(6);
                address = results.getString(7);
                address2 = results.getString(8);
                city = results.getString(9);
                country = results.getString(10);
                postalCode = results.getString(11);
                phoneNumber = results.getString(12);

                Customer tempCustomer = new Customer(
                        consultantName,
                        customerName,
                        address,
                        address2,
                        city,
                        country,
                        postalCode,
                        phoneNumber);
                tempCustomer.set_id(customerID);
                tempCustomer.getAddress().set_id(addressID);
                tempCustomer.getAddress().getCity().set_id(cityID);
                tempCustomer.getAddress().getCity().getCountry().set_id(countryID);
                generatedCustomers.add(tempCustomer);
            }
            return generatedCustomers;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public int addAppointment(Appointment appointment) {
        return 0;
    }

    @Override
    public boolean updateAppointment(int appointmentID, Appointment appointment) {
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
        int appointmentID;
        int customerID;
        int consultantID;
        String appointmentTitle;
        String appointmentDescription;
        String appointmentType;
        String appointmentStart;
        String appointmentEnd;
        String createdBy;
        String lastUpdateBy;

        ArrayList<Appointment> generatedAppointments = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            System.out.println("getallAppointments " + QUERY_GET_ALL_APPOINTMENT);
            ResultSet results = statement.executeQuery(QUERY_GET_ALL_APPOINTMENT);
            while(results.next()) {
                appointmentID = results.getInt(1);
                customerID = results.getInt(2);
                consultantID = results.getInt(3);
                appointmentTitle = results.getString(4);
                appointmentDescription = results.getString(5);
                appointmentType = results.getString(6);
                appointmentStart = results.getString(7);
                appointmentEnd = results.getString(8);
                createdBy = results.getString(9);
                lastUpdateBy = results.getString(10);

                Appointment retrievedAppointment = new Appointment(
                        customerID,
                        consultantID,
                        appointmentTitle,
                        appointmentDescription,
                        appointmentType,
                        parseSqlTime(appointmentStart),
                        parseSqlTime(appointmentEnd),
                        createdBy,
                        lastUpdateBy
                );
                retrievedAppointment.set_id(appointmentID);
                generatedAppointments.add(retrievedAppointment);
            }
            return generatedAppointments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> getConsultantAppointments(int consultantID) {
        List<Appointment> allAppointments = getAllAppointments();
        List<Appointment> filteredAppointments = new ArrayList<>();
        allAppointments
                .stream()
                .filter(c->(c.getConsultantID()) == consultantID)
                .forEach(filteredAppointments::add);
//        TODO Use collections filters
        return filteredAppointments;
    }

    @Override
    public ZonedDateTime parseSqlTime(String time) {
        ZonedDateTime converted = LocalDateTime.parse(time, dateTimeFormatter)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault());
        return converted;
    }
    @Override
    public String convertToSqlTime(ZonedDateTime dateTimeObject) {
        ZonedDateTime utcZoned = dateTimeObject.withZoneSameInstant(ZoneOffset.UTC);
        String convertedTime = dateTimeFormatter.format(utcZoned);
        return convertedTime;
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
