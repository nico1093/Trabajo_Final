package main.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import main.java.SEM.Aplication.App;
import main.java.SEM.Aplication.IPantalla;
import main.java.SEM.Compras.Compra;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.Estacionamiento;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;
import main.java.Controllers.ServicioDeAlerta;
import main.java.SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EntidadesCaseTest {
	
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private SEM sem;
	private Comercio comercio;
	private App app;
	private IPantalla pantalla;
	private ServicioDeAlerta entidad;
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();	
		entidad = mock(ServicioDeAlerta.class);
		inspector = mock(Inspector.class);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		pantalla = mock(IPantalla.class);
		app = new App(1153276406, "ABC123",pantalla);
	}
	
	
	@Test
	public void seInicioUnEstacionamientoPorCompra() {
		sem.suscribirEntidad(entidad);
		comercio.generarEstacionamiento("ABC123", 2);
		Mockito.verify(entidad).seInicioEstacionamiento(isA(Estacionamiento.class));
	}

	@Test
	public void seInicioUnEstacionamientoVirtual() {
		sem.suscribirEntidad(entidad);
		comercio.recargarAplicativo(1153276406, 200);
		Mockito.verify(entidad).seRealizoUnaRecarga(isA(Compra.class));
		sem.getListeners().clear();
	}
	
	
	@Test
	public void seFinalizoUnEstacionamiento(){

		sem.suscribirEntidad(entidad);
		app.entroAZonaDeEstacionamiento(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Mockito.verify(entidad).seFinalizoEstacionamiento(isA(Estacionamiento.class));
	}
	
	
}
