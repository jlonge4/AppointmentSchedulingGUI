package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Customer");
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

    public void onCustomerSave(ActionEvent event) throws SQLException, IOException {
        System.out.println("Saved customer");
        int id = randomId();
        String name = "";
        String address = "";
        String postalCode = "";
        String phone = "";
        int city = 22;

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

        Customer customer = new Customer(id,name,address,postalCode,phone,city);
//        try {
            Data.addCustomer(customer);
//            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
//            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root, 1200, 500);
//            stage.setTitle("Main Menu");
//            stage.setScene(scene);
//            stage.show();
            MainMenu(event);
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Add Failed");
//            alert.setContentText("Invalid Customer");
//            alert.showAndWait();
//        }


    }

}
