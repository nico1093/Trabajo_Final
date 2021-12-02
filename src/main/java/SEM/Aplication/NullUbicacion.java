package main.java.SEM.Aplication;

import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

public class NullUbicacion extends Ubicacion {

	@Override
	 boolean seEncuentraEnZonaDeEstacionamiento() {
		
		return false;
	}

	@Override
	 ZonaDeEstacionamiento getZona() {
		// Nunca se le va a pedir en la app la zona a esta clase porque primero verifica 
		//que se encuentra en una zona
		return null;
	}
	
	

}
