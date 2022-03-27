package app.unedl.models;

//Define los dias de la semana
public enum Dias {
    LUNES(0), MARTES(1), MIERCOLES(2), JUEVES(3), VIERNES(4), SABADO(5), DOMINGO(6);

    Dias(int valor){ this.valor = valor; }

    public int obtenerValor(){ return  this.valor; }

    private final int valor;
}
