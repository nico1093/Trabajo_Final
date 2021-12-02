package main.java;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import main.java.SEM.SEM;
import main.java.SEM.Aplication.App;
import main.java.SEM.Aplication.IPantalla;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.Estacionamiento;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InspectorTestCase {
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private SEM sem;
	private Comercio comercio;
	private IPantalla pantalla;
	private App app;
	
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		pantalla = mock(IPantalla.class);
		app = new App(1145648612, "AB 123 CD",pantalla);
		comercio.recargarAplicativo(1145648612, 2000);
		sem.setPrecioPorHora( (double) 40);
	}
	

	@Test
	public void inspectorIncial() {
		Inspector encargado = new Inspector(zona);
		Assertions.assertEquals(encargado.getZonaEncargada(),zona);
	}

	@Test
	public void inspectorInspeccionaUnaPatente() {
		Inspector encargado = new Inspector(zona);
		Estacionamiento hospedado = null;
		app.entroAZonaDeEstacionamiento(zona);
		app.inicarEstacionamiento();
		for(Estacionamiento estacionado : sem.getEstacionamientos().get(app.getPatente()).getEstacionados()){
			if(estacionado.getPatente().equals(app.getPatente())){
				hospedado = estacionado;
			}
		}
		hospedado.anularValidez();
		encargado.inspeccionarEstacionamiento(app.getPatente());
		Assertions.assertTrue(sem.getInfracciones().size() > 0);
	}
	
	@Test
	public void testInspectorEstaAsociadoAlSem() {
		Inspector encargado = new Inspector(zona);
		assertEquals(encargado.getSem(), sem);
	}
}
