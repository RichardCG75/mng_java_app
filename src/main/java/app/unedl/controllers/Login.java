package app.unedl.controllers;

import app.unedl.utils.AppLogger;
import app.unedl.utils.DBConenction;
import app.unedl.views.UnedlApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    @FXML TextField usr_field;
    @FXML PasswordField pass_field;

    public void initialize(){
    }

    public void onLoginClicked(){

        if (verifyLogin()) {
            Notifications.create().text("Logged In").show();
            setAdminScene();
        }
        else {
            Notifications.create().title("Login failed").text("Wrong user or password").show();
        }
    }

    private boolean verifyLogin(){

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Verifying login...");
        String user = usr_field.getText();
        String pass = pass_field.getText();

        try{

            if (user != null && pass != null){

                //prepare statement
                String sql = "SELECT * FROM admins WHERE user=? and pass=?";
                PreparedStatement statement = DBConenction.getConnection().prepareStatement(sql);
                statement.setString(1, user);
                statement.setString(2, pass);

                //execute & validate
                AppLogger.LOGGER.log(System.Logger.Level.INFO, "Executing query " + statement);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }

        }
        catch (Exception e){
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Failed executing query");
            e.printStackTrace();
        }
        return false;
    }

    private void setAdminScene(){

        try {
            Stage stage = (Stage) usr_field.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(UnedlApp.class.getResource("admin-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("UNEDL Manager!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
