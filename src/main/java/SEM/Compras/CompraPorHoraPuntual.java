package main.java.SEM.Compras;

import main.java.SEM.Entidades.Comercio;

public class CompraPorHoraPuntual extends Compra {
	private int HorasCompradas;

	public CompraPorHoraPuntual(int horasCompradas, int nroControl, Comercio comercio) {
		super(nroControl, comercio);
		HorasCompradas = horasCompradas;
	}

	public int getHorasCompradas() {
		return HorasCompradas;
	}


	
	

}
