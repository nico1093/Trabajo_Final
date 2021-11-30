package main.java.EstadosModos;

import main.java.Controllers.ModoDeApp;
import main.java.SEM.Aplication.App;

public class Automatico implements ModoDeApp {


	@Override
	public void conductorCambioDeCaminarAConducir(App app) {
		if(app.isSeInicioEstacionamiento()) {
			app.finalizarEstacionamiento();
		}
		
	}

	@Override
	public void conductorCambioDeConducirACaminar(App app) {
		if(!app.isSeInicioEstacionamiento()) {
			app.inicarEstacionamiento();
		}
	}


}
