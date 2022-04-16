package app.unedl.controllers;

import app.unedl.models.Coordinador;
import app.unedl.models.Estudiante;
import app.unedl.models.MiembroUNEDL;
import app.unedl.models.Profesor;
import app.unedl.utils.AppLogger;
import app.unedl.utils.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.Hashtable;

public class SceneViewController {

    @FXML private TextField nombre_campo_crear;
    @FXML private TextField apellido1_campo_crear;
    @FXML private TextField apellido2_campo_crear;
    @FXML private TextField matricula_campo_crear;
    @FXML private TextField cedula_campo_crear;
    @FXML private TextField email_campo_crear;
    @FXML private ChoiceBox<String> selector_tabla_crear;

    @FXML private TextField nombre_campo_borrar;
    @FXML private TextField apellido1_campo_borrar;
    @FXML private TextField apellido2_campo_borrar;
    @FXML private TextField matricula_campo_borrar;
    @FXML private TextField cedula_campo_borrar;
    @FXML private TextField email_campo_borrar;
    @FXML private ChoiceBox<String> selector_tabla_borrar;
    @FXML private ChoiceBox<Hashtable<String, String>> selector_miembros_borrar;

    private Connection conexion;
    private TextField[] campos_crear;
    private TextField[] campos_borrar;
    private Estudiante estudianteModel;
    private Profesor profesorModel;
    private Coordinador coordinadorModel;
    private MiembroUNEDL miembroUNEDL;

    public void initialize(){

        //region inicializar_modelos
        this.estudianteModel = new Estudiante();
        this.profesorModel = new Profesor();
        this.coordinadorModel = new Coordinador();
        this.miembroUNEDL = new MiembroUNEDL();
        //endregion

        //region inicializar_campos
        conexion = ConexionBD.obtenerConexion();
        campos_crear = new TextField[]{nombre_campo_crear, apellido1_campo_crear, apellido2_campo_crear, matricula_campo_crear, cedula_campo_crear, email_campo_crear};
        //endregion

        //region iniciar_selectores
        selector_tabla_crear.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        selector_tabla_crear.setValue("Estudiante");
        selector_tabla_borrar.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        selector_tabla_borrar.setValue("Estudiante");

        //endregion

    }

