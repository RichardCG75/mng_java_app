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

import java.security.KeyPair;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class SceneViewController {

    @FXML private TextField nombre_campo;
    @FXML private TextField apellido1_campo;
    @FXML private TextField apellido2_campo;
    @FXML private TextField matricula_campo;
    @FXML private TextField cedula_campo;
    @FXML private TextField email_campo;
    @FXML private ChoiceBox<String> selector_tipo_izq;
    @FXML private ChoiceBox<String> selector_tipo_der;

    private Connection conexion;
    private TextField[] campos;

    public void initialize(){

        //region inicializar_campos
        conexion = ConexionBD.obtenerConexion();
        campos = new TextField[]{nombre_campo, apellido1_campo, apellido2_campo, matricula_campo, cedula_campo, email_campo};
        //endregion

        //region iniciar_selectores
        selector_tipo_izq.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        selector_tipo_izq.setValue("Estudiante");
        selector_tipo_der.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        selector_tipo_der.setValue("Estudiante");

        //iniciar lista de miembros desde base de datos
        ObservableList<String> miembros = FXCollections.observableArrayList();
        //endregion

    }

    public void onCambioMiembroCrear(ActionEvent e){

        switch (selector_tipo_izq.getValue()){
            case "Estudiante": habilitarCamposEstudiante(); break;
            case  "Profesor": habilitarCamposProfesor(); break;
            case  "Coordinador": habilitarCamposCoordinador(); break;
            default: AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Miembro UNEDL no valido");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Miembro no valido");
            alert.show();
        }
    }

    public void onCambioMiembroElimAct(ActionEvent e){
        //TODO: Codigo cambio formulario actualizar
    }

    public void onCrear(ActionEvent e) {
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando " + selector_tipo_izq.getValue() + "...");

        //region crear_miembroUNEDL

        /*si cualquier campo esta vacio lanzar advertencia*/
        if (existenCamposVacios()){
            AppLogger.LOGGER.log(System.Logger.Level.WARNING, "Hay campos vacios en el formulario");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No puede haber campos vacios!\nAsegurese de llenar todos los campos! :)");
            alert.show();
        }

        /* si no hay campos vacios -> obtener datos de formulario e insertar*/
        else {
            Hashtable<String, String> datos = obtenerDatosFormulario();
            insertarMiembroUNEDL(datos.get("nombre"), datos.get("apellido1"), datos.get("apellido2"), datos.get("matricula"), datos.get("email"));
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "alumno " + datos.get("nombre") + " creado con exito...");
        }

        //endregion
    }

    public void onLimpiar(ActionEvent e){

    }

    public void onActualizar(ActionEvent e){

    }

    public void onEliminar(ActionEvent e){

    }

    private void insertarMiembroUNEDL(String nombre, String apellido1, String apellido2, String registro, String email) {

        try {

            /* la tabla y columna son asignados a la sentencia sql dependiendo de la opcion seleccionada en el choicebox */
            String tabla = (selector_tipo_izq.getValue() == "Estudiante") ? "estudiantes" : (selector_tipo_izq.getValue() == "Profesor") ? "profesores" : "coordinadores";
            String columna = (selector_tipo_izq.getValue() == "Estudiante") ? "matricula" : (selector_tipo_izq.getValue() == "Profesor") ? "cedula" : "cedula";
            String query = "INSERT INTO " + tabla + " (nombre, apellido1, apellido2, " + columna + ", email) VALUES (?, ?, ?, ?, ?)";

            /* prepara y ejecuta la sentencia sql */
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, apellido1);
            statement.setString(3, apellido2);
            statement.setString(4, registro);
            statement.setString(5, email);

            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL: " + statement);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error ejecutando sentencia SQL");
            return;
        }
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

    private Hashtable<String, String> obtenerDatosFormulario(){
        Hashtable<String, String> datos = new Hashtable<String, String>();

        /*obtener datos de la vista*/
        datos.put("nombre", nombre_campo.getText());
        datos.put("apellido1", apellido1_campo.getText());
        datos.put("apellido2", apellido2_campo.getText());
        datos.put("matricula", matricula_campo.getText());
        datos.put("cedula", cedula_campo.getText());
        datos.put("email", email_campo.getText());

        return datos;
    }
    private boolean existenCamposVacios(){

        for (TextField campo : this.campos) {
            if (campo.isDisabled()) continue;
            if (campo.getText().isEmpty()) return true;
        }
        return false;
    }
}