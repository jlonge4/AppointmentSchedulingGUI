package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String start;
    private String end;
    private int custId;
    private int userId;
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Appointment(int id, String title, String description, String location, String contact, String type, String start, String end, int custId, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.custId = custId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

//    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
//        Statement stm = JDBC.getConnection().createStatement();
//        String query = "Select * FROM appointments;";
//        ResultSet rs = stm.executeQuery(query);
//        while(rs.next()) {
//            Appointment appointment = new Appointment (
//                    rs.getInt("Appointment_ID"),
//                    rs.getString("Title"),
//                    rs.getString("Description"),
//                    rs.getString("Location"),
//                    rs.getString("Contact_ID"),
//                    rs.getString("Type"),
//                    rs.getString("Start"),
//                    rs.getString("End"),
//                    rs.getInt("Customer_ID"),
//                    rs.getInt("User_ID"));
//            allAppointments.setAll(appointment);
//        }
//        return allAppointments;
//    }
}
