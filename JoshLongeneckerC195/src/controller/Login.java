package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public Button login;
    public TextField username;
    public TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("It works");

    }

    public void toMainMenu (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Appointment Screen");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void onLogin(ActionEvent event) throws IOException, SQLException {
        String user = username.getText();
        String pass = password.getText();
        boolean userExists = loginVerification(user, pass);
        if (userExists) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                ((Button)(event.getSource())).getScene().getWindow().hide();
        } else {
            System.out.println("Login Failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setContentText("Login Failed");
            alert.showAndWait();
        }
    }
    public static boolean loginVerification (String user, String pass) throws SQLException {
        Statement stm = JDBC.getConnection().createStatement();
        String query = "Select * \n" +
                "FROM users \n" +
                "WHERE User_Name='" + user + "'\n" +
                "AND Password='" + pass + "';";
        ResultSet rs = stm.executeQuery(query);
        if (rs.next()) {
            return true;
        }
        return false;
    }
}

