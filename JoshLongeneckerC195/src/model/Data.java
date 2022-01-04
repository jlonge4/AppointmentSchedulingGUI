package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();


    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
    }

    public static void removeAppointment(Appointment appointment)throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "DELETE FROM appointments WHERE Appointment_ID=" + appointment.getId() + ";";
        stm.executeUpdate(query);
    }

    public static void addCustomer(Customer customer) throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES ("
                + customer.getId() + ", '"
                + customer.getName() + "', '" + customer.getAddress() + "', '"
                + customer.getPostalCode() + "', '" + customer.getPhone() + "',"
                + customer.getCity() + ");";
        stm.executeUpdate(query);
    }
    public static void removeCustomer(Customer customer)throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "DELETE FROM customers WHERE Customer_ID=" + customer.getId() + ";";
        stm.executeUpdate(query);
    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "Select * FROM appointments;";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            Appointment appointment = new Appointment (
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Contact_ID"),
                    rs.getString("Type"),
                    rs.getString("Start"),
                    rs.getString("End"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"));
            emptyList.add(appointment);
        }
        stm.close();
        return emptyList;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "Select * FROM customers;";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            Customer customer = new Customer (
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID"));
            emptyList.add(customer);
        }
        stm.close();
        return emptyList;
    }

    public static boolean validateCustomerDelete(Customer customer) throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * \n" +
                "FROM appointments\n" +
                "WHERE Customer_ID=" + customer.getId() + ";";
        ResultSet rs = stm.executeQuery(query);
        if (rs.next()) {
            return false;
        }
        return true;
    }

    public static ObservableList<String> getList(int selection) throws SQLException {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM first_level_divisions WHERE Country_ID=" + selection + ";";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            emptyList.add(rs.getString("Division"));
        }
        stm.close();
        return emptyList;
    }

    public static int getDivId(Object selection) throws SQLException {
        int divId = 0;
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM first_level_divisions WHERE Division='" + selection + "';";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            divId = rs.getInt("Division_ID");
        }
        stm.close();
        return divId;
    }

//    public static ObservableList<String> getDiv(int selection) throws SQLException {
//        String div = "";
//        ObservableList<String> emptyList = FXCollections.observableArrayList();
//        Statement stm = JDBC.getConnection().createStatement();
//        String query = "SELECT * FROM first_level_divisions WHERE Division_ID=" + selection + ";";
//        ResultSet rs = stm.executeQuery(query);
//        while(rs.next()) {
//            div = rs.getString("Division");
//        }
//        emptyList.add(div);
//        stm.close();
//        return emptyList;
//    }

    public static String getCountry(int selection) throws SQLException {
        String div = "";
        int number = 0;
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID=" + selection +";";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            number = rs.getInt("Country_ID");
        }
        if (number == 1) {
            div = "US";
        } else if (number == 2) {
            div = "UK";
        } else {
            div = "CA";;
        }
        emptyList.add(div);
        stm.close();
        return div;
    }

    public static String getState(int selection) throws SQLException {
        String div = "";
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT Division FROM first_level_divisions WHERE Division_ID=" + selection +";";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            div = rs.getString("Division");
        }
        stm.close();
        return div;
    }

}