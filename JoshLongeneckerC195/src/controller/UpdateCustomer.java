package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Data;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable {

    public TextField customerId;
    public TextField customerName;
    public TextField customerAddress;
    public TextField customerPostalCode;
    public TextField customerPhone;
    private static Customer customerOld;
    public ComboBox customerState;
    public ComboBox customerCountry;
    private Customer customerNew;
    public ObservableList<String> countries = FXCollections.observableArrayList("US" , "UK" , "CA");



    /**initialize update customer screen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Update Customer");

        customerOld = MainMenu.getSelectedCustomer();
        customerCountry.setItems(countries);
        try {
            customerCountry.getSelectionModel().select(Data.getCountry(customerOld.getCity()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        customerId.setText(Integer.toString(customerOld.getId()));
        customerName.setText(customerOld.getName());
        customerAddress.setText(customerOld.getAddress());
        customerPostalCode.setText(customerOld.getPostalCode());
        customerPhone.setText(customerOld.getPhone());
        try {
            if (customerCountry.getSelectionModel().getSelectedItem().equals("US")) {
                customerState.setItems(Data.getList(1));
            } else if (customerCountry.getSelectionModel().getSelectedItem().equals("UK")) {
                customerState.setItems(Data.getList(2));
            } else {
                customerState.setItems(Data.getList(3));
            }
            customerState.getSelectionModel().select(Data.getState(customerOld.getCity()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    /**sets first level div combo content*/
    public void selectComboContent(ActionEvent event) throws SQLException {
        if (customerCountry.getSelectionModel().getSelectedItem().equals("US")) {
            customerState.setItems(Data.getList(1));
        } else if (customerCountry.getSelectionModel().getSelectedItem().equals("UK")) {
            customerState.setItems(Data.getList(2));
        } else {
            customerState.setItems(Data.getList(3));
        }
    }
    /**updates the new customer*/
    public void onSave (ActionEvent actionEvent) throws IOException, SQLException {
        int id = customerOld.getId();
        String name = "";
        String address = "";
        String postalCode = "";
        String phone = "";
        int city = 0;

        try {
            name = customerName.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the customer name text field");
            alert.showAndWait();
        }
        try {
            address = customerAddress.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the customer address text field");
            alert.showAndWait();
        }
        try {
            postalCode = customerPostalCode.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the customer postal code text field");
            alert.showAndWait();
        }
        try {
            phone = customerPhone.getText();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the customer name text field");
            alert.showAndWait();
        }
        try {
            city = Data.getDivId(customerState.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the customer name text field");
            alert.showAndWait();
        }

        customerNew = new Customer(id,name,address,postalCode,phone,city);
        Data.removeCustomer(customerOld);
        Data.addCustomer(customerNew);
        MainMenu(actionEvent);
    }
    /**navigates back to the main menu*/
    public void MainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
