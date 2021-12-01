package SEM;

import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;

import java.util.Date;

public class Infraccion {
    private String patente;
    private Date fechaOrigen;
    private ZonaDeEstacionamiento zona;
    private Inspector inspector;

    public Infraccion(String patente, ZonaDeEstacionamiento zonaEncargada, Inspector inspector){
        this.patente = patente;
        this.fechaOrigen = new Date();
        this.zona = zonaEncargada;
        this.inspector = inspector;
    }

    public String getPatente() {
        return patente;
    }

    public Date getFechaOrigen() {
        return fechaOrigen;
    }

    public ZonaDeEstacionamiento getZona() {
        return zona;
    }

    public Inspector getInspector() {
        return inspector;
    }
}
