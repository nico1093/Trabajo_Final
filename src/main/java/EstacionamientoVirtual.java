package main.java;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EstacionamientoVirtual extends Estacionamiento {
    private int phone;
    

    public EstacionamientoVirtual(String patente, int phone){
        super(patente);
        this.phone = phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPhone() {
        return phone;
    }


    public void cobrarHoraDeEstacionamiento(){
        if(super.getSem().saldoDeUsuario(phone) > this.getSem().getPrecioPorHora()){
            getSem().generarPagoVirtual(phone, this.getSem().getPrecioPorHora());
        }else{
            super.anularValidez();
            /*Cambiar la hora de finalizacion*/
        }
    }

	@Override
	public boolean esVigente() {
		return this.isValidezEstacionamiento();
	}

	@Override
	protected void revisarValidez() {
		if(super.getSem().saldoDeUsuario(phone) > this.getSem().getPrecioPorHora()){
            getSem().generarPagoVirtual(phone, this.getSem().getPrecioPorHora());
        }else{
        	finalizar();
            /*Cambiar la hora de finalizacion*/
        }
	}

	private void finalizar() {
		this.setFin(new Date());
		super.anularValidez();
	}

	@Override
	public void activarSeguimiento() {
	    	Timer timer = new Timer();
	    	TareaDeEstacionamientos tarea = new TareaDeEstacionamientos(this);
	    	timer.schedule(tarea, 0, 1000 * 60 * 60);  	
	    }
		
}
