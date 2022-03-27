package app.unedl.models;

import java.util.Date;

//Define cualquier individuo que pertenezca a la institucion UNEDL
public class MiembroUNEDL {

    //TODO: code constructor
    public MiembroUNEDL(int id, String nombre, String apellido1, String apellido2){

    }

//    public Jornada obtenerJornadaLunes(){
//        null;
//    }

    private int id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaAlta;
    private Date fechaBaja;
    private Horarios[] horarios;
    private boolean estaActivo;
}
