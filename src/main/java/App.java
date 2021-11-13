package main.java;

import java.sql.Date;
import java.util.Calendar;

public class App implements MovementSensor{

	private Integer numero;
	private String patente;
	private EstadoDeMovimiento estado; /*State*/
	private ModoDeApp modo; /* Strategy */
	private boolean seInicioEstacionamiento;
	private ZonaDeEstacionamiento ubicacionGPS;
	private SEM sem = SEM.getInstance();
	private int horaDeinicioDeEstacionamiento ;
	
	

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
			sem.iniciarEstacionamientoVirtual(this.getPatente(), this.getUbicacionGPS());
			System.out.print(this.getHoraInicioDeEstacionamiento());
			System.out.print(this.horaMaximaDeEstacionamiento());
		}
		else {
			System.out.print("saldo insuficiente. Estacionamiento no permitido");
		}
	}
	
	 boolean seEncuentraEnUnaZonaEstacionamiento() {
		// TODO Auto-generated method stub
		return false;
	}

	private int horaMaximaDeEstacionamiento() {
		
		return  (int) Math.min( (this.getHoraInicioDeEstacionamiento() + (this.saldoDeUsuario() / 40)) , 20) ;
	}
	
	private void registrarHora() {
		Calendar now = Calendar.getInstance();
		this.setHoraDeInicioDeEstacionamiento(now.get(Calendar.HOUR_OF_DAY));
	}


	public void finalizarEstacionamiento() {
		if(this.isSeInicioEstacionamiento()) {
			this.setSeInicioEstacionamiento(false);
			sem.finalizarEstacionamientoVirtual(patente);
			System.out.print(this.getHoraInicioDeEstacionamiento());
			System.out.print(this.horaActual());
			System.out.print(this.horaActual() - this.getHoraInicioDeEstacionamiento());
			System.out.print(this.costeTotalDeEstacionamiento());
			
		}		
	}
	
	private int costeTotalDeEstacionamiento() {
		return (this.horaActual() - this.getHoraInicioDeEstacionamiento()) * 40;
	}

	private int horaActual() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.HOUR_OF_DAY);
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
	
	private int getHoraInicioDeEstacionamiento() {
		return horaDeinicioDeEstacionamiento;
	}
	
	private void setHoraDeInicioDeEstacionamiento(int inicioDeEstacionamiento) {
		this.horaDeinicioDeEstacionamiento = inicioDeEstacionamiento;
	}
	

	
}
