package app.unedl.controllers;

import app.unedl.utils.AppLogger;
import app.unedl.utils.ConnecionBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SceneViewController {

    @FXML private BorderPane sceneArea;
    @FXML private TextArea textArea;
    @FXML private HBox topArea;
    @FXML private HBox bottonArea;
    @FXML private VBox leftArea;
    @FXML private VBox rightArea;



    public void initialize(){

    }

    public void onLimpiarFormulario(ActionEvent e) {
//        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando MiembroUNEDL..");
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "limpiando formulario...");
    }

    public void onCrearAlumno(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando alumno...");

        //region database connection
        String jdbcURL = "jdbc:mysql://54.215.148.52:3306/sql3485197";
        String user = "sql3485197";
        String pass = "RXHirfk9vP";

        ConnecionBD connecionBD = new ConnecionBD(jdbcURL, user, pass);
        Connection connection = connecionBD.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO Estudiante VALUES(2, 'Madrazo', 'Gonzales', 'madrazo.gonzales@gmail.com')");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //endregion

    }

    public void onLimpiarAlumno(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "limpiando alumno...");
    }

    public void onCrearProfesor(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando profesor...");
    }

    public void onLimpiarProfesor(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "limpiando profesor...");
    }

    public void onCrearCoordinador(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando coordinador...");
    }

    public void onLimpiarCoordinador(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "limpiando coordinador...");
    }
}