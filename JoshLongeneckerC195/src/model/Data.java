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
                    rs.getString("Phone"));
            emptyList.add(customer);
        }
        stm.close();
        return emptyList;
    }
}