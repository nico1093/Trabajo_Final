package main.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.SEM.SEM;
import main.java.SEM.Compras.CompraPorHoraPuntual;
import main.java.SEM.Compras.CompraPorRecarga;
import main.java.SEM.Entidades.Comercio;
import main.java.SEM.Entidades.Inspector;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

public class CompraDeRecargaTestCase {

	private CompraPorHoraPuntual compraPorHora;
	private ZonaDeEstacionamiento zona;
	private Inspector inspector;
	private Comercio comercio;
	private CompraPorRecarga compraPorRecarga;
	private SEM sem;
	
	@BeforeEach
	public void setUP() throws Exception {
		sem = SEM.getInstance();
		sem.reset();
		inspector = new Inspector(zona);
		zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
	}
	
	@Test
	public void testCuandoUnaCompraPorRecarga() {
		compraPorRecarga = new CompraPorRecarga(0, comercio, 1153276406, 200);
		Assertions.assertEquals(compraPorRecarga.getMonto(), 200);

	}
	
	@Test
	public void testUnaCompraPorRecargaTieneUnNumeroAsociado() {
		comercio.recargarAplicativo(1145648612, 50);
		int numeroAsociado = ((CompraPorRecarga) sem.getCompras().get(0)).getNumero();
		assertEquals(numeroAsociado, 1145648612 );
	}
}
