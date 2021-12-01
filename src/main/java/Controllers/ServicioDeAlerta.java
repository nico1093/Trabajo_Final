package Controllers;

import SEM.Compras.Compra;
import SEM.Estacionamiento.Estacionamiento;

public interface ServicioDeAlerta {
	
	public void seInicioEstacionamiento(Estacionamiento estacionamiento);
	public void seFinalizoEstacionamiento(Estacionamiento estacionamiento);
	public void seRealizoUnaRecarga(Compra compra);
	public void mensajeDeAlertaAlUsuario(String mensaje);


}
