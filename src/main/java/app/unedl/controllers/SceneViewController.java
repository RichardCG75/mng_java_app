package app.unedl.controllers;

import app.unedl.utils.AppLogger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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