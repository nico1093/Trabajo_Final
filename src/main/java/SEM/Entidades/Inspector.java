package main.java.SEM.Entidades;

import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.SEM.SEM;

public class Inspector {
    private final SEM sem = SEM.getInstance();
    private ZonaDeEstacionamiento zonaEncargada;

    public Inspector(ZonaDeEstacionamiento zona){
		this.zonaEncargada = zona;
    }

    public void inspeccionarEstacionamiento(String patente){
        /**
         * Este mensaje verificara si el estacionamiento se encuentra en validez. En el caso que no se encuentre
         * valido este generara una infraccion.
         */
        if(!sem.esValidoElEstacionamiento(patente)){
            sem.generarInfraccion(patente, this.zonaEncargada, this);
        }
    }
    


	public ZonaDeEstacionamiento getZonaEncargada() {
		return this.zonaEncargada;
	}


	public SEM getSem() {
		return this.sem;
	}
    
    

}
