package controller;

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
import model.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GenerateReports implements Initializable {

    public ComboBox contacts;
    public TableView AppointmentTableTwo;
    public TableColumn aptIDT;
    public TableColumn titleT;
    public TableColumn descriptionT;
    public TableColumn locationT;
    public TableColumn contactT;
    public TableColumn typeT;
    public TableColumn startT;
    public TableColumn endT;
    public TableColumn custIDT;
    public TableColumn userIDT;
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
    public TableColumn customerID;
    public TableColumn name;
    public TableColumn address;
    public TableColumn postalCode;
    public TableColumn phone;
    public TableColumn divId;
    public RadioButton month;
    public RadioButton typeRadio;
    public RadioButton scheduled;
    public RadioButton nonScheduled;
    private ObservableList<String> contactsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contacts.setItems(Data.getContacts());
            AppointmentTableTwo.setItems(Data.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Generate Reports");
        /**apt table 1**/
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
        /**apt table 2**/
        aptIDT.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleT.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionT.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationT.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactT.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeT.setCellValueFactory(new PropertyValueFactory<>("type"));
        startT.setCellValueFactory(new PropertyValueFactory<>("start"));
        endT.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIDT.setCellValueFactory(new PropertyValueFactory<>("custId"));
        userIDT.setCellValueFactory(new PropertyValueFactory<>("userId"));
        /**customers table**/
        customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divId.setCellValueFactory(new PropertyValueFactory<>("city"));

        try {
            setCustomerAptTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void MainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    public void setContactsTable () throws SQLException {
        ObservableList<Appointment> emptyList = FXCollections.observableArrayList();
        int selection = contacts.getSelectionModel().getSelectedIndex() + 1;
        emptyList = Data.getContactsReports(selection);
        AppointmentTableTwo.setItems(emptyList);
    }

    public void setAptByMonthTable () throws SQLException {
        if(month.isSelected()) {
            AppointmentTable.setItems(Data.getMonthAppointments());
        } else if (typeRadio.isSelected()) {
            System.out.println("I giver up");
        }
    }

    public void setCustomerAptTable() throws SQLException {
        if (scheduled.isSelected()) {
            CustomersTable.setItems(Data.customerWithApts(true));
        } else if (nonScheduled.isSelected()) {
            CustomersTable.setItems(Data.customerWithApts(false));
        } else {
            CustomersTable.setItems(Data.getAllCustomers());
        }
    }

}
