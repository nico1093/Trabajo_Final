package main.java.EstadosModos;

import main.java.Controllers.EstadoDeMovimiento;
import main.java.SEM.Aplication.App;

public class EstadoCaminando implements EstadoDeMovimiento {

	@Override
	public void alertaDriving(App app) {
		app.cambioDeCaminarAConducir();
	}

	@Override
	public void alertaWalking(App app) {
		// Si llega este mensaje cuando esta caminando no pasa nada
		
	}

}
