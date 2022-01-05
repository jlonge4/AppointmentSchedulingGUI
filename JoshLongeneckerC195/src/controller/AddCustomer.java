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
import java.util.concurrent.atomic.AtomicInteger;

public class AddCustomer implements Initializable {

    public TextField customerId;
    public TextField customerName;
    public TextField customerAddress;
    public TextField customerPostalCode;
    public TextField customerPhone;
    public Customer customer;
    public ComboBox customerState;
    public ComboBox customerCountry;
    public ObservableList<String> countries = FXCollections.observableArrayList("US" , "UK" , "CA");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Customer");
        customerCountry.setItems(countries);
        Customer customer = null;
        try {
            customer = new Customer(randomId(),"name","address","postalCode","phone", 1234);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(customer.getId());
        
    }

    public void MainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public int increaseCount(int count) {
        count ++;
        return count;
    }

    public int randomId() throws SQLException {
        AtomicInteger randomId = new AtomicInteger(increaseCount(Data.getAllCustomers().size()));
        Data.getAllCustomers().forEach((item) -> {
            if (item.getId() == randomId.get()) {
                randomId.addAndGet(1);
            };
        });
        return randomId.get();
    }

    public void selectComboContent(ActionEvent event) throws SQLException {
        if (customerCountry.getSelectionModel().getSelectedItem().equals("US")) {
            customerState.setItems(Data.getList(1));
        } else if (customerCountry.getSelectionModel().getSelectedItem().equals("UK")) {
            customerState.setItems(Data.getList(2));
        } else {
            customerState.setItems(Data.getList(3));
        }
    }

    public void onCustomerSave(ActionEvent event) throws SQLException, IOException {
        System.out.println("Saved customer");
        int id = randomId();
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
            address = customerAddress.getText() ;
//                    + ", " +
//                    customerState.getSelectionModel().getSelectedItem() + ", " +
//                    customerCountry.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Failed");
            alert.setContentText("please fill in or correct the customer address, state/province, and country fields");
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

        Customer customer = new Customer(id,name,address,postalCode,phone,city);
            Data.addCustomer(customer);
            MainMenu(event);
    }


}
