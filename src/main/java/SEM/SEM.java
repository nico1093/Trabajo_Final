package SEM;

import Controllers.ServicioDeAlerta;
import SEM.Compras.Compra;
import SEM.Compras.CompraPorHoraPuntual;
import SEM.Compras.CompraPorRecarga;
import SEM.Entidades.Comercio;
import SEM.Entidades.Inspector;
import SEM.Estacionamiento.Estacionamiento;
import SEM.Estacionamiento.EstacionamientoPorCompra;
import SEM.Estacionamiento.EstacionamientoVirtual;
import SEM.Estacionamiento.ZonaDeEstacionamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SEM {
    private static SEM instancia;
    private List<Infraccion> infracciones = new ArrayList<Infraccion>();
    private final double precioPorHora =  40;
    /**
      El string(patente) es la forma univoca de identificar un vehiculo estacionado en esa zona.
      Y el mismo vehiculo no se podra visualizar en dos zonas de estacionamiento simultaneamente.
     */
    private HashMap<String, ZonaDeEstacionamiento> estacionamientos = new HashMap<String,ZonaDeEstacionamiento>();
    /**
     * El key int es la relacion de un numero telefonico y el value es el saldo que corresponde al
     * numero telefonico.
     * */
    private HashMap<Integer, Double> registroSaldo = new HashMap<Integer, Double>();
    private List<Compra> compras = new ArrayList<Compra>();
    private List<ServicioDeAlerta> listeners = new ArrayList <ServicioDeAlerta>();


    private SEM(){}

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
    	for (ServicioDeAlerta listener : this.listeners) {
    		listener.seInicioEstacionamiento(estacionamiento);
    	}
    	
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
    	for (ServicioDeAlerta listener : this.listeners) {
    		listener.seFinalizoEstacionamiento(estacionamiento);
    	}
    }

    public boolean esValidoElEstacionamiento(String patente){
        ZonaDeEstacionamiento zona = this.getEstacionamientos().get(patente);
    	return zona.estacionamientoEsVigente(patente) ;
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
            this.agregarCompraDeRecarga(numero,saldo,comercio);
        }else{
            this.registroSaldo.put(numero, saldo);
            this.agregarCompraDeRecarga(numero,saldo,comercio);
        }
    }

    private void agregarCompraDeRecarga(int numero, double saldo, Comercio comercio) {
		Compra compra = new CompraPorRecarga(this.proximoNumeroDeCompra(), comercio, numero, saldo);
    	this.getCompras().add(compra);
		this.notifcarRecarga(compra);
	}

    private void notifcarRecarga(Compra compra) {
    	for (ServicioDeAlerta listener : this.listeners) {
			listener.seRealizoUnaRecarga(compra);
		}
		
	}

	public boolean tieneSaldoSuficiente(Integer numero) {
    	return this.getRegistroSaldo().containsKey(numero) && this.saldoDeUsuario(numero) > 40;
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

	private int proximoNumeroDeCompra() {
		if (this.getCompras().isEmpty()) {
			return 1;
		}
		else {
			return this.getCompras().get(this.getCompras().size() -1 ).getNroControl() + 1;
			}
	}
	public HashMap<Integer, Double> getRegistroSaldo() {
		return registroSaldo;
	}

	public void reset() {
		this.estacionamientos = new HashMap<String,ZonaDeEstacionamiento>();
		this.infracciones = new ArrayList<Infraccion>();
		this.registroSaldo = new HashMap<Integer, Double>();
		this.compras = new  ArrayList<Compra>();
		this.listeners = new ArrayList <ServicioDeAlerta>(); 
		
		
	}


}
