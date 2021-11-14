package main.java;
import java.util.Date;

public abstract class Compra { // registro de compra de estacionamiento
	private int nroControl;
	private Comercio comercio;
	private Date fecha;
	
	public Compra(int nroControl, Comercio comercio) {
		this.nroControl= nroControl;
		this.comercio= comercio;
		this.fecha= new Date();
	}

	public int getNroControl() {
		return nroControl;
	}

	public void setNroControl(int nroControl) {
		this.nroControl = nroControl;
	}

	public Comercio getComercio() {
		return comercio;
	}

	public void setComercio(Comercio comercio) {
		this.comercio = comercio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}
	
	
	

}
