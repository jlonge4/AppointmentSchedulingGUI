package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Data;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class AddAppointment implements Initializable {
    public TextField appointmentId;
    public TextField userId;
    public TextField title;
    public TextField description;
    public TextField location;
    public TextField type;
    public ComboBox contact;
    public TextField customerId;
    public DatePicker start;
    public DatePicker end;
    public ComboBox hoursOne;
    public ComboBox minutesTwo;
    public ComboBox hoursTwo;
    public ComboBox minutesOne;
    private ObservableList<String> contacts = FXCollections.observableArrayList();
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        hoursOne.setItems(hours);
        hoursTwo.setItems(hours);
        minutesOne.setItems(minutes);
        minutesTwo.setItems(minutes);
        start.setOnAction(event -> {
            LocalDate date = start.getValue();
            System.out.println("Selected date: " + date);
        });
        end.setOnAction(event -> {
            LocalDate date = end.getValue();
            System.out.println("Selected date: " + date);
        });

        userId.setText(String.valueOf(Login.returnUser()));
        int id;
        Customer customer;
        customer = MainMenu.getSelectedAptCustomer();
        customerId.setText(String.valueOf(customer.getId()));
        try {
            for(String s: Data.getContacts()) {
                contacts.add(s);
            }
            contact.setItems(contacts);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    /**Increase Count for Random iD*/
    public int increaseCount(int count) {
        count ++;
        return count;
    }
    /**provide random id for new apt*/
    public int randomId() throws SQLException {
        AtomicInteger randomId = new AtomicInteger(increaseCount(Data.getAllAppointments().size()));
        Data.getAllAppointments().forEach((item) -> {
            if (item.getId() == randomId.get()) {
                randomId.addAndGet(1);
            };
        });
        return randomId.get();
    }
    /**navigate back to main menu*/
    public void MainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    /**save new apt*/
    public void onAppointmentSave(ActionEvent event) throws SQLException, IOException, NullPointerException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
        int appointmentId = randomId();
        String titleApt = "";
        String descriptionApt = null;
        String locationApt = null;
        String contactApt = null;
        String typeApt = null;
        String startApt = null;
        String endApt = null;
        int custIdApt = 0;
        int userIdApt = Login.returnUser();
        try {
            titleApt = title.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment title text field");
            alert.showAndWait();
        }
        try {
            descriptionApt = description.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment description text field");
            alert.showAndWait();
        }
        try {
            locationApt = location.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment location text field");
            alert.showAndWait();
        }
        try {
            contactApt = String.valueOf(contact.getSelectionModel().getSelectedIndex() + 1);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment contact field");
            alert.showAndWait();
        }
        try {
            typeApt = type.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment type text field");
            alert.showAndWait();
        }
        try {
            descriptionApt = description.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment description text field");
            alert.showAndWait();
        }
        try {
            String minuteOne =  String.valueOf(minutesOne.getSelectionModel().getSelectedItem());
            LocalDateTime date = start.getValue().atTime(hoursOne.getSelectionModel().getSelectedIndex(), Integer.parseInt(minuteOne));
            startApt = date.format(formatter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the start date and time fields");
            alert.showAndWait();
        }
        try {
            String minuteTwo =  String.valueOf(minutesTwo.getSelectionModel().getSelectedItem());
            LocalDateTime date = end.getValue().atTime(hoursTwo.getSelectionModel().getSelectedIndex(), Integer.parseInt(minuteTwo));
            endApt = date.format(formatter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the end date and time fields");
            alert.showAndWait();
        }
        try {
            custIdApt = Integer.parseInt(customerId.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the appointment customer ID text field");
            alert.showAndWait();
        }
        try {
            userIdApt = Integer.parseInt(userId.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the apointment user ID text field");
            alert.showAndWait();
        }
        try {
            System.out.println(startApt);
        Appointment appointment = new Appointment(appointmentId, titleApt, descriptionApt, locationApt, contactApt, typeApt, startApt, endApt, custIdApt, userIdApt);
        if (validate(appointment)) {
            Data.addAppointment(appointment);
            MainMenu(event);
        }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("Invalid appointment");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("Invalid Appointment");
            alert.showAndWait();
        }

    }
    public boolean validate (Appointment appointment) {
        if (appointment.getId() == 0) {
            return false;
        } else if (appointment.getDescription() == "") {
            return false;
        } else if (appointment.getDescription() == "") {
            return false;
        } else if (appointment.getLocation() == "" ) {
            return false;
        } else if (appointment.getContact() == "" ) {
            return false;
        } else if (appointment.getType() == "") {
            return false;
        } else if (appointment.getStart() == "") {
            return false;
        } else if (appointment.getEnd() == "") {
            return false;
        } else if (appointment.getCustId() == 0) {
            return false;
        } else if (appointment.getUserId() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
