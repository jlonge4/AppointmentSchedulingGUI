package controller;

import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.JDBC;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login implements Initializable {

    public Button login;
    public TextField username;
    public TextField password;
    public Label location;
    public Button user;
    public Button pass;
    public Label signIn;
    private String loginFailed;
    public static int userInt = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("It works");
        Locale locate = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("languageFiles/rb" , locate);
        location.setText(String.valueOf(locate));
        user.setText(rb.getString("username"));
        pass.setText(rb.getString("password"));
        login.setText(rb.getString("login"));
        signIn.setText(rb.getString("signin"));
        loginFailed=(rb.getString("loginFailed"));

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
                logLoginAttempt(true);
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                ((Button)(event.getSource())).getScene().getWindow().hide();
        } else {
            logLoginAttempt(false);
            System.out.println("Login Failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(loginFailed);
            alert.setContentText(loginFailed);
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
    private void logLoginAttempt(boolean success) {
        final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        final String time = dtf.format(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        final String user = username.getText();
        final String pass = password.getText();
        try {
            final FileWriter fw = new FileWriter("login_activity.txt", true);
            final BufferedWriter bw = new BufferedWriter(fw);
                if (success) {
                    bw.write("Login Successful: " + "| UserName: " + user + "| PassWord: " + pass + "| time: " + time);
                    bw.newLine();
                    bw.close();
                } else {
                    bw.write("Login Failed: " + "| UserName: " + user + "| PassWord: " + pass + "| time: " + time);
                    bw.newLine();
                    bw.close();
                }
        } catch (IOException ex) {
            System.out.println("Failed to write login attempt");
            System.out.println(ex.getMessage());
        }
    }
    public void getUser(){
        if (username.getText().equals("test")) {
            userInt = 1;
        }
        userInt = 2;
    }

    public static int returnUser(){
        return userInt;
    }
}

