package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.Data;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public TableColumn aptID;
    public TableColumn title;
    public TableColumn description;
    public TableColumn location;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn start;
    public TableColumn end;
    public TableColumn custID;
    public TableColumn userID;
    public TableView AppointmentTable;
    public TableView CustomersTable;
    public TableColumn name;
    public TableColumn address;
    public TableColumn postalCode;
    public TableColumn phone;
    public TableColumn customerID;
    private static Customer selectedCustomer = null;
    private static Customer selectedAptCustomer = null;
    public static int selectedAptCustomerId;
    private static Appointment selectedAppointment = null;
    public TableColumn divId;
    public RadioButton month;
    public RadioButton all;
    public RadioButton week;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("It works");

        AppointmentTable.getItems().clear();
        try {
            if(all.isSelected()) {
                AppointmentTable.setItems(Data.getAllAppointments());
            }
//            SELECT * FROM table WHERE date_column >= '2014-01-01' AND date_column <= '2015-01-01';
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            CustomersTable.getItems().clear();
        try{
            CustomersTable.setItems(Data.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        /**Appointment Table*/
        aptID.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        custID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        /**Customers Table*/
        customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divId.setCellValueFactory(new PropertyValueFactory<>("city"));

    }

    public void handleAptFilter () throws SQLException {
        AppointmentTable.getItems().clear();
        if(month.isSelected()) {
           AppointmentTable.setItems(Data.getMonthAppointments());
       } else if (week.isSelected()) {
           AppointmentTable.setItems(Data.getWeekAppointments());

       } else {
            AppointmentTable.setItems(Data.getAllAppointments());
        }
    }

    public void toLogin (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();
    }
    public void addCustomer (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("addCustomer");
        stage.setScene(scene);
        stage.show();
    }

    public void modifyAppointment (ActionEvent actionEvent) throws IOException {
        selectedAppointment= (Appointment) AppointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment!= null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 500);
            stage.setTitle("modify appointment");
            stage.setScene(scene);
            stage.show(); }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select an appointment to modify");
            alert.showAndWait();
        }
    }

    public static Appointment getselectedAppointment() { return selectedAppointment; }

    public void addAppointment (ActionEvent actionEvent) throws IOException, NullPointerException {
        selectedAptCustomer = (Customer) CustomersTable.getSelectionModel().getSelectedItem();
        if (selectedAptCustomer!=null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 500);
            stage.setTitle("addAppointment");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
        }

    }

    public void removeCustomer() throws SQLException {
        Customer customer;
        customer = (Customer) CustomersTable.getSelectionModel().getSelectedItem();
        if (customer!=null) {
            Alert alertA = new Alert(Alert.AlertType.ERROR);
            alertA.setTitle("Selection Error");
            alertA.setContentText("Are you sure you want to delete this customer?");
            alertA.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        if (Data.validateCustomerDelete(customer)) {
                            Data.removeCustomer(customer);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Selection Error");
                            alert.setContentText("Please verify customer has no appointments or select a customer");
                            alert.showAndWait();
                        }
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Selection Error");
                        alert.setContentText("Please verify customer has no appointments or select a customer");
                        alert.showAndWait();
                    }
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a customer to delete");
            alert.showAndWait();
        }
        CustomersTable.setItems(Data.getAllCustomers());
    }

    public void removeAppointment() throws SQLException {
        Appointment appointment;
        appointment = (Appointment) AppointmentTable.getSelectionModel().getSelectedItem();
        if (appointment!=null) {
            Alert alertA = new Alert(Alert.AlertType.ERROR);
            alertA.setTitle("Selection Error");
            alertA.setContentText("Are you sure you want to delete this appointment?");
            alertA.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        Data.removeAppointment(appointment);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Selection Error");
                        alert.setContentText("Please verify customer has no appointments or select a customer");
                        alert.showAndWait();
                    }
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select an appointment to delete");
            alert.showAndWait();
        }
        AppointmentTable.setItems(Data.getAllAppointments());
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public static Customer getSelectedAptCustomer() {
        return selectedAptCustomer;
    }

    public void updateCustomer (ActionEvent actionEvent) throws IOException {
        selectedCustomer = (Customer) CustomersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer!= null) {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("addCustomer");
        stage.setScene(scene);
        stage.show(); }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
        }
    }


}
