package main.java;

import java.util.Date;
import java.util.Timer;

public abstract class Estacionamiento {
    private String patente;
    private SEM sem = SEM.getInstance();
    private Date inicio = new Date();
    protected Date fin;
    private boolean validezEstacionamiento = true;
    Timer timer = new Timer();
    
    public abstract void activarSeguimiento();
    
    public Estacionamiento(String patente){
    	this.patente = patente;
    }
    
    public Date getFin() {
		return fin;
	}


	public void setFin(Date fin) {
		this.fin = fin;
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
        this.getSem().notificarFinalizacionDeEstacionamiento(this);;
        timer.cancel();
    }
    
    public boolean esVigente() {
		return this.isValidezEstacionamiento();
	}

	protected abstract void revisarValidez();
	

}
