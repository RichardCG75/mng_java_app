package app.unedl.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Define un Profesor que pertenece a UNEDL
public class Profesor extends MiembroUNEDL {

    public Profesor(){    super();   }
    public Profesor(String nombre, String apellido1, String apellido2, String registro, String email) {
        super(nombre, apellido1, apellido2, email);
    }

    //getters/setters
    public void establecerCedula(String matricula) { this.cedula.set(matricula); }
    public String obtenerCedula() {return this.cedula.get();}
    public StringProperty obtenerCedulaProperty() {return this.cedula;}

    private StringProperty cedula = new SimpleStringProperty();
}
