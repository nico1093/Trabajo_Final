package SEM.Entidades;

import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;

public class Comercio {
    private String id;
    private final SEM sem = SEM.getInstance();
    private ZonaDeEstacionamiento zona;

    public Comercio(String id, ZonaDeEstacionamiento zona){
        this.id = id;
        this.zona = zona;
    }

    public void recargarAplicativo(int number, double monto){
        sem.cargarSaldo(number, monto, this);
    }

    public void generarEstacionamiento(String patente, int hours){
        sem.iniciarEstacionamientoPorCompra(patente, this.zona, hours, this);
    }

	public String getId() {
		return id;
	}

	public ZonaDeEstacionamiento getZona() {
		return zona;
	}
    
    
}
