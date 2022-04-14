package app.unedl.controllers;

import app.unedl.utils.AppLogger;
import app.unedl.utils.ConexionBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SceneViewController {

    @FXML
    private TextField nombre_campo;
    @FXML
    private TextField apellido1_campo;
    @FXML
    private TextField apellido2_campo;
    @FXML
    private TextField email_campo;

    Connection conexion = ConexionBD.obtenerConexion();

    public void initialize(){

    }

    public void onLimpiarFormulario(ActionEvent e) {
//        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando MiembroUNEDL..");
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "limpiando formulario...");
    }

    public void onCrearAlumno(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando alumno...");

        //region crear alumno

        /*obtener datos de la vista*/
        String nombre = nombre_campo.getText();
        String apellido1 = apellido1_campo.getText();
        String apellido2 = apellido2_campo.getText();
        String email = email_campo.getText();

        /*si caulquier campo esta vacio lanzar advertencia*/
        if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || email.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No puede haber campos vacios!\nAsegurese de llenar todos los campos! :)");
            alert.show();
        } else {
            /*si no hay campos vacios -> ejecuar sentencia sql*/
            //TODO: concatenar sentencia sql en logger
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL: ");
            System.out.println("nombre: " + nombre);
            System.out.println("apellido1: " + apellido1);
            System.out.println("apellido2: " + apellido2);
            System.out.println("email: " + email);
        }

        //crear y ejecutar sentencia
        try {
            Statement statement = conexion.createStatement();
//            statement.execute("INSERT INTO Estudiante VALUES(2, 'Madrazo', 'Gonzales', 'madrazo.gonzales@gmail.com')");

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