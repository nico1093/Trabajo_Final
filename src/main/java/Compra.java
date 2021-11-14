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

	public Date getFecha() {
		return fecha;
	}

	public int getNroControl() {
		return nroControl;
	}


	public Comercio getComercio() {
		return comercio;
	}

	
	
	

}
