package SEM.Aplication;

import Controllers.EstadoDeMovimiento;
import Controllers.ModoDeApp;
import Controllers.MovementSensor;
import EstadosModos.Automatico;
import EstadosModos.EstadoCaminando;
import EstadosModos.Manual;
import SEM.Estacionamiento.ZonaDeEstacionamiento;
import SEM.SEM;

import java.util.Date;

public class App implements MovementSensor {

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
	
	
	public void mostrarAlertaDeIncioDeEstacionamiento() {
		System.out.print("No se inicio el estacionamiento");
	}
	
	public void mostrarAlertaDeFinalizacionDeEstacionamiento() {
		System.out.print("No se finalizo el estacionamiento");
	}
	
	public double saldoDeUsuario() {
		return sem.saldoDeUsuario(this.getNumero());
	}
	
	
	public void inicarEstacionamiento() {
		if(sem.tieneSaldoSuficiente(numero)) {
			this.setSeInicioEstacionamiento(true);
			this.registrarHora();
			sem.iniciarEstacionamientoVirtual(this.getPatente(), this.getUbicacionGPS(), this.getNumero());
		}
		else if(!sem.tieneSaldoSuficiente(numero)) {
			System.out.print("saldo insuficiente. SEM.Estacionamiento no permitido");
		}
	  }

	public ZonaDeEstacionamiento seEncuentraEnLaZonaEstacionamiento() {
		//return this.getUbicacionGPS().seEncuentraEnZonaDeEstacionamiento();
		return sem.getEstacionamientos().get(this.patente);
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
			System.out.println(this.getHoraInicioDeEstacionamiento());
			System.out.println(this.horaActual());
			System.out.println(this.horaActual().getHours() - this.getHoraInicioDeEstacionamiento().getHours());
			System.out.println(this.costeTotalDeEstacionamiento());
			
		}		
	}
	
	private double costeTotalDeEstacionamiento() {
		return Math.max ((this.horaActual().getHours() - this.getHoraInicioDeEstacionamiento().getHours()) * sem.getPrecioPorHora(), 40);
	}

	private Date horaActual() {
		return new Date();
	}

	
	public Integer getNumero() {
		return numero;
	}
	
	public String getPatente() {
		return patente;
	}
	
	public EstadoDeMovimiento getEstado() {
		return this.estado;
	}
	
	public ModoDeApp getModo() {
		return this.modo;
	}

	public boolean isSeInicioEstacionamiento() {
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
