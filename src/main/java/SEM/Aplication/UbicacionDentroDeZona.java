package main.java.SEM.Aplication;

import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

public class UbicacionDentroDeZona extends Ubicacion {
	private ZonaDeEstacionamiento zona;

	@Override
	 boolean seEncuentraEnZonaDeEstacionamiento() {
		// TODO Auto-generated method stub
		return this.zona.estaDentroDeLaZona();
	}

	 UbicacionDentroDeZona(ZonaDeEstacionamiento zona) {
		super();
		this.zona = zona;
	}

	@Override
	 ZonaDeEstacionamiento getZona() {
		// TODO Auto-generated method stub
		return this.zona;
	}

	
	
	
}
