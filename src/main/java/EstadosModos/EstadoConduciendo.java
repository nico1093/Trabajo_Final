package EstadosModos;

import Controllers.EstadoDeMovimiento;
import SEM.Aplication.App;

public class EstadoConduciendo implements EstadoDeMovimiento {
	@Override
	public void driving(App app) {
		// No hace nada
	}

	@Override
	public void walking(App app) {
		app.cambioDeConducirACaminar();
	}
}
