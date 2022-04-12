package app.unedl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnecionBD {
    public ConnecionBD(Connection connection){
        this.connection = connection;
    }

    public ConnecionBD(String jdbcURL, String user, String pass){
        this.loadConnection(jdbcURL, user, pass);
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public void setConnection(String jdbcURL, String user, String pass){
        this.loadConnection(jdbcURL, user, pass);
    }

    //loads the app-database connection
    private void loadConnection(String jdbcURL, String user, String pass){
        try {
            this.connection = DriverManager.getConnection(jdbcURL, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;
}
