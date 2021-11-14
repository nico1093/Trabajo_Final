package main.java;

import java.util.Date;
import java.util.Timer;

public class EstacionamientoPorCompra extends Estacionamiento {
    private int horasFijas;
    private CompraPorHoraPuntual compra;

    public EstacionamientoPorCompra(String patente, int horasFijas, CompraPorHoraPuntual compra){
        super(patente);
        this.horasFijas = horasFijas;
        this.compra = compra;
        this.fin = new Date(super.getInicio().getHours() + horasFijas);
    }

	@Override
	public boolean esVigente() {
		if ( this.fin.compareTo(new Date()) <= 0 ) {
			return this.isValidezEstacionamiento();
		}
		else {
			this.anularValidez();
			return this.isValidezEstacionamiento();
		}
	}

	@Override
	protected void revisarValidez() {
		this.anularValidez();
	}

	@Override
	public void activarSeguimiento() {
	    	Timer timer = new Timer();
	    	TareaDeEstacionamientos tarea = new TareaDeEstacionamientos(this);
	    	timer.schedule(tarea, 0, this.horasFijas);    		
	}
	
}
