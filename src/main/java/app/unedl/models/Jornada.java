package app.unedl.models;

//Define la jornada de un Miembro UNEDL
public class Jornada {

    public Jornada(Dias dia, int entrada, int salida){
        this.dia = dia;
        this.entrada = entrada;
        this.salida = salida;
    }

    //Setters
    public void establecerDia(Dias dia){ this.dia = dia; }
    public void establecerEntrada(int entrada){ this.entrada =  entrada; }
    public void establecerSalida(int salida){ this.salida = salida; }

    //Getters
    public Dias obtenerDia(){ return this.dia; }
    public int obtenerEntrada() { return this.entrada; }
    public int obtenerSalida(){ return this.salida; }

    private Dias dia;
    private int entrada;
    private int salida;
}
