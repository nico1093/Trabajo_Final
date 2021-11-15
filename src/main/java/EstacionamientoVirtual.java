package main.java;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EstacionamientoVirtual extends Estacionamiento {
    private int phone;
    

    public EstacionamientoVirtual(String patente, int phone){
        super(patente);
        this.phone = phone;
        this.activarSeguimiento();
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPhone() {
        return phone;
    }


	@Override
	protected void revisarValidez() {
		if(super.getSem().saldoDeUsuario(phone) > this.getSem().getPrecioPorHora()){
            getSem().generarPagoVirtual(phone, this.getSem().getPrecioPorHora());
        }else{
        	finalizar();
        }
		
	}

	void finalizar() {
		this.setFin(new Date());
		super.anularValidez();
	}

	@Override
	public void activarSeguimiento() {
	    	TareaDeEstacionamientos tarea = new TareaDeEstacionamientos(this);
	    	this.timer.scheduleAtFixedRate(tarea, 0, 1000*60*60);  	
	    }
	
	
		
}
