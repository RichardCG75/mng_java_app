package app.unedl.utils;

public class Credentials {

    public Credentials(){
        this.setJdbc(null);
        this.setHost(null);
        this.setUser(null);
        this.setPass(null);
        this.setPort(null);
        this.setDb_name(null);
    }
    public Credentials(String jdbc, String host, String user, String pass, String port, String db_name){
        this.setJdbc(jdbc);
        this.setHost(host);
        this.setUser(user);
        this.setPass(pass);
        this.setPort(port);
        this.setDb_name(db_name);
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        this.jdbc = jdbc;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    private String jdbc;
    private String host;
    private String user;
    private String pass;
    private String port;
    private String db_name;
}
