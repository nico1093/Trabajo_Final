package main.java;
public class EstadoConduciendo implements EstadoDeMovimiento {

	@Override
	public void llegoMensajeDriving(App app) {
		// No hace nada

	}

	@Override
	public void llegoMensajeWalking(App app) {
		app.setEstado(new EstadoCaminando());
		app.getModo().conductorCambioDeConducirACaminar(app);
	}

}
