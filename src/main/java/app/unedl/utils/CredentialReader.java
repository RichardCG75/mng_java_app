package app.unedl.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Read credentials (user & pass) from XML file and produces a java object containing the data
 */
public class CredentialReader {

    public CredentialReader(){
        this.credentials = null;
        this.XMLcredentials = null;
    }

    public CredentialReader(File cred){
        this.readCredentials(cred);
    }

    private void readCredentials(File cred){

        try {

            //prepare files
            File file = cred;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            //read nodes by tag-name
            NodeList jdbc = doc.getElementsByTagName("jdbc");
            NodeList host = doc.getElementsByTagName("host");
            NodeList port = doc.getElementsByTagName("port");
            NodeList db_name = doc.getElementsByTagName("db_name");
            NodeList user = doc.getElementsByTagName("user");
            NodeList pass = doc.getElementsByTagName("pass");

            //assign nodes to java object
            this.credentials = new Credentials();
            this.credentials.setJdbc(jdbc.item(0).getTextContent());
            this.credentials.setHost(host.item(0).getTextContent());
            this.credentials.setUser(user.item(0).getTextContent());
            this.credentials.setPass(pass.item(0).getTextContent());
            this.credentials.setPort(port.item(0).getTextContent());
            this.credentials.setDb_name(db_name.item(0).getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCredentials(File credentials){
        this.XMLcredentials = credentials;
        this.readCredentials(this.XMLcredentials);
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials getCredentials(){
        return this.credentials;
    }

    public File getCredentialsFile(){
        return this.XMLcredentials;
    }

    private File XMLcredentials;
    private Credentials credentials;
}