package main.java;

import java.util.Timer;
import java.util.TimerTask;

public class EstacionamientoVirtual extends Estacionamiento {
    private int phone;
    
    Timer timer = new Timer ();
    
    TimerTask tarea = new TimerTask() {
		
		@Override
		public void run() {
			
		}
	};
	

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
}
