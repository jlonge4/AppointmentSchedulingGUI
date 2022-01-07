package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("modify Apt");
        Appointment appointment;
        appointment = MainMenu.getselectedAppointment();
        appointmentId.setText(String.valueOf(appointment.getId()));
        userId.setText(String.valueOf(appointment.getUserId()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        location.setText(appointment.getLocation());

    }

    public void MainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
