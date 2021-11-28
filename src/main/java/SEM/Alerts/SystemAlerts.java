package SEM.Alerts;

import Controllers.AlertasGeneradas;
import Controllers.EstadoDeMovimiento;


public class SystemAlerts implements AlertasGeneradas {

    public String saldoInsuficienteAllert(){
        return "saldo insuficiente. SEM.Estacionamiento no permitido";
    }
    public String cambioEstadoAllert(EstadoDeMovimiento estado){
        return "Se a cambiado de Estado";
    }
    public String negacionDeFinalizacionEstacionamientoAllert() {
        return "No se finalizo el estacionamiento";
    }
    public String negacionDeInicioEstacionamientoAllert() {
        return "No se inicio el estacionamiento";
    }


}
