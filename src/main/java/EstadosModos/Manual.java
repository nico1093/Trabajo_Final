package main.java.EstadosModos;

import main.java.Controllers.ModoDeApp;
import main.java.SEM.Aplication.App;

public class Manual implements ModoDeApp {


	@Override
	public void conductorCambioDeCaminarAConducir(App app) {
		app.posibleAlertaDeFinalizacionDeEstacionamiento();
		
	}

	@Override
	public void conductorCambioDeConducirACaminar(App app) {
		app.posibleAlertaDeInicioDeEstacionamiento();
		
	}

}
