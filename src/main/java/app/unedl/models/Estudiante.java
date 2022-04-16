package app.unedl.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Define un Estudiante que pertenece a UNEDL
public class Estudiante extends MiembroUNEDL {

    public Estudiante(){    super();   }
    public Estudiante(String nombre, String apellido1, String apellido2, String registro, String email) {
        super(nombre, apellido1, apellido2, email);
    }

    //getters/setters
    public void establecerMatricula(String matricula) { this.matricula.set(matricula); }
    public String obtenerMatricula() {return this.matricula.get();}
    public StringProperty obtenerMatriculaProperty() {return this.matricula;}

    private StringProperty matricula = new SimpleStringProperty();
}
