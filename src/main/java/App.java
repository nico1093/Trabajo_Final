package main.java;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class App implements MovementSensor{

	private Integer numero;
	private String patente;
	private EstadoDeMovimiento estado; /*State*/
	private ModoDeApp modo; /* Strategy */
	private boolean seInicioEstacionamiento;
	private ZonaDeEstacionamiento ubicacionGPS;
	private SEM sem = SEM.getInstance();
	private Date horaDeinicioDeEstacionamiento ;
	
	

	public App(Integer numero, String patente) {
		super();
		this.numero = numero;
		this.seInicioEstacionamiento = false;
		this.modo = new Manual();
		this.estado = new EstadoCaminando();
		this.patente = patente;
		/* Se asume que el estado del conductor al iniciar la app es camninado 
		   y que la app se inicia en modo automatico*/
	}

	@Override
	public void driving() {
		this.getEstado().llegoMensajeDriving(this);
		
	}

	@Override
	public void walking() {
		this.getEstado().llegoMensajeWalking(this);
		
	}
	
	public void cambiarModoAutomatico() {
		this.setModo(new Automatico());
	}
	
	public void cambiarModoManual() {
		this.setModo(new Manual());
	}
	
	
	protected void mostrarAlertaDeIncioDeEstacionamiento() {
		System.out.print("No se inicio el estacionamiento");
		
	}
	
	protected void mostrarAlertaDeFinalizacionDeEstacionamiento() {
		System.out.print("No se finalizo el estacionamiento");
		
	}
	
	public double saldoDeUsuario() {
		return sem.saldoDeUsuario(this.getNumero());
	}
	
	
	public void inicarEstacionamiento() {
		if(sem.tieneSaldoSuficiente(numero) && this.seEncuentraEnUnaZonaEstacionamiento()) {
			this.setSeInicioEstacionamiento(true);
			this.registrarHora();
			sem.iniciarEstacionamientoVirtual(this.getPatente(), this.getUbicacionGPS(), this.getNumero());
			System.out.print(this.getHoraInicioDeEstacionamiento());
			System.out.print(this.horaMaximaDeEstacionamiento());
		}
		else {
			System.out.print("saldo insuficiente. Estacionamiento no permitido");
		}
	}
	
	  boolean seEncuentraEnUnaZonaEstacionamiento() {
		  if (this.getUbicacionGPS() == null) {
			  return false;
		  }
		  else {
			  return this.getUbicacionGPS().seEncuentraEnZonaDeEstacionamiento();
			   }
	}

	private Date horaMaximaDeEstacionamiento() {
		int horaMaxima = (int) Math.min( (this.getHoraInicioDeEstacionamiento().getHours() + (this.saldoDeUsuario() / sem.getPrecioPorHora())) , 20);
		Date now = new Date();
		now.setHours(horaMaxima);
		return  now ;
	}
	
	private void registrarHora() {
		this.setHoraDeInicioDeEstacionamiento(new Date());
	}


	public void finalizarEstacionamiento() {
		if(this.isSeInicioEstacionamiento()) {
			this.setSeInicioEstacionamiento(false);
			sem.finalizarEstacionamientoVirtual(patente);
			System.out.print(this.getHoraInicioDeEstacionamiento());
			System.out.print(this.horaActual());
			System.out.print(this.horaActual().getHours() - this.getHoraInicioDeEstacionamiento().getHours());
			System.out.print(this.costeTotalDeEstacionamiento());
			
		}		
	}
	
	private double costeTotalDeEstacionamiento() {
		return (this.horaActual().getHours() - this.getHoraInicioDeEstacionamiento().getHours()) * sem.getPrecioPorHora();
	}

	private Date horaActual() {
		return new Date();
	}

	
	Integer getNumero() {
		return numero;
	}
	
	String getPatente() {
		return patente;
	}
	
	public EstadoDeMovimiento getEstado() {
		return this.estado;
	}
	
	public ModoDeApp getModo() {
		return this.modo;
	}

	boolean isSeInicioEstacionamiento() {
		return seInicioEstacionamiento;
	}

	private void setSeInicioEstacionamiento(boolean seInicioEstacionamiento) {
		this.seInicioEstacionamiento = seInicioEstacionamiento;
	}

	public void setEstado(EstadoDeMovimiento estado) {
		this.estado = estado;
	}

	public void setModo(ModoDeApp modo) {
		this.modo = modo;
	}

	private ZonaDeEstacionamiento getUbicacionGPS() {
		return ubicacionGPS;
	}
	
	/* Este metodo es solo para testear en realidad la aplicacion le pedira a su gps la ubicacion*/
	public void setUbicacionGPS(ZonaDeEstacionamiento ubicacionGPS) {
		this.ubicacionGPS = ubicacionGPS;
	}
	
	private Date getHoraInicioDeEstacionamiento() {
		return horaDeinicioDeEstacionamiento;
	}
	
	private void setHoraDeInicioDeEstacionamiento(Date date) {
		this.horaDeinicioDeEstacionamiento = date;
	}
	
	
	
}
