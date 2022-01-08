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
import model.Data;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {

    public TextField appointmentId;
    public TextField userId;
    public TextField title;
    public TextField description;
    public TextField location;
    public ComboBox contact;
    public ComboBox hoursOne;
    public ComboBox minutesTwo;
    public ComboBox hoursTwo;
    public ComboBox minutesOne;
    public DatePicker start;
    public DatePicker end;
    public TextField customerId;
    public TextField type;
    public ComboBox typeCombo;
    private Appointment appointmentNew;
    private static Appointment appointmentOld;
    private ObservableList<String> contacts = FXCollections.observableArrayList();
    ObservableList<String> types = FXCollections.observableArrayList("Planning Session" , "New Appointment");
    ObservableList<String> hours = FXCollections.observableArrayList("08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22");
    ObservableList<String> minutes = FXCollections.observableArrayList("00", "15", "30", "45");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.setItems(types);
        hoursOne.setItems(hours);
        hoursTwo.setItems(hours);
        minutesOne.setItems(minutes);
        minutesTwo.setItems(minutes);

        start.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        end.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        start.setOnAction(event -> {
            LocalDate date = start.getValue();
            System.out.println("Selected date: " + date);
        });
        end.setOnAction(event -> {
            LocalDate date = end.getValue();
            System.out.println("Selected date: " + date);
        });
        System.out.println("modify Apt");

        appointmentOld = MainMenu.getselectedAppointment();
        appointmentId.setText(String.valueOf(appointmentOld.getId()));
        userId.setText(String.valueOf(appointmentOld.getUserId()));
        title.setText(appointmentOld.getTitle());
        description.setText(appointmentOld.getDescription());
        location.setText(appointmentOld.getLocation());
        customerId.setText(String.valueOf(appointmentOld.getCustId()));
        typeCombo.setValue(appointmentOld.getType());

        String dateTimeStart = appointmentOld.getStart();
        dateTimeStart = dateTimeStart.substring(0,10);
        LocalDate ld = LocalDate.parse(dateTimeStart);
        start.setValue(ld);

        String hOneTimeStart = appointmentOld.getStart();
        hOneTimeStart = hOneTimeStart.substring(11,13);
        hoursOne.setValue(hOneTimeStart);

        String mOneTimeStart = appointmentOld.getStart();
        mOneTimeStart = mOneTimeStart.substring(14,16);
        minutesOne.setValue(mOneTimeStart);

        String dateTimeEnd = appointmentOld.getEnd();
        dateTimeEnd = dateTimeEnd.substring(0,10);
        LocalDate ldE = LocalDate.parse(dateTimeEnd);
        end.setValue(ldE);

        String hOneTimeEnd = appointmentOld.getEnd();
        hOneTimeEnd = hOneTimeEnd.substring(11,13);
        hoursTwo.setValue(hOneTimeEnd);

        String mOneTimeEnd = appointmentOld.getStart();
        mOneTimeEnd = mOneTimeEnd.substring(14,16);
        minutesTwo.setValue(mOneTimeEnd);

        try {
            for(String s: Data.getContacts()) {
                contacts.add(s);
            }
            contact.setItems(contacts);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            contact.getSelectionModel().select(Data.getContactsByID(appointmentOld.getContact()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    /**saves modified apt*/
    public void onAppointmentSave(ActionEvent event) throws SQLException, IOException, NullPointerException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
        int appointmentIdNew = Integer.parseInt(appointmentId.getText());
        String titleApt = "";
        String descriptionApt = "";
        String locationApt = "";
        String contactApt = "";
        String typeApt = "";
        String startApt = "";
        String endApt = "";
        int custIdApt = 0;
        int userIdApt = Login.returnUser();

        try {
            titleApt = title.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the title text field");
            alert.showAndWait();
        }
        try {
            descriptionApt = description.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the description text field");
            alert.showAndWait();
        }
        try {
            locationApt = location.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the location text field");
            alert.showAndWait();
        }
        try {
            contactApt = String.valueOf(contact.getSelectionModel().getSelectedIndex() + 1);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the contact field");
            alert.showAndWait();
        }
        try {
            typeApt = (String) typeCombo.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the type field");
            alert.showAndWait();
        }
        try {
            descriptionApt = description.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the description text field");
            alert.showAndWait();
        }
        try {
            String minuteOne =  String.valueOf(minutesOne.getSelectionModel().getSelectedItem());
            String hourOne =  String.valueOf(hoursOne.getSelectionModel().getSelectedItem());
            LocalDateTime date = start.getValue().atTime(Integer.parseInt(hourOne), Integer.parseInt(minuteOne));
            startApt = date.format(formatter);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the start date and time fields");
            alert.showAndWait();
        }
        try {
            String minuteTwo =  String.valueOf(minutesTwo.getSelectionModel().getSelectedItem());
            String hourTwo = String.valueOf(hoursTwo.getSelectionModel().getSelectedItem());
            LocalDateTime date = end.getValue().atTime(Integer.parseInt(hourTwo), Integer.parseInt(minuteTwo));
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
            alert.setContentText("please fill in or correct the customer ID text field");
            alert.showAndWait();
        }
        try {
            userIdApt = Integer.parseInt(userId.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the user ID text field");
            alert.showAndWait();
        }
        try {
            System.out.println(startApt);
            appointmentNew = new Appointment(appointmentIdNew, titleApt, descriptionApt, locationApt, contactApt, typeApt, startApt, endApt, custIdApt, userIdApt);
            if (AddAppointment.validate(appointmentNew)) {
                System.out.println(appointmentIdNew + titleApt + descriptionApt + locationApt + contactApt + typeApt + startApt + endApt + custIdApt + userIdApt);
                    Data.removeAppointment(appointmentOld);
                    Data.addAppointment(appointmentNew);
                    MainMenu(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Failed");
                alert.setContentText("please fill in or correct the appointment text fields");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("Invalid Appointmentr");
            alert.showAndWait();
        }
    }
}
