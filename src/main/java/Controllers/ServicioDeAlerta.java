package main.java.Controllers;

import main.java.SEM.Compras.Compra;
import main.java.SEM.Estacionamiento.Estacionamiento;

public interface ServicioDeAlerta {
	
	public void seInicioEstacionamiento(Estacionamiento estacionamiento);
	public void seFinalizoEstacionamiento(Estacionamiento estacionamiento);
	public void seRealizoUnaRecarga(Compra compra);

	

}
