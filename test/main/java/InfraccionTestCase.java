package main.java;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.SEM.SEM;
import main.java.SEM.Aplication.App;
import main.java.SEM.Aplication.IPantalla;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

public class InfraccionTestCase {
	private ZonaDeEstacionamiento zona;
	private SEM sem;
	private Comercio comercio;
	private IPantalla pantalla;
	private Inspector inspector;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		zona = mock(ZonaDeEstacionamiento.class);
		inspector = new Inspector(zona);
		zona.getComercios().add(comercio);
		sem.reset();
	}
	
	@Test
	public void testUnaInfraccionGuardaLaPatenteBuscada() {
		inspector.inspeccionarEstacionamiento("ABC 123");
		assertEquals(sem.getInfracciones().get(0).getPatente(), "ABC 123");
	}
	
	@Test
	public void testUnaInfraccionTieneLaPatenteBuscadaAsociada() {
		inspector.inspeccionarEstacionamiento("ABC 123");
		assertEquals(sem.getInfracciones().get(0).getZona(), zona);
	}
	
	@Test
	public void testUnaInfraccionTieneAsociadoAlInspectorQueLaHizo() {
		inspector.inspeccionarEstacionamiento("ABC 123");
		assertEquals(sem.getInfracciones().get(0).getInspector(), inspector);
	}
}
