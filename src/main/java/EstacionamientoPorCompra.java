package main.java;

import java.util.Date;

public class EstacionamientoPorCompra extends Estacionamiento {
    private int horasFijas;
    private Date fin;
    private CompraPorHoraPuntual compra;

    public EstacionamientoPorCompra(String patente, int horasFijas, CompraPorHoraPuntual compra){
        super(patente);
        this.horasFijas = horasFijas;
        this.compra = compra;
        fin = new Date(super.getInicio().getHours() + horasFijas);
    }

	@Override
	public boolean esVigente() {
		if ( this.fin.compareTo(new Date()) <= 0 ) {
			return this.isValidezEstacionamiento();
		}
		else {
			this.anularValidez();
			return this.isValidezEstacionamiento();
		}
	}
   
}