    public void onCambioTablaCrear(ActionEvent e){

        switch (selector_tabla_crear.getValue()){
            case "Estudiante": habilitarCamposEstudiante(); break;
            case  "Profesor": habilitarCamposProfesor(); break;
            case  "Coordinador": habilitarCamposCoordinador(); break;
            default: AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Miembro UNEDL no valido");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Miembro no valido");
            alert.show();
        }
    }

    public void onCambioTablaBorrar(ActionEvent e){

        this.rellenarSelectorMiembros();

    }

    public void onCambioMiembroUNEDL(){

        if (selector_tabla_borrar.getValue() == "Estudiante") asignarValoresAlModelo(this.estudianteModel);
        if (selector_tabla_borrar.getValue() == "Profesor") asignarValoresAlModelo(this.profesorModel);
        if (selector_tabla_borrar.getValue() == "Coordinador") asignarValoresAlModelo(this.coordinadorModel);

//        this.enlazarProperties();

    }

    public void onCrear(ActionEvent e) {

        //region crear_miembroUNEDL

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando " + selector_tabla_crear.getValue() + "...");

        /*si cualquier campo esta vacio lanzar advertencia*/
        if (existenCamposVacios(this.campos_crear)){
            AppLogger.LOGGER.log(System.Logger.Level.WARNING, "Hay campos vacios en el formulario");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No puede haber campos vacios!\nAsegurese de llenar todos los campos! :)");
            alert.show();
        }

        /* si no hay campos vacios -> obtener datos de formulario e insertar*/
        else {
            Hashtable<String, String> datos = recuperarDatosFormularioCrear();
            switch (selector_tabla_crear.getValue()){
                case "Estudiante": insertarMiembroUNEDL(datos.get("nombre"), datos.get("apellido1"), datos.get("apellido2"), datos.get("matricula"), datos.get("email"));
                    break;
                case "Profesor": insertarMiembroUNEDL(datos.get("nombre"), datos.get("apellido1"), datos.get("apellido2"), datos.get("cedula"), datos.get("email"));
                    break;
                case "Coordinador": insertarMiembroUNEDL(datos.get("nombre"), datos.get("apellido1"), datos.get("apellido2"), datos.get("cedula"), datos.get("email"));
                    break;
            }
            //Ejm: Estudiante Juan creado con exito...
            AppLogger.LOGGER.log(System.Logger.Level.INFO, selector_miembros_borrar.getValue() + datos.get("nombre") + " creado con exito...");
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
            String tabla = this.saberTablaDestino(selector_tabla_crear);
            String columna = this.saberRegistroDestino(selector_tabla_crear);
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
        this.matricula_campo_crear.setDisable(false);
        this.cedula_campo_crear.setDisable(true);
    }
    private void habilitarCamposProfesor(){
        this.cedula_campo_crear.setDisable(false);
        this.matricula_campo_crear.setDisable(true);
    }
    private void habilitarCamposCoordinador(){
        this.cedula_campo_crear.setDisable(false);
        this.matricula_campo_crear.setDisable(true);
    }
    private Hashtable<String, String> recuperarDatosFormularioCrear(){
        Hashtable<String, String> datos = new Hashtable<String, String>();

        /*obtener datos de la vista*/
        datos.put("nombre", nombre_campo_crear.getText());
        datos.put("apellido1", apellido1_campo_crear.getText());
        datos.put("apellido2", apellido2_campo_crear.getText());
        datos.put("matricula", matricula_campo_crear.getText());
        datos.put("cedula", cedula_campo_crear.getText());
        datos.put("email", email_campo_crear.getText());

        return datos;
    }
    private Hashtable<String, String> obtenerDatosFormularioBorrar(){
        Hashtable<String, String> datos = new Hashtable<String, String>();

        /*obtener datos de la vista*/
        datos.put("nombre", nombre_campo_borrar.getText());
        datos.put("apellido1", apellido1_campo_borrar.getText());
        datos.put("apellido2", apellido2_campo_borrar.getText());
        datos.put("matricula", matricula_campo_borrar.getText());
        datos.put("cedula", cedula_campo_borrar.getText());
        datos.put("email", email_campo_borrar.getText());

        return datos;
    }
    private boolean existenCamposVacios(TextField[] campos){

        for (TextField campo : campos) {
            if (campo.isDisabled()) continue;
            if (campo.getText().isEmpty()) return true;
        }
        return false;
    }
    private void enlazarProperties(){
        //region unir_vista_modelo
        switch (selector_tabla_borrar.getValue()){
            case "Estudiante":
                nombre_campo_borrar.textProperty().bind(estudianteModel.obtenerNombreProperty());
                apellido1_campo_borrar.textProperty().bind(estudianteModel.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.PATERNO));
                apellido2_campo_borrar.textProperty().bind(estudianteModel.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.MATERNO));
                matricula_campo_borrar.textProperty().bind(estudianteModel.obtenerMatriculaProperty());
                email_campo_borrar.textProperty().bind(estudianteModel.obtenerEmailProperty());
                break;
            case "Profesor" :
                nombre_campo_borrar.textProperty().bind(profesorModel.obtenerNombreProperty());
                apellido1_campo_borrar.textProperty().bind(profesorModel.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.PATERNO));
                apellido2_campo_borrar.textProperty().bind(profesorModel.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.MATERNO));
                cedula_campo_borrar.textProperty().bind(profesorModel.obtenerCedulaProperty());
                email_campo_borrar.textProperty().bind(profesorModel.obtenerEmailProperty());
            case "Coordinador" :
                nombre_campo_borrar.textProperty().bind(coordinadorModel.obtenerNombreProperty());
                apellido1_campo_borrar.textProperty().bind(coordinadorModel.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.PATERNO));
                apellido2_campo_borrar.textProperty().bind(coordinadorModel.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.MATERNO));
                cedula_campo_borrar.textProperty().bind(coordinadorModel.obtenerCedulaProperty());
                email_campo_borrar.textProperty().bind(coordinadorModel.obtenerEmailProperty());
        }
        //endregion
    }
    private void asignarValoresAlModelo(MiembroUNEDL miembroModel){

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Asignando valores al modelo");
        Hashtable<String, String> datos = new Hashtable<>();
        ResultSet resultSet = consultarMiembroSeleccionado();

        try {
            //guardar en datos
            while (resultSet.next()){

                datos.put("nombre", resultSet.getString("nombre"));
                datos.put("apellido", resultSet.getString("apellido1"));
                datos.put("id", resultSet.getString("id"));

                if (miembroModel.getClass() == Estudiante.class) datos.put("matricula", "matricula");
                if (miembroModel.getClass() == Profesor.class) datos.put("cedula", "cedula");
                if (miembroModel.getClass() == Coordinador.class) datos.put("cedula", "cedula");
            }

            miembroModel.establecerNombre(datos.get("nombre"));
            miembroModel.establecerApellido(datos.get("apellido1"), MiembroUNEDL.APELLIDO.PATERNO);
            miembroModel.establecerApellido(datos.get("apellido2"), MiembroUNEDL.APELLIDO.MATERNO);
            if (miembroModel.getClass() == Estudiante.class) ((Estudiante) miembroModel).establecerMatricula(datos.get("matricula"));
            if (miembroModel.getClass() == Profesor.class) ((Profesor) miembroModel).establecerCedula(datos.get("cedula"));
            if (miembroModel.getClass() == Coordinador.class) ((Coordinador) miembroModel).establecerCedula(datos.get("cedula"));
            miembroModel.establecerEmail(datos.get("email"));
        } catch (SQLException e) {

            e.printStackTrace();
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error asignando valores al modelo");
        }

    }
    private String saberTablaDestino(ChoiceBox<String> selector){
        return (selector.getValue() == "Estudiante") ? "estudiantes" : (selector.getValue() == "Profesor") ? "profesores" : "coordinadores";
    }
    private String saberRegistroDestino(ChoiceBox<String> selector){
        return (selector.getValue() == "Estudiante") ? "matricula" : (selector.getValue() == "Profesor") ? "cedula" : "cedula";
    }
    private void rellenarSelectorMiembros(){
        //region queries
        try {

            //limpiar
            selector_miembros_borrar.getItems().clear();

            //preparar sentencia
            String tabla = this.saberTablaDestino(selector_tabla_borrar);
            String registro = this.saberRegistroDestino(selector_tabla_borrar);
            String query = "SELECT * FROM " + tabla;
            Statement statement = conexion.createStatement();

            //ejecutar sentencia
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL " + query);
            ResultSet resultSet = statement.executeQuery(query);
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Sentencia SQL ejecutada con exito");

            //si la tabla esta vacia
//            if (resultSet.next() == false){
//                selector_miembros_borrar.setValue(null);
//                AppLogger.LOGGER.log(System.Logger.Level.WARNING, "No existen miembros en tabla " + tabla);
//            }
//
//            //llenar choicebox con datos de ResultSet
//            else {
//
//
//            }

            while (resultSet.next()){
                Hashtable<String, String> datos = new Hashtable<>();
                datos.put("nombre", resultSet.getString("nombre"));
                datos.put("apellido", resultSet.getString("apellido1"));
                datos.put("id", resultSet.getString("id"));
                datos.put(registro, resultSet.getString(registro));

                selector_miembros_borrar.getItems().add(datos);
                selector_miembros_borrar.setValue(datos);
            }

        } catch (SQLException ex) {
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error ejecutando sentencia SQL");
            ex.printStackTrace();
        }
        //endregion
    }
    private ResultSet consultarMiembroSeleccionado(){

        String tabla = this.saberTablaDestino(selector_tabla_borrar);
        String query = "SELECT * FROM " + tabla + " WHERE id=?";
        String id = this.selector_miembros_borrar.getValue().get("id");

        try {

            //consultar por id
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, id);

            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL: " + statement);
            ResultSet resultSet = statement.executeQuery();
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Sentencia SQL ejecutada con exito");
            return resultSet;

        } catch (SQLException e) {
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error ejecutando la sentencia SQL");
            e.printStackTrace();
            return null;
        }
    }

}