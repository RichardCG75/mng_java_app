package app.unedl.models;

//Define un Alumno que pertenece a UNEDL
public class Alumno extends MiembroUNEDL {

    public Alumno(int id, String nombre, String apellido1, String apellido2, int matricula) {
        super(id, nombre, apellido1, apellido2);
    }

    //getters/setters
    public int getMatricula() {
        return this.matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    private int matricula;
}
