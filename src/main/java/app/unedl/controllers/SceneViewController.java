package app.unedl.controllers;

import app.unedl.utils.AppLogger;
import app.unedl.utils.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SceneViewController {

    Connection conexion = ConexionBD.obtenerConexion();

    @FXML private TextField nombre_campo;
    @FXML private TextField apellido1_campo;
    @FXML private TextField apellido2_campo;
    @FXML private TextField matricula_campo;
    @FXML private TextField cedula_campo;
    @FXML private TextField email_campo;
    @FXML private ChoiceBox<String> selector_tipo_izq;
    @FXML private ChoiceBox<String> selector_tipo_der;

    public void initialize(){

        //region selectores
        selector_tipo_izq.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        selector_tipo_izq.setValue("Estudiante");
        selector_tipo_der.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        selector_tipo_der.setValue("Estudiante");

        //iniciar lista de miembros desde base de datos
        ObservableList<String> miembros = FXCollections.observableArrayList();
        //endregion

    }

    public void onCambioMiembroCrear(ActionEvent e){
        System.out.println(selector_tipo_izq.getValue().toString());
        switch (selector_tipo_izq.getValue()){
            case "Estudiante": habilitarCamposEstudiante(); break;
            case  "Profesor": habilitarCamposProfesor(); break;
            case  "Coordinador": habilitarCamposCoordinador(); break;
            default: AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Miembro UNEDL no valido");
//            new Alert(Alert.AlertType.ERROR).setContentText("Miembro no valido");

        }
    }

    public void onCambioMiembroElimAct(ActionEvent e){

    }

    public void onCrear(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando alumno...");

        //region crear alumno

        /*obtener datos de la vista*/
        String nombre = nombre_campo.getText();
        String apellido1 = apellido1_campo.getText();
        String apellido2 = apellido2_campo.getText();
        String email =  email_campo.getText();

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

    public void onLimpiar(ActionEvent e){

    }

    public void onActualizar(ActionEvent e){

    }

    public void onEliminar(ActionEvent e){

    }

    //auxiliares (tareas repetitivas)
    private void habilitarCamposEstudiante(){
        this.matricula_campo.setDisable(false);
        this.cedula_campo.setDisable(true);
    }
    private void habilitarCamposProfesor(){
        this.cedula_campo.setDisable(false);
        this.matricula_campo.setDisable(true);
    }
    private void habilitarCamposCoordinador(){
        this.cedula_campo.setDisable(false);
        this.matricula_campo.setDisable(true);
    }
    private boolean hayCamposVacios(){
        //TODO: code empty fields
        return false;
    }

}