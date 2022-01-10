package model;

import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import model.Appointment;

public class Data {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**SQL query to add apt from add apt screen*/
    public static void addAppointment(Appointment appointment) throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES ("
                + appointment.getId() + ", '"
                + appointment.getTitle() + "', '" + appointment.getDescription() + "', '"
                + appointment.getLocation() + "', '" + appointment.getType() + "', '" + Timestamp.valueOf(appointment.getStart()) + "', '" + Timestamp.valueOf(appointment.getEnd()) + "', " + appointment.getCustId() + "," + appointment.getUserId() + "," + appointment.getContact() + ");";
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
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
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
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
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
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
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

    public static String getContactsByID(String selection) throws SQLException {
        ObservableList<String> emptyList = FXCollections.observableArrayList();
        String string = "";
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM contacts WHERE Contact_ID=" + selection + ";";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            string = rs.getString("Contact_Name");
        }
        stm.close();
        return string;
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
    /**method to prompt 15 min apt alert*/
    public static Appointment appointment15MinAlert() {
        Appointment appointment;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zid);
        LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        ldt = ldt.truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime ldt2 = ldt.plusMinutes(15);
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT * FROM appointments WHERE start BETWEEN '" + ldt + "' AND '" + ldt2 + "';";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                appointment = new Appointment(rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Contact_ID"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                return appointment;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }
    /**method to populate report for apts by contact*/
    public static ObservableList<Appointment> getContactsReports(int selection) throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM appointments WHERE Contact_ID=" + selection + ";";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            Appointment appointment;
            appointment = new Appointment(rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Contact_ID"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"));
            emptyList.add(appointment);
        }
        stm.close();
        System.out.println(emptyList.size());
        return emptyList;
    }
    /**lambda expression to add customer Ids from scheduled apts into a set to ensure no redundant data, used later to compare customer Ids to all customers Ids*/
    /**lambda expression to remove scheduled customers from all customers list, leaving only non schedulded customers*/
    public static ObservableList<Customer> customerWithApts(boolean scheduled) throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        ObservableList<Customer> emptyListA = FXCollections.observableArrayList();
        ObservableList<Customer> emptyListB = FXCollections.observableArrayList();
        Set<Integer> set = new HashSet<>();
        emptyList = Data.getAllAppointments();
        emptyListB = Data.getAllCustomers();
        /**lambda expression to add customer Ids from scheduled apts into a set to ensure no redundant data, used later to compare customer Ids to all customers Ids*/
        emptyList.forEach( (a -> {  set.add(a.getCustId()); }));
        for (int i : set) {
            for(Customer c : emptyListB) {
                if (c.getId() == i) {
                    emptyListA.add(c);
                }
            }
        }
        if (scheduled) {
            return emptyListA;
        } else {
            ObservableList<Customer> finalEmptyListB = emptyListB;
            /**lambda expression to remove scheduled customers from all customers list, leaving only non schedulded customers*/
            emptyListA.forEach( (c) -> { finalEmptyListB.remove(c); } );
          return emptyListB;
        }

    }
    /**filter apts by type*/
    public static ObservableList<Appointment> filterAptType(String selection, String month) throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM appointments WHERE monthname(Start)='" + month + "' AND Type='" + selection + "';";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            Appointment appointment = new Appointment (
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Contact_ID"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"));
            emptyList.add(appointment);
        }
        stm.close();
        return emptyList;
    }
    /**validate created or modified apt overlap*/
    public static boolean validateOverlap(Appointment b) throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        Statement stm = JDBC.getConnection().createStatement();
        String query = "SELECT * FROM appointments;";
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()) {
            Appointment appointment = new Appointment (
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Contact_ID"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"));
            emptyList.add(appointment);
        }
        stm.close();
        boolean valid = false;
        int One = 12;
        int Two = 1;
        int Three = 5;
        for (Appointment a : emptyList) {
            LocalDateTime bStart = b.getStart();
            ZonedDateTime zdtOut = bStart.atZone(ZoneId.of("UTC"));
            ZonedDateTime zdtOutToLocalTZ = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDateTime ldtOutFinal = zdtOutToLocalTZ.toLocalDateTime();
            One = Integer.parseInt(a.getContact());
            Two = Integer.parseInt(b.getContact());
            Three = One - Two;

            if (ldtOutFinal.isAfter(a.getStart()) && ldtOutFinal.isBefore(a.getEnd()) && Three==0 ) {
                valid = false;
            } else {
                valid = true;
            }
        }
        return valid;
    }

}