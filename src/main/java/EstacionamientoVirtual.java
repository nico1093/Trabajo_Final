package main.java;

import java.sql.Date;
import java.util.Calendar;
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
		System.out.println( Calendar.getInstance().getTime());
		
	}
}
