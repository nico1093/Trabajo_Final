package main.java;

import java.sql.Date;

public class App implements MovementSensor{

	private Integer numero;
	private String patente;
	private EstadoDeMovimiento estado;
	private ModoDeApp modo;
	private boolean seInicioEstacionamiento;
	private SEM sem = SEM.getInstance();
	
	

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
	
	public void inicarEstacionamiento() {
		this.setSeInicioEstacionamiento(true);
		;
		
	}
	
	public void finalizarEstacionamiento() {
		this.setSeInicioEstacionamiento(false);
		
	}
	
	public double saldoDeUsuario() {
		return sem.getRegistroSaldo().get(numero);
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

	public void setSeInicioEstacionamiento(boolean seInicioEstacionamiento) {
		this.seInicioEstacionamiento = seInicioEstacionamiento;
	}

	public void setEstado(EstadoDeMovimiento estado) {
		this.estado = estado;
	}

	public void setModo(ModoDeApp modo) {
		this.modo = modo;
	}

	
}
