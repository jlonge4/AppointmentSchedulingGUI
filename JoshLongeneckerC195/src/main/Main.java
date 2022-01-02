package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale locate = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("languageFiles/rb" , locate);
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle(rb.getString("signin"));
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }

    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}//
