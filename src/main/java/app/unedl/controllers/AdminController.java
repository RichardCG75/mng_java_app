package app.unedl.controllers;

import app.unedl.models.UserUNEDL;
import app.unedl.utils.AppLogger;
import app.unedl.utils.DBConenction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class AdminController {

    @FXML TableView<UserUNEDL> table;
    @FXML TableColumn<UserUNEDL, Integer> colId;
    @FXML TableColumn<UserUNEDL, String> colName;
    @FXML TableColumn<UserUNEDL, String> colLn1;
    @FXML TableColumn<UserUNEDL, String> colLn2;
    @FXML TableColumn<UserUNEDL, String> colEmail;
    @FXML TableColumn<UserUNEDL, String> colReg;
    @FXML TableColumn<UserUNEDL, Boolean> colActive;
    @FXML TableColumn<UserUNEDL, String> colType;
    @FXML ToggleGroup memberType;
    @FXML RadioButton studentR;
    @FXML RadioButton professorR;
    @FXML ChoiceBox<String> searchTypeSelec;
    @FXML TextField searchField;
    @FXML CheckBox studentC;
    @FXML CheckBox professorC;
    @FXML CheckBox coordinatorC;
    @FXML RadioButton coordinatorR;
    @FXML TextField name;
    @FXML TextField lastName1;
    @FXML TextField lastName2;
    @FXML TextField email;
    @FXML TextField register;
    @FXML ToggleGroup isActive;
    @FXML RadioButton active;
    @FXML RadioButton noActive;
    @FXML Button create;
    @FXML Button remove;
    private Connection connection;

    public void initialize() {

        //region inicializar_modelos

        //endregion

        //region inicializar_campos
        connection = DBConenction.getConnection();
        searchTypeSelec.getItems().addAll("ID", "Nombre", "Apellido1", "Apellido2", "Email", "Registro", "Actividad");
        searchTypeSelec.getSelectionModel().selectFirst();
        studentR.setSelected(true);
        studentC.setSelected(true);
        active.setSelected(true);
        //endregion

        //region SetUpTableCells

        colId.setCellValueFactory(new PropertyValueFactory<UserUNEDL, Integer>("id"));

        colName.setCellValueFactory(new PropertyValueFactory<UserUNEDL, String>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());

        colLn1.setCellValueFactory(new PropertyValueFactory<UserUNEDL, String>("ln1"));
        colLn1.setCellFactory(TextFieldTableCell.forTableColumn());

        colLn2.setCellValueFactory(new PropertyValueFactory<UserUNEDL, String>("ln2"));
        colLn2.setCellFactory(TextFieldTableCell.forTableColumn());

        colEmail.setCellValueFactory(new PropertyValueFactory<UserUNEDL, String>("email"));
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());

        colReg.setCellValueFactory(new PropertyValueFactory<UserUNEDL, String>("reg"));
        colReg.setCellFactory(TextFieldTableCell.forTableColumn());

        colActive.setCellValueFactory(new PropertyValueFactory<UserUNEDL, Boolean>("active"));

        colType.setCellValueFactory(new PropertyValueFactory<UserUNEDL, String>("type"));

        display();
        //endregion

    }
    public void onCrear(ActionEvent e) {

        RadioButton typeSelected = (RadioButton) memberType.getSelectedToggle();
        String table = typeSelected.getText();
        switch (table){
            case "Estudiante" -> table = "estudiantes";
            case "Profesor" -> table = "profesores";
            case "Coordinador" -> table = "coordinadores";
        }
        AppLogger.LOGGER.log(System.Logger.Level.INFO, "creando " + typeSelected.getText() + "...");

        if (emptyFields()) {
            AppLogger.LOGGER.log(System.Logger.Level.WARNING, "Hay campos vacios en el formulario");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No puede haber campos vacios!\nAsegurese de llenar todos los campos! :)");
            alert.show();
        }

        else {
            String name = this.name.getText();
            String ln1 = this.lastName1.getText();
            String ln2 = this.lastName2.getText();
            String email = this.email.getText();
            String reg = this.register.getText();
            boolean active = this.active.isSelected();
            this.insert(table, name, ln1, ln2, reg, email, active);
            AppLogger.LOGGER.log(System.Logger.Level.INFO, typeSelected.getText() + name + " creado con exito...");
        }

        display();
    }

    public void onActualizar(TableColumn.CellEditEvent<UserUNEDL, String> e) {

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Cell edited. Updating...");

        int id = table.getSelectionModel().getSelectedItem().getId();
        String tableName = getTableNameSelectedByType();
        String newValue = e.getNewValue();
        String column = e.getTableColumn().getText().toLowerCase();

        String sql = "UPDATE " + tableName + " SET " + column + "=? WHERE id=?";
        try {
            PreparedStatement statement =  DBConenction.getConnection().prepareStatement(sql);
            statement.setString(1, newValue);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ex) {
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Failed trying to update");
            throw new RuntimeException(ex);
        }
        display();
    }

    public void onEliminar(ActionEvent e) {

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Eliminando...");

        int id = table.getSelectionModel().getSelectedItem().getId();
        String tableName = getTableNameSelectedByType();

        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        try {
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Eliminado usuario...");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error eliminando usuario");
            ex.printStackTrace();
        }

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Sentencia SQL ejecutada con exito");
        display();
    }

    public void onBuscar(ActionEvent e) {

        String constraint = getSearchConstraint();
        String value = searchField.getText();
        display();
    }

    /**
     * Cleans form
     * @param e
     */
    public void onLimpiar(ActionEvent e) {

        TextField[] fields = {this.name, this.lastName1, this.lastName2, this.email, this.register};

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "Limpiando formulario...");

        for (TextField field : fields) {
            field.setText("");
        }

        AppLogger.LOGGER.log(System.Logger.Level.INFO, "...Formulario limpiado");
    }

    public void onActivar(ActionEvent e){

        String tableName = getTableNameSelectedByType();
        int id = table.getSelectionModel().getSelectedItem().getId();
        Boolean state = table.getSelectionModel().getSelectedItem().isActive();

        String sql = "UPDATE " + tableName + " SET " + " activo=? WHERE id=?";
        try {
            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Activating user...");
            PreparedStatement statement = DBConenction.getConnection().prepareStatement(sql);
            statement.setBoolean(1, !state);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ex) {
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error activating user");
            throw new RuntimeException(ex);
        }

        display();
    }

    //SUB-ROUTINES

    /**
     * Inserts a brand-new member into the specified table
     * @param table Table name
     * @param name Name
     * @param ln1 Last name 2
     * @param ln2 Last name 2
     * @param reg register
     * @param email email
     * @param active Is active or not
     */
    private void insert(String table, String name, String ln1, String ln2, String reg, String email, boolean active) {

        try {

            String query = "INSERT INTO " + table + " (nombre, apellido1, apellido2, registro, email, activo) VALUES (?, ?, ?, ?, ?, ?)";

            /* Prepare/Execute SQL Statement */
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, ln1);
            statement.setString(3, ln2);
            statement.setString(4, reg);
            statement.setString(5, email);
            statement.setBoolean(6, active);

            AppLogger.LOGGER.log(System.Logger.Level.INFO, "Ejecutando sentencia SQL: " + statement);
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            AppLogger.LOGGER.log(System.Logger.Level.ERROR, "Error ejecutando sentencia SQL");
            return;
        }
    }

    private ObservableList<UserUNEDL> query(String sql){

        ObservableList<UserUNEDL> list = FXCollections.observableArrayList();

        try {
            //Prepare statement
            Statement statement = DBConenction.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            String tableName = resultSet.getMetaData().getTableName(1);

            switch (tableName){
                case "estudiantes" -> tableName = "Estudiante";
                case "profesores" -> tableName = "Profesor";
                case "coordinadores" -> tableName = "Coordinador";
            }

            while (resultSet.next()) {
                UserUNEDL m = new UserUNEDL(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido1"),
                        resultSet.getString("apellido2"),
                        resultSet.getString("email"),
                        resultSet.getString("registro"),
                        resultSet.getBoolean("activo"),
                        tableName
                );

                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    /**
     * Retrieves all rows from the given table
     * @param table Table which want to get all tables
     * @return ObservableList containing all the rows
     */
    private ObservableList<UserUNEDL> queryTable(String table) {

        String sql = "SELECT * FROM " + table + ";";
        ObservableList<UserUNEDL> list = query(sql);
        return list;
    }

    private ObservableList<UserUNEDL> queryTable(String table, String constraint, String value) {

        String sql = "SELECT * FROM " + table + " WHERE " + constraint + "='"+value+"'";
        ObservableList<UserUNEDL> list = query(sql);
        return list;
    }

    private ObservableList<UserUNEDL> querySelectedTables(){

        ObservableList<UserUNEDL> list = FXCollections.observableArrayList();

        if (studentC.isSelected()) list.addAll(queryTable("estudiantes"));
        if (professorC.isSelected()) list.addAll(queryTable("profesores"));
        if (coordinatorC.isSelected()) list.addAll(queryTable("coordinadores"));

        return list;

    }

    private ObservableList<UserUNEDL> querySelectedTables(String constraint, String value){
        ObservableList<UserUNEDL> list = FXCollections.observableArrayList();

        if (studentC.isSelected()) list.addAll(queryTable("estudiantes", constraint, value));
        if (professorC.isSelected()) list.addAll(queryTable("profesores", constraint, value));
        if (coordinatorC.isSelected()) list.addAll(queryTable("coordinadores", constraint, value));

        return list;
    }

    /**
     * Checks if any of the text fields for insert is empty
     * @return true if any text field is empty, false no one is empty
     */
    private boolean emptyFields() {

        TextField[] fields = {this.name, this.lastName1, this.lastName2, this.email, this.register};

        for (TextField field : fields) {
            if (field.getText().isEmpty()) return true;
        }
        return false;
    }

    private String getSearchConstraint(){

        String constraint = "";
        switch (searchTypeSelec.getSelectionModel().getSelectedItem()){
            case "ID" -> constraint = "id";
            case "Nombre" -> constraint = "nombre";
            case "Apellido1" -> constraint = "apellido1";
            case "Apellido2" -> constraint = "apellido2";
            case "Email" -> constraint = "email";
            case "Registro" -> constraint = "registro";
            case "Actividad" -> constraint = "activo";
        }
        return constraint;
    }

    private void display(){

        if (searchField.getText().isEmpty()){
            ObservableList<UserUNEDL> items = querySelectedTables();        //If no constraints query whole table
            this.table.setItems(items);
        } else {
            String constraint = getSearchConstraint();                      //If constraints make select
            String value = searchField.getText();
            ObservableList<UserUNEDL> items = querySelectedTables(constraint, value);
            this.table.setItems(items);
        }
    }

    private String getTableNameSelectedByType(){

        String name = table.getSelectionModel().getSelectedItem().getType();
        switch (name){
            case "Estudiante" -> name = "estudiantes";
            case "Profesor" -> name = "profesores";
            case "Coordinador" -> name = "coordinadores";
        }

        return name;
    }
}