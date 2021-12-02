package main.java.SEM.Estacionamiento;

import main.java.Controllers.TareaDeEstacionamientos;
import main.java.SEM.Compras.CompraPorHoraPuntual;

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
        this.activarSeguimiento();
    }

    @Override
	public void revisarValidez() {
		this.anularValidez();
	}

	@Override
	public void activarSeguimiento() {
	    	Timer timer = new Timer();
	    	TareaDeEstacionamientos tarea = new TareaDeEstacionamientos(this);
	    	timer.schedule(tarea, this.horasFijas);    		
	}
	
}
