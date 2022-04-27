package app.unedl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    //loads the app-database connection
    public static void establecerConexion(String jdbcURL, String usuario, String clave){
        try {
            ConexionBD.conexion = DriverManager.getConnection(jdbcURL, usuario, clave);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void establecerConexion(Connection conexion){
        ConexionBD.conexion = conexion;
    }

    public static Connection obtenerConexion(){
        return ConexionBD.conexion;
    }

    public static void establecerConexionEstandar(){
        try {
            ConexionBD.conexion = DriverManager.getConnection(ConexionBD.jdbcURL, ConexionBD.usuario, ConexionBD.clave);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: obtener credenciales de archivo xml o txt
    private static Connection conexion;
    private static final String jdbcURL = "jdbc:mysql://54.215.148.52:3306/sql3488439";
    private static final String usuario = "sql3488439";
    private static final String clave = "PnIhHiSUqj";
}
