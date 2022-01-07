package model;

import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Data {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**SQL query to add apt from add apt screen*/
    public static void addAppointment(Appointment appointment) throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES ("
                + appointment.getId() + ", '"
                + appointment.getTitle() + "', '" + appointment.getDescription() + "', '"
                + appointment.getLocation() + "', '" + appointment.getType() + "', '" + appointment.getStart() + "', '" + appointment.getEnd() + "', " + appointment.getCustId() + "," + appointment.getUserId() + "," + appointment.getContact() + ");";
        stm.executeUpdate(query);
    }
    /**SQL query to remove apt from main menu screen*/
    public static void removeAppointment(Appointment appointment)throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "DELETE FROM appointments WHERE Appointment_ID=" + appointment.getId() + ";";
        stm.executeUpdate(query);
    }
    /**SQL query to add customer*/
    public static void addCustomer(Customer customer) throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES ("
                + customer.getId() + ", '"
                + customer.getName() + "', '" + customer.getAddress() + "', '"
                + customer.getPostalCode() + "', '" + customer.getPhone() + "',"
                + customer.getCity() + ");";
        stm.executeUpdate(query);
    }
    /**SQL query to remove customer*/
    public static void removeCustomer(Customer customer)throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "DELETE FROM customers WHERE Customer_ID=" + customer.getId() + ";";
        stm.executeUpdate(query);
    }
    /**SQL query to add all apts to an observable list for use on main menu*/
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
    /**SQL query to return all apts by current month*/
    public static ObservableList<Appointment> getMonthAppointments() throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "select * from appointments\n" +
                "where MONTH(start)=MONTH(now())\n" +
                "and YEAR(start)=YEAR(now());";
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
    /**SQL query to return all apts by current week*/
    public static ObservableList<Appointment> getWeekAppointments() throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "select * from appointments\n" +
                "where WEEK(start)=WEEK(now())\n" +
                "and YEAR(start)=YEAR(now());";
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
    /**SQL query to return all customers in database for use in main menu table*/
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
    /**SQL query to check a customer has existing apts before deletion*/
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
    /**SQL query to return list of country Ids*/
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
    /**SQL query to return country divisions*/
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
    /**SQL query to return all contacts*/
    public static ObservableList<String> getContacts() throws SQLException {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM contacts;";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            emptyList.add(rs.getString("Contact_Name"));
        }
        stm.close();
        return emptyList;
    }


    public static ObservableList<String> getDiv(int selection) throws SQLException {
        String div = "";
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM first_level_divisions WHERE Division_ID=" + selection + ";";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            div = rs.getString("Division");
        }
        emptyList.add(div);
        stm.close();
        return emptyList;
    }
    /**SQL query to return the associated country with each div Id*/
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
    /**SQL query to return divisions by division Id*/
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

    public static Appointment appointment15MinAlert() {
        Appointment appointment;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zid);
        LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime ldt2 = ldt.plusMinutes(15);
        int user = Login.returnUser();
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN '" + ldt + "' AND '" + ldt2 + "' AND " +
                    "contact=" + user + ";";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Contact_ID"),
                        rs.getString("Type"),
                        rs.getString("Start"),
                        rs.getString("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                return appointment;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    public static ObservableList<Appointment> getContactsReports(int selection) throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM appointments WHERE contact_ID=" + selection + ";";
        ResultSet rs = stm.executeQuery(query);
        if(rs.next()) {
            Appointment appointment;
            appointment = new Appointment(rs.getInt("Appointment_ID"),
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

}