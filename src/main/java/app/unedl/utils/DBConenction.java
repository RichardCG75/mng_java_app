package app.unedl.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConenction {

    /**
     * Saves default credentials specified at cred/db_cred.xml file in encapsulated class fields
     */
    public static void setDefaultCredentials(){

        //Prepare file and reader
        File file = new File("cred/db_cred.xml");
        CredentialReader credReader = new CredentialReader(file);
        Credentials cred = credReader.getCredentials();

        //Save credentials
        DBConenction.jdbc = cred.getJdbc();
        DBConenction.host = cred.getHost();
        DBConenction.port = cred.getPort();
        DBConenction.db_name = cred.getDb_name();
        DBConenction.user = cred.getUser();
        DBConenction.pass = cred.getPass();
    }

    /**
     * Starts a new connection using the default credentials specified at cred/db_cred.xml file
     */
    public static void startDefaultConnection(){
        try {

            DBConenction.setDefaultCredentials();
            DBConenction.startNewConnection(jdbc, host, port, db_name, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startNewConnection(String jdbc, String host, String port, String db_name, String user, String pass){
        String jdbcURL = jdbc+"://"+host+":"+port+"/"+db_name;
        System.out.println(jdbcURL);
        try {
            DBConenction.connection = DriverManager.getConnection(jdbcURL, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startNewConnection(Connection conexion){
        DBConenction.connection = conexion;
    }

    public static Connection getConnection(){
        return DBConenction.connection;
    }

    private static Connection connection;
    private static String jdbc;
    private static String host;
    private static String port;
    private static String db_name;
    private static String user;
    private static String pass;
}