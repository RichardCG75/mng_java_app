package app.unedl.controllers;

import app.unedl.models.Coordinador;
import app.unedl.models.Estudiante;
import app.unedl.models.MiembroUNEDL;
import app.unedl.models.Profesor;
import app.unedl.utils.AppLogger;
import app.unedl.utils.ConexionBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.Hashtable;
import java.util.Optional;

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
    @FXML private ChoiceBox<String> selector_buscar;
    @FXML private TextField campo_buscar;
    @FXML private Button boton_buscar;
    @FXML private TextArea campo_area;

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
        campos_borrar = new TextField[]{nombre_campo_borrar, apellido1_campo_borrar, apellido2_campo_borrar, matricula_campo_borrar, cedula_campo_borrar, email_campo_borrar};
        //endregion

        //region iniciar_selectores

        //selectores crear/borrar
        this.selector_tabla_crear.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        this.selector_tabla_crear.setValue("Estudiante");
        this.selector_tabla_borrar.getItems().addAll("Estudiante", "Profesor", "Coordinador");
        this.selector_tabla_borrar.setValue("Estudiante");

        //selector buscar
        this.selector_buscar.getItems().addAll("id", "nombre", "apellido1", "apellido2", "registro", "email");
        this.selector_buscar.setValue("id");

        //endregion

    }

    public void onSeleccionarTablaCrear(ActionEvent e){

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

    public void onSeleccionarTablaBorrar(ActionEvent e){
        this.habilitarRegistrosBorrar();
        this.rellenarSelectorMiembros();
    }

    private void habilitarRegistrosBorrar() {
        switch (this.selector_tabla_borrar.getValue()){
            case "Estudiante": {this.cedula_campo_borrar.setDisable(true); this.matricula_campo_borrar.setDisable(false);}
            break;
            case "Profesor", "Coordinador": {this.cedula_campo_borrar.setDisable(false); this.matricula_campo_borrar.setDisable(true);}
            break;
        }
    }

    public void onSeleccionarMiembroUNEDL(){

        this.asignarValoresAlModelo(this.miembroUNEDL);
        this.enlazarProperties();
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
            this.insertarMiembroUNEDL(datos.get("nombre"), datos.get("apellido1"), datos.get("apellido2"), datos.get("registro"), datos.get("email"));
            AppLogger.LOGGER.log(System.Logger.Level.INFO, selector_miembros_borrar.getValue() + datos.get("nombre") + " creado con exito...");
        }

        //endregion
    }

    public void onLimpiar(ActionEvent e){

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Limpiando formulario...");

        for (TextField campo : this.campos_crear) {
            campo.setText("");
        }

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "...Formulario limpiado");
    }

    public void onActualizar(ActionEvent e){

        if (hayCamposBorrarVacios()){
            AppLogger.LOGGER.log(System.Logger.Level.WARNING, "Hay campos vacios");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Hay campos vacios!\nDesea continuar?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType r = result.get();
            if (r == ButtonType.CANCEL || r == ButtonType.CLOSE || r == ButtonType.NO || r == ButtonType.FINISH) return;
        }

        Hashtable<String, String> datos = this.recuperarDatosFormularioBorrar();
        this.actualizarMiembro(datos);
        this.rellenarSelectorMiembros();

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Actualizando...");
    }

    public void onEliminar(ActionEvent e){
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Eliminando " + this.selector_tabla_borrar.getValue());
        String tabla = this.saberTablaDestino(this.selector_tabla_borrar);
        String id = this.selector_miembros_borrar.getValue().get("id");
        String query = "DELETE FROM " + tabla + " WHERE id=?;";

        try {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, id);

            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL " + statement);
            statement.execute();
        } catch (SQLException ex) {
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error ejecutando sentencia SQL");
            ex.printStackTrace();
        }

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Sentencia SQL ejecutada con exito");
        this.rellenarSelectorMiembros();

    }

    public void onBuscar(ActionEvent e){
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Buscando...");
        ResultSet resultSetEstudiantes = this.buscarEnTabla("estudiantes", this.selector_buscar.getValue(), this.campo_buscar.getText());
        ResultSet resultSetProfesores = this.buscarEnTabla("profesores", this.selector_buscar.getValue(), this.campo_buscar.getText());
        ResultSet resultSetCoordinadores = this.buscarEnTabla("coordinadores", this.selector_buscar.getValue(), this.campo_buscar.getText());

        //limpiar campo
        this.campo_area.clear();

        this.desplegarResultSetEnInterfaz(resultSetEstudiantes);
        this.desplegarResultSetEnInterfaz(resultSetProfesores);
        this.desplegarResultSetEnInterfaz(resultSetCoordinadores);


    }

    //TODO: recibe hashtable
    private void insertarMiembroUNEDL(String nombre, String apellido1, String apellido2, String registro, String email) {

        try {

            /* la tabla y columna son asignados a la sentencia sql dependiendo de la opcion seleccionada en el choicebox */
            String tabla = this.saberTablaDestino(selector_tabla_crear);
            String query = "INSERT INTO " + tabla + " (nombre, apellido1, apellido2, registro, email) VALUES (?, ?, ?, ?, ?)";

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

    //auxiliares (subrutinas)
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
        datos.put("nombre", this.nombre_campo_crear.getText());
        datos.put("apellido1", this.apellido1_campo_crear.getText());
        datos.put("apellido2", this.apellido2_campo_crear.getText());
        if (this.selector_tabla_crear.getValue() == "Estudiante") datos.put("registro", this.matricula_campo_crear.getText());
        if (this.selector_tabla_crear.getValue() == "Profesor") datos.put("registro", this.cedula_campo_crear.getText());
        if (this.selector_tabla_crear.getValue() == "Coordinador") datos.put("registro", this.cedula_campo_crear.getText());
        datos.put("email", this.email_campo_crear.getText());

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
        this.nombre_campo_borrar.textProperty().bindBidirectional(this.miembroUNEDL.obtenerNombreProperty());
        this.apellido1_campo_borrar.textProperty().bindBidirectional(this.miembroUNEDL.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.PATERNO));
        this.apellido2_campo_borrar.textProperty().bindBidirectional(this.miembroUNEDL.obtenerApellidoProperty(MiembroUNEDL.APELLIDO.MATERNO));
        if (this.selector_tabla_borrar.getValue() == "Estudiante") {
            this.cedula_campo_borrar.textProperty().unbindBidirectional(this.miembroUNEDL.obtenerRegistroProperty());
            this.cedula_campo_borrar.setText("");
            this.matricula_campo_borrar.textProperty().bindBidirectional(this.miembroUNEDL.obtenerRegistroProperty());
        }
        if (this.selector_tabla_borrar.getValue() == "Profesor" || this.selector_tabla_borrar.getValue() == "Coordinador") {
            this.matricula_campo_borrar.textProperty().unbindBidirectional(this.miembroUNEDL.obtenerRegistroProperty());
            this.matricula_campo_borrar.setText("");
            this.cedula_campo_borrar.textProperty().bindBidirectional(this.miembroUNEDL.obtenerRegistroProperty());
        }
        this.email_campo_borrar.textProperty().bindBidirectional(this.miembroUNEDL.obtenerEmailProperty());
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
                datos.put("apellido1", resultSet.getString("apellido1"));
                datos.put("apellido2", resultSet.getString("apellido2"));
                datos.put("registro", resultSet.getString("registro"));
                datos.put("email", resultSet.getString("email"));
                datos.put("id", resultSet.getString("id"));
            }

            miembroModel.establecerNombre(datos.get("nombre"));
            miembroModel.establecerApellido(datos.get("apellido1"), MiembroUNEDL.APELLIDO.PATERNO);
            miembroModel.establecerApellido(datos.get("apellido2"), MiembroUNEDL.APELLIDO.MATERNO);
            miembroModel.establecerRegistro(datos.get("registro"));
            miembroModel.establecerId(Integer.parseInt(datos.get("id")));
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
//            String registro = this.saberRegistroDestino(selector_tabla_borrar);
            String query = "SELECT * FROM " + tabla;
            Statement statement = conexion.createStatement();

            //ejecutar sentencia
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL " + query);
            ResultSet resultSet = statement.executeQuery(query);
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Sentencia SQL ejecutada con exito");

            boolean f = false;

            while (resultSet.next()){
                Hashtable<String, String> datos = new Hashtable<>();
                datos.put("nombre", resultSet.getString("nombre"));
                datos.put("apellido", resultSet.getString("apellido1"));
                datos.put("id", resultSet.getString("id"));
                datos.put("registro", resultSet.getString("registro"));

                selector_miembros_borrar.getItems().add(datos);
                if (!f) {selector_miembros_borrar.setValue(datos); f = true; }
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
    private Hashtable<String, String> recuperarDatosFormularioBorrar(){

        Hashtable<String, String> datos = new Hashtable<>();
        datos.put("id", this.selector_miembros_borrar.getValue().get("id"));
        datos.put("nombre", this.nombre_campo_borrar.getText());
        datos.put("apellido1", this.apellido1_campo_borrar.getText());
        datos.put("apellido2", this.apellido2_campo_borrar.getText());
        if (this.selector_tabla_borrar.getValue() == "Estudiante") datos.put("registro", this.matricula_campo_borrar.getText());
        if (this.selector_tabla_borrar.getValue() == "Profesor") datos.put("registro", this.cedula_campo_borrar.getText());
        if (this.selector_tabla_borrar.getValue() == "Coordinador") datos.put("registro", this.cedula_campo_borrar.getText());
        datos.put("email", this.email_campo_borrar.getText());

        return datos;
    }
    private void actualizarMiembro(Hashtable<String, String> datos){

        String tabla = this.saberTablaDestino(this.selector_tabla_borrar);
        String query = "UPDATE " + tabla + " SET nombre=?, apellido1=?, apellido2=?, registro=?, email=? WHERE id=?;";

        try {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, datos.get("nombre"));
            statement.setString(2, datos.get("apellido1"));
            statement.setString(3, datos.get("apellido2"));
            statement.setString(4, datos.get("registro"));
            statement.setString(5, datos.get("email"));
            statement.setString(6, datos.get("id"));

            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia " + statement);
            statement.execute();
        } catch (SQLException e) {
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error ejecutando sentencia SQL");
            e.printStackTrace();
        }
    }
    private ResultSet buscarEnTabla(String tabla, String clave, String valor){

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Buscando " + tabla);
        String query = "SELECT * FROM " + tabla + " WHERE " + clave + "=?;";
        try {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, valor);

            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL " + statement);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Error ejecutando sentencia SQL");
            e.printStackTrace();
            return null;
        }
    }
    private void desplegarResultSetEnInterfaz(ResultSet resultSet){

        try {

            int i = 1;
            while (resultSet.next()){
                Hashtable<String, String> datos = new Hashtable<>();

                datos.put("id", resultSet.getString("id"));
                datos.put("nombre", resultSet.getString("nombre"));
                datos.put("apellido1", resultSet.getString("apellido1"));
                datos.put("apellido2", resultSet.getString("apellido2"));
                datos.put("registro", resultSet.getString("registro"));
                datos.put("email", resultSet.getString("email"));

                this.campo_area.appendText(datos.toString());
                this.campo_area.appendText("\n");
            }

        } catch (Exception ex){

        }
    }
    private boolean hayCamposBorrarVacios(){

        boolean f = false;

        for (TextField campo : this.campos_borrar) {
            f = campo.getText().isEmpty() ? true : false;
        }

        return f;
    }
}