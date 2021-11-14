package main.java;

import java.util.Date;
import java.util.Timer;

public abstract class Estacionamiento {
    private String patente;
    private SEM sem = SEM.getInstance();
    private Date inicio;
    protected Date fin;
    
    public Date getFin() {
		return fin;
	}


	public void setFin(Date fin) {
		this.fin = fin;
	}

	private boolean validezEstacionamiento = true;
    
    public abstract void activarSeguimiento();
    

    public Estacionamiento(String patente){
        this.patente = patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getPatente() {
        return patente;
    }

    public Date getInicio() {
        return inicio;
    }

    public SEM getSem() {
        return sem;
    }

    public boolean isValidezEstacionamiento() {
        return validezEstacionamiento;
    }

    public void anularValidez(){
        this.validezEstacionamiento = false;
        this.getSem().finalizoUnEstacionamiento(this);
    }
    
    public abstract boolean esVigente();

	protected abstract void revisarValidez();
	
	public static void main(String[] args) {
		Estacionamiento estacionamiento = new EstacionamientoVirtual("ABC123", 1646864896);
		estacionamiento.activarSeguimiento();
	}

}
