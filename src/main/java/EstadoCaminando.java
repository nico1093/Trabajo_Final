package main.java;

public class EstadoCaminando implements EstadoDeMovimiento {

	@Override
	public void llegoMensajeDriving(App app) {
		app.setEstado(new EstadoConduciendo());
		app.getModo().conductorCambioDeCaminarAConducir(app);
	}

	@Override
	public void llegoMensajeWalking(App app) {
		// Si llega este mensaje cuando esta caminando no pasa nada
		
	}
	
	

}
