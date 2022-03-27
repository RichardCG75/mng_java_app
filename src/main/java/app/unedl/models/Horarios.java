package app.unedl.models;

//Define el horario de un miembro de la institucion (MiembroUNEDL)
public class Horarios {

    //TODO: code horarios
    public Horarios(){

    }

    /**
     * Establece la configuracion de una jornada (dia) en el horario (semana) de un MiembroUNEDL
     * @param dia dia de la semana
     * @param entrada hora de entrada
     * @param salida hora de salida
     */
    public void establecerDia(Dias dia,int entrada, int salida){
        this.jornadas[dia.obtenerValor()].establecerDia(dia);
        this.jornadas[dia.obtenerValor()].establecerEntrada(entrada);
        this.jornadas[dia.obtenerValor()].establecerSalida(salida);
    }

    private Jornada[] jornadas = new Jornada[7];
}
