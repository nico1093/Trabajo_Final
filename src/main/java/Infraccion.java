package main.java;

import java.util.Date;

public class Infraccion {
	private SEM sem = SEM.getInstance();
    private String patente;
    private Date fechaOrigen;
    private ZonaDeEstacionamiento zonaEs;
   
    public Infraccion(String patente, ZonaDeEstacionamiento zonaEs){
        
        this.patente = patente;
        this.fechaOrigen = new Date();
        this.zonaEs= zonaEs;
        
    }
    
    	
	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Date getFechaOrigen() {
		return fechaOrigen;
	}

	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}
	
	 public SEM getSem() {
	        return sem;
	    }


	public ZonaDeEstacionamiento getZonaEs() {
		return zonaEs;
	}


	public void setZonaEs(ZonaDeEstacionamiento zonaEs) {
		this.zonaEs = zonaEs;
	}
	 
	 

	
	
}
