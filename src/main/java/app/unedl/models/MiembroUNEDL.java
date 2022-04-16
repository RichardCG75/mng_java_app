package app.unedl.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Define cualquier miembro que pertenece a UNEDL
public class MiembroUNEDL {

    public MiembroUNEDL(){

    }

    //TODO: code constructor
    public MiembroUNEDL(String nombre, String apellido1, String apellido2, String email){

    }

    public void establecerId(int id){ this.id.set(id); }
    public int obtenerId(){ return this.id.get(); }
    public IntegerProperty obtenerIdProperty(){ return this.id; }
    public void establecerNombre(String nombre){ this.nombre.set(nombre); }
    public String obtenerNombre(){ return this.nombre.get(); }
    public StringProperty obtenerNombreProperty(){ return this.nombre; }
    public void establecerApellido(String apellido, APELLIDO tutor){
        switch (tutor){
            case PATERNO -> this.apellido1.set(apellido);
            case MATERNO -> this.apellido2.set(apellido);
        }
    }
    public String obtenerApellido(APELLIDO tutor){ return (tutor == APELLIDO.PATERNO) ? this.apellido1.get() : this.apellido2.get(); }
    public StringProperty obtenerApellidoProperty(APELLIDO tutor){ return (tutor == APELLIDO.PATERNO) ? this.apellido1 : this.apellido2; }
    public void establecerEmail(String email){ this.email.set(email); }
    public String obtenerEmail(){ return this.email.get(); }
    public StringProperty obtenerEmailProperty(){ return this.email; }

    public enum APELLIDO{PATERNO, MATERNO};
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty apellido1 = new SimpleStringProperty();
    private StringProperty apellido2 = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty fechaAlta = new SimpleStringProperty();
    private StringProperty fechaBaja = new SimpleStringProperty();
    private Horarios[] horarios;
    private boolean estaActivo;
}
