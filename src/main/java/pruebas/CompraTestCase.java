package pruebas;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Date;

import SEM.Compras.CompraPorHoraPuntual;
import SEM.Compras.CompraPorRecarga;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompraTestCase {

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
	public void testCuandoUnaCompraPorHorasFijasSeInicia() {
		compraPorHora = new CompraPorHoraPuntual(2, 0, comercio);
		assertEquals(2, compraPorHora.getHorasCompradas());
	}
	
	@Test
	public void testUnaCompraTieneUnNumeroDeControlAlEntrarEnElSem() {
		comercio.generarEstacionamiento("ABC 123 ", 2);
		comercio.recargarAplicativo(1145648612, 50);
		Assertions.assertEquals(sem.getCompras().get(0).getNroControl(), 1);
		Assertions.assertEquals(sem.getCompras().get(1).getNroControl(), 2);
	}
	
	@Test
	public void testUnaCompraTieneAsociadaElComercioDondeSeHizo() {
		comercio.generarEstacionamiento("ABC 123 ", 2);
		Assertions.assertEquals(sem.getCompras().get(0).getComercio() , comercio );
	}
	
	@Test
	public void testUnaCompraTieneAsociadoLaFechaEnQueSeHizo() {
		comercio.generarEstacionamiento("AAS 142", 3);
		Assertions.assertTrue(sem.getCompras().get(0).getFecha() instanceof Date);
	}
	
	@Test
	public void testUnaCompraPorRecargaTieneUnNumeroAsociado() {
		comercio.recargarAplicativo(1145648612, 50);
		int numeroAsociado = ((CompraPorRecarga) sem.getCompras().get(0)).getNumero();
		Assertions.assertEquals(numeroAsociado, 1145648612 );
	}
	
}
