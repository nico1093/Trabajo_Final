package main.java.SEM.Estacionamiento;

import main.java.Controllers.TareaDeEstacionamientos;

import java.util.Date;

public class EstacionamientoVirtual extends Estacionamiento {
    private int phone;
    

    public EstacionamientoVirtual(String patente, int phone){
        super(patente);
        this.phone = phone;
        this.activarSeguimiento();
    }

    public int getPhone() {
        return phone;
    }


	@Override
	public void revisarValidez() {
		if(super.getSem().saldoDeUsuario(phone) > this.getSem().getPrecioPorHora()){
            getSem().generarPagoVirtual(phone, this.getSem().getPrecioPorHora());
        }else{
        	finalizar();
        }
		
	}

	public void finalizar() {
		this.setFin(new Date());
		super.anularValidez();
	}

	@Override
	public void activarSeguimiento() {
	    	TareaDeEstacionamientos tarea = new TareaDeEstacionamientos(this);
	    	this.timer.scheduleAtFixedRate(tarea, 0, 1000*60*60);  	
	    }
	
	
		
}
