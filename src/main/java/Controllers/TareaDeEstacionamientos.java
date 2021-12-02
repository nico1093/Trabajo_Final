package Controllers;

import SEM.Estacionamiento.Estacionamiento;
import SEM.SEM;

import java.util.TimerTask;

public class TareaDeEstacionamientos extends TimerTask {
	
	private Estacionamiento estacionamiento;
	private SEM sem;

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
		sem = SEM.getInstancia();
	}

}
