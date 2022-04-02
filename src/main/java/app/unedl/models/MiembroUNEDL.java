package app.unedl.models;

import javafx.beans.property.StringProperty;

import java.util.Date;

//Define cualquier miembro que pertenece a UNEDL
public class MiembroUNEDL {

    //TODO: code constructor
    public MiembroUNEDL(int id, String nombre, String apellido1, String apellido2){

    }

    private int id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaAlta;
    private Date fechaBaja;
    private Horarios[] horarios;
    private boolean estaActivo;
}
