package main.java.EstadosModos;

import main.java.Controllers.EstadoDeMovimiento;
import main.java.SEM.Aplication.App;

public class EstadoConduciendo implements EstadoDeMovimiento {

	@Override
	public void alertaDriving(App app) {
		// No hace nada
	}

	@Override
	public void alertaWalking(App app) {
		app.cambioDeConducirACaminar();
	}

}
