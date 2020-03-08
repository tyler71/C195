package dataModel;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.List;

public class DataSourceSourceMySql implements IDataSource {
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/C195";

    public static final String TABLE_USER = "user";
    public static final String USER_COLUMN_ID = "userId";
    public static final String USER_COLUMN_USERNAME = "userName";
    public static final String USER_COLUMN_PASSWORD = "password";

    public static final String GET_CONSULTANT_QUERY_START = String.format(
            "SELECT %s, %s "
            + "FROM %s WHERE %s =",
            USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD,
            TABLE_USER, USER_COLUMN_ID);

    public static final String VALIDATE_LOGIN_QUERY_START = String.format(
            "SELECT %s, %s FROM %s",
            USER_COLUMN_USERNAME, USER_COLUMN_PASSWORD, TABLE_USER);

    private Connection conn;

    @Override
    public boolean openConnection() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean closeConnection() {
        try {
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
        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(GET_CONSULTANT_QUERY_START)) {
            int indexConsultantID = 1;
            int indexConsultantUsername = 2;
            int indexConsultantPassword = 3;
            Consultant consultant = new Consultant(results.getInt(indexConsultantID),
                    results.getString(indexConsultantUsername), results.getString(indexConsultantPassword));
            return consultant;
        } catch (SQLException e) {
            System.out.println("Unable to find Consultant");
            return null;
        }
    }

    @Override
    public boolean validateLogin(String userName, String password) {
        try(Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(VALIDATE_LOGIN_QUERY_START)) {
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
    public int addCustomer(String name, String address, String phoneNumber) {
        return 0;
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
