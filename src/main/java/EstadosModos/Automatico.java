package EstadosModos;

import Controllers.ModoDeApp;
import SEM.Aplication.App;

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
