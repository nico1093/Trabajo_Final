public class Automatico implements ModoDeApp {


	@Override
	public void conductorCambioDeCaminarAConducir(App app) {
		if(app.isSeInicioEstacionamiento()) {
			app.finalizarEstacionamiento();
		}
		
	}

	@Override
	public void conductorCambioDeConducirACaminar(App app) {
		if(app.seEncuentraEnUnaZonaEstacionamiento() && !app.isSeInicioEstacionamiento()) {
			app.inicarEstacionamiento();
		}
	}


}
