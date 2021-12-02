package main.java;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.EstadosModos.Manual;
import main.java.SEM.Aplication.App;

public class ManualTestCase {
	private App app;
	private Manual modo;
	
	@BeforeEach
	public void setUp() {
		app = mock(App.class);
		modo = new Manual();
	}
	
	@Test
	public void testAlLlegarElMensajeConductorCambioDeCaminarAConducir() {
		modo.conductorCambioDeCaminarAConducir(app);
		Mockito.verify(app).posibleAlertaDeFinalizacionDeEstacionamiento();
	}
	
	@Test
	public void testAlLlegarConductorCambioDeConducirACaminar() {
		modo.conductorCambioDeConducirACaminar(app);
		Mockito.verify(app).posibleAlertaDeInicioDeEstacionamiento();
	}
}
