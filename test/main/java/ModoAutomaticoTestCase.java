package main.java;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.EstadosModos.Automatico;
import main.java.SEM.Aplication.App;

public class ModoAutomaticoTestCase {
	private App app;
	private Automatico modo;
	
	@BeforeEach
	public void setUp() {
		app = mock(App.class);
		modo = new Automatico();
	}
	
	@Test
	public void testAlLlegarElMensajeConductorCambioDeCaminarAConducir() {
		modo.conductorCambioDeCaminarAConducir(app);
		Mockito.verify(app).finalizarEstacionamiento();
	}
	
	@Test
	public void testAlLlegarConductorCambioDeConducirACaminar() {
		modo.conductorCambioDeConducirACaminar(app);
		Mockito.verify(app).inicarEstacionamiento();
	}
}
