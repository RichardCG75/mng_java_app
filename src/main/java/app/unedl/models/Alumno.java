package app.unedl.models;

//Define un Alumno que pertenece a UNEDL
public class Alumno extends MiembroUNEDL {

    public Alumno(){    super();   }
    public Alumno(int id, String nombre, String apellido1, String apellido2, int matricula) {
        super(id, nombre, apellido1, apellido2);
    }

    //getters/setters
    public int obtenerMatricula() {
        return this.matricula;
    }
    public void establecerMatricula(int matricula) {
        this.matricula = matricula;
    }

    private int matricula;
}
