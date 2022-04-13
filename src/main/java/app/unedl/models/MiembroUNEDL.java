package app.unedl.models;

import javafx.beans.property.StringProperty;

import java.util.Date;

//Define cualquier miembro que pertenece a UNEDL
public class MiembroUNEDL {

    public MiembroUNEDL(){

    }

    //TODO: code constructor
    public MiembroUNEDL(int id, String nombre, String apellido1, String apellido2){

    }

    private int id;
    private StringProperty nombre;
    private StringProperty apellidoPaterno;
    private StringProperty apellidoMaterno;
    private StringProperty fechaAlta;
    private StringProperty fechaBaja;
    private Horarios[] horarios;
    private boolean estaActivo;
}
