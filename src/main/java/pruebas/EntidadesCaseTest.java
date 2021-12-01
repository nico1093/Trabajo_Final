package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import SEM.Aplication.App;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntidadesCaseTest {
	
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private SEM sem;
	private Comercio comercio;
	private App app;
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();	
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		System.setOut(new PrintStream(outContent));
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		app = new App(1153276406, "ABC123");
		comercio.recargarAplicativo(1153276406, 2000);
	}
	
	
	@Test
	public void seInicioUnEstacionamientoPorCompra() {
		comercio.generarEstacionamiento("ABC123", 2);
	}

	@Test
	public void seInicioUnEstacionamientoVirtual() {
		app.setUbicacionGPS(zona);
		app.inicarEstacionamiento();
		Assertions.assertNotNull(sem.getEstacionamientos().get(app.getPatente()));
	}


	@Test
	public void seFinalizoUnEstacionamiento(){
		app.setUbicacionGPS(zona);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();
		Assertions.assertNull(sem.getEstacionamientos().get(app.getPatente()));
	}
}
