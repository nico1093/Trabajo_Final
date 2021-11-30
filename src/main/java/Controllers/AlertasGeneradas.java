package main.java.Controllers;

public interface AlertasGeneradas {

    public String saldoInsuficienteAllert();
    public String cambioEstadoAllert(EstadoDeMovimiento estado);
    public String negacionDeFinalizacionEstacionamientoAllert();
    public String negacionDeInicioEstacionamientoAllert();
}
