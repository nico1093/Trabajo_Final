package main.java;

import java.util.TimerTask;

public class TareaDeEstacionamientos extends TimerTask {
	
	private Estacionamiento estacionamiento;

	@Override
	public void run() {
		
		this.getEstacionamiento().revisarValidez();
		
	}

	public Estacionamiento getEstacionamiento() {
		return estacionamiento;
	}

	public TareaDeEstacionamientos(Estacionamiento estacionamiento) {
		super();
		this.estacionamiento = estacionamiento;
	}

}
