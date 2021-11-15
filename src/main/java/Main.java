package main.java;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String []args){
    	SEM sem = SEM.getInstance();
		sem.reset();	
		Inspector inspector = new Inspector();
		ZonaDeEstacionamiento zona = new ZonaDeEstacionamiento(SEM.getInstance(), inspector, new ArrayList<Comercio>());
		Comercio comercio = new Comercio("Kiosco", zona);
		zona.getComercios().add(comercio);
		EntidadObservadora entidad = new EntidadObservadora();
		sem.suscribirEntidad(entidad);
		App app = new App(1153276406, "ABC123");
    	
    	
    	app.setUbicacionGPS(zona);
		comercio.recargarAplicativo(1153276406, 200);
		app.inicarEstacionamiento();
		app.finalizarEstacionamiento();

    }
}
