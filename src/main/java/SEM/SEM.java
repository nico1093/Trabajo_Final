package main.java.SEM;

import main.java.Controllers.*;
import main.java.SEM.Compras.*;
import main.java.SEM.Entidades.*;
import main.java.SEM.Estacionamiento.Estacionamiento;
import main.java.SEM.Estacionamiento.EstacionamientoPorCompra;
import main.java.SEM.Estacionamiento.EstacionamientoVirtual;
import main.java.SEM.Estacionamiento.ZonaDeEstacionamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SEM {
    private static SEM instancia;
    private List<Infraccion> infracciones ;
    private Double precioPorHora ;
    /**
      El string(patente) es la forma univoca de identificar un vehiculo estacionado en esa zona.
      Y el mismo vehiculo no se podra visualizar en dos zonas de estacionamiento simultaneamente.
     */
    private HashMap<String, ZonaDeEstacionamiento> estacionamientos ;
    /**
     * El key int es la relacion de un numero telefonico y el value es el saldo que corresponde al
     * numero telefonico.
     * */
    private HashMap<Integer, Double> registroSaldo ;
    private List<Compra> compras ;
    private Set<ServicioDeAlerta> listeners ;

    private SEM(){
    	this.precioPorHora =  40.0;
    	this.estacionamientos =  new HashMap<String,ZonaDeEstacionamiento>();
    	this.registroSaldo =  new HashMap<Integer, Double>();
    	this.infracciones =  new ArrayList<Infraccion>();
    	this.compras = new ArrayList<Compra>();
    	this.listeners = new HashSet<ServicioDeAlerta>();
    	
    }

    public static SEM getInstance(){
        if(instancia == null){
            instancia = new SEM();
        }
        return instancia;
    }

    public void iniciarEstacionamientoPorCompra(String patente, ZonaDeEstacionamiento zona,int horas, Comercio puntoDeVenta){
    	this.estacionamientos.put(patente, zona);
    	EstacionamientoPorCompra estacionamiento = new EstacionamientoPorCompra(patente, horas, this.agregarCompraDeHoras(patente, horas, puntoDeVenta));
    	zona.agregarEstacionamiento(estacionamiento);
    	this.notificarInicioDeEstacionamiento(estacionamiento);
    }
    
    public void notificarInicioDeEstacionamiento(Estacionamiento estacionamiento) {
    	this.getListeners().stream().forEach(l -> l.seInicioEstacionamiento(estacionamiento));
    	
    }
    
    private CompraPorHoraPuntual agregarCompraDeHoras(String patente, int horas, Comercio puntoDeVenta) {
    	/* Suma 1 al numero de control de la ultima compra */
    	CompraPorHoraPuntual compra = new CompraPorHoraPuntual(horas, this.proximoNumeroDeCompra(), puntoDeVenta);
    	this.getCompras().add(compra);
    	return compra;
    }
    
    public void iniciarEstacionamientoVirtual(String patente, ZonaDeEstacionamiento zona, Integer numero){
    	this.estacionamientos.put(patente, zona);
    	Estacionamiento estacionamiento = new EstacionamientoVirtual(patente, numero);
    	zona.agregarEstacionamiento(estacionamiento);
    	this.notificarInicioDeEstacionamiento(estacionamiento);
    }
    
    public void notificarFinalizacionDeEstacionamiento(Estacionamiento estacionamiento) {
    	
    	this.getListeners().stream().forEach(l -> l.seFinalizoEstacionamiento(estacionamiento)) ;
    	
    }

    public Set<ServicioDeAlerta> getListeners() {
		return this.listeners; 
	}

	public boolean esValidoElEstacionamiento(String patente){
		boolean esVigente;
		if (this.getEstacionamientos().containsKey(patente)) {
			ZonaDeEstacionamiento zona = this.getEstacionamientos().get(patente);	
			esVigente = zona.estacionamientoEsVigente(patente);
		}
		else {
			esVigente = false;
		}
    	return esVigente;
    }


	public void generarPagoVirtual(int number, double monto) {
        /**
          El usuario debe estar registrado con un mondo.
         */
        this.registroSaldo.put(number, registroSaldo.get(number) - monto);
    }

    public void cargarSaldo(int numero, double saldo, Comercio comercio){
        if(this.registroSaldo.containsKey(numero)){
            this.registroSaldo.put(numero, registroSaldo.get(numero) + saldo);
        }else{
            this.registroSaldo.put(numero, saldo);
        }
        	this.agregarCompraDeRecarga(numero,saldo,comercio);
    }

    private void agregarCompraDeRecarga(int numero, double saldo, Comercio comercio) {
		Compra compra = new CompraPorRecarga(this.proximoNumeroDeCompra(), comercio, numero, saldo);
    	this.getCompras().add(compra);
		this.notifcarRecarga(compra);
	}

    private void notifcarRecarga(Compra compra) {
    	this.getListeners().stream().forEach(l -> l.seRealizoUnaRecarga(compra));
	}

	public boolean tieneSaldoSuficiente(Integer numero) {
    	return this.getRegistroSaldo().containsKey(numero) && this.saldoDeUsuario(numero) >= this.getPrecioPorHora();
    }
    
	public void finalizarEstacionamientoVirtual(String patente){
       ZonaDeEstacionamiento zona = this.getEstacionamientos().get(patente);
       EstacionamientoVirtual estacionamiento = (EstacionamientoVirtual) zona.estacionamientoDe(patente);
       estacionamiento.finalizar();
       this.getEstacionamientos().remove(patente);
    }

    public void generarInfraccion(String patente, ZonaDeEstacionamiento zonaEncargada, Inspector inspector){
        Infraccion infraccion = new Infraccion(patente, zonaEncargada, inspector);
        this.infracciones.add(infraccion);
    }

    public double saldoDeUsuario(Integer numero) {
		return this.getRegistroSaldo().containsKey(numero) ? this.getRegistroSaldo().get(numero) : 0;
    }
    
    public void suscribirEntidad(ServicioDeAlerta servicio) {
    	this.listeners.add(servicio);
    }
    
    public List<Infraccion> getInfracciones() {
    	return infracciones;
    }
    
    public HashMap<String, ZonaDeEstacionamiento> getEstacionamientos() {
    	return estacionamientos;
    }
    
    public List<Compra> getCompras() {
    	return this.compras;
    }

	public Double getPrecioPorHora() {
		return precioPorHora;
	}

	public void setPrecioPorHora(Double precioPorHora) {
		this.precioPorHora = precioPorHora;
	}
	
	private int proximoNumeroDeCompra() {
		int numeroDeCompra;
		if (this.getCompras().isEmpty()) {
			 numeroDeCompra = 1;
		}
		else {
			numeroDeCompra = this.getCompras().get(this.getCompras().size() -1 ).getNroControl() + 1;
			}
		return numeroDeCompra;
	}
	public HashMap<Integer, Double> getRegistroSaldo() {
		return registroSaldo;
	}
	

	public void reset() {
		this.estacionamientos = new HashMap<String,ZonaDeEstacionamiento>();
		this.infracciones = new ArrayList<Infraccion>();
		this.registroSaldo = new HashMap<Integer, Double>();
		this.compras = new  ArrayList<Compra>();
		this.listeners = new HashSet<ServicioDeAlerta>(); 
		
	}
	



}
