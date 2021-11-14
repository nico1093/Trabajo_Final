package main.java;

import java.util.ArrayList;
import java.util.List;

public class ZonaDeEstacionamiento {
    private SEM sem = SEM.getInstance();
    private Inspector encargado;
    private List<Comercio> comercios;
    private List<Estacionamiento> estacionados;
    
    
    
    
	public ZonaDeEstacionamiento(SEM sem, Inspector encargado, List<Comercio> comercios) {
		super();
		this.sem = sem;
		this.encargado = encargado;
		this.comercios = comercios;
		this.estacionados = new ArrayList<Estacionamiento>();;
	}




	public void agregarEstacionamiento(Estacionamiento estacionamiento) {
		this.getEstacionados().add(estacionamiento);
		
	}




	public SEM getSem() {
		return sem;
	}




	public void setSem(SEM sem) {
		this.sem = sem;
	}




	public Inspector getEncargado() {
		return encargado;
	}




	public void setEncargado(Inspector encargado) {
		this.encargado = encargado;
	}




	public List<Comercio> getComercios() {
		return comercios;
	}




	public void setComercios(List<Comercio> comercios) {
		this.comercios = comercios;
	}




	public List<Estacionamiento> getEstacionados() {
		return estacionados;
	}




	public void setEstacionados(List<Estacionamiento> estacionados) {
		this.estacionados = estacionados;
	}




	public boolean estacionamientoEsVigente(String patente) {
		for (Estacionamiento estacionamiento : this.getEstacionados()) {
	        if (estacionamiento.getPatente().equals(patente)) {
	            return estacionamiento.esVigente();
	        }
		}
		return false;
	}
}
