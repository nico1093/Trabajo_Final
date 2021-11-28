package EstadosModos;

import Controllers.ModoDeApp;
import SEM.Aplication.App;

public class Manual implements ModoDeApp {


	@Override
	public void conductorCambioDeCaminarAConducir(App app) {
		if(app.isSeInicioEstacionamiento()) {
			app.mostrarAlertaDeFinalizacionDeEstacionamiento();
		}
		
	}

	@Override
	public void conductorCambioDeConducirACaminar(App app) {
		if(!app.isSeInicioEstacionamiento()) {
			app.mostrarAlertaDeIncioDeEstacionamiento();
		}
		
	}

}
