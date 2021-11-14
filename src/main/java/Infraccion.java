package main.java;

import java.util.Date;

public class Infraccion {
    private String patente;
    private Date fechaOrigen;
    private double monto;
    private ZonaDeEstacionamiento zona;

    public Infraccion(String patente, double monto, ZonaDeEstacionamiento zonaEncargada){
        this.monto = monto;
        this.patente = patente;
        this.fechaOrigen = new Date();
        this.zona = zonaEncargada;
    }
}
