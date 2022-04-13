package app.unedl.controllers;

import app.unedl.utils.AppLogger;
import app.unedl.utils.ConexionBD;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SceneViewController {

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

        //obtener datos de la vista


        //crear y ejecutar sentencia
        try {
            Statement statement = conexion.createStatement();
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