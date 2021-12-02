package main.java.SEM.Aplication;

import main.java.Controllers.*;
import main.java.EstadosModos.*;
import main.java.SEM.SEM;
import main.java.SEM.Estacionamiento.*;

import java.util.Date;

public class App implements MovementSensor {

	private Integer numero;
	private String patente;
	private EstadoDeMovimiento estado; /*State*/
	private ModoDeApp modo; /* Strategy */
	private boolean seInicioEstacionamiento;
	private Ubicacion ubicacionGPS;
	private SEM sem = SEM.getInstance();
	private Date horaDeinicioDeEstacionamiento ;
	private IPantalla pantalla;
	
	

	public App(Integer numero, String patente, IPantalla pantalla) {
		super();
		this.numero = numero;
		this.seInicioEstacionamiento = false;
		this.modo = new Manual();
		this.estado = new EstadoCaminando();
		this.patente = patente;
		this.pantalla = pantalla;
		this.ubicacionGPS = new NullUbicacion();
		/* Se asume que el estado del conductor al iniciar la app es camninado 
		   y que la app se inicia en modo manual*/
	}

	public IPantalla getPantalla() {
		return pantalla;
	}

	@Override
	public void driving() {
		this.getEstadoDeMovimiento().alertaDriving(this);
		
	}
	
	@Override
	public void walking() {
		this.getEstadoDeMovimiento().alertaWalking(this);
		
	}
	
	public void cambiarModoAutomatico() {
		this.setModo(new Automatico());
	}
	
	public void cambiarModoManual() {
		this.setModo(new Manual());
	}
	
	
	public void mostrarAlertaDeIncioDeEstacionamiento() {
		this.getPantalla().mostrar("No se inicio un estacionamiento");
	}
	
	public void mostrarAlertaDeFinalizacionDeEstacionamiento() {
		this.getPantalla().mostrar("No se finalizo el estacionamiento");
	}
	
	public double saldoDeUsuario() {
		return sem.saldoDeUsuario(this.getNumero());
	}
	
	
	public void inicarEstacionamiento() {
		if(this.seInicioEstacionamiento) {
			// no puedo Iniciar Dos Estacionamientos
		}
		else if(sem.tieneSaldoSuficiente(numero) && this.seEncuentraEnLaZonaEstacionamiento()) {
			this.setSeInicioEstacionamiento(true);
			this.registrarHora();
			sem.iniciarEstacionamientoVirtual(this.getPatente(), this.getUbicacionGPS().getZona(), this.getNumero());
			this.getPantalla().mostrar("Hora de inicializacion: " + this.getHoraInicioDeEstacionamiento().getHours() + ", Hora de finalizacion: " + this.horaMaximaDeEstacionamiento().getHours() );
		}
		else if(!sem.tieneSaldoSuficiente(numero)) {
			this.getPantalla().mostrar("saldo insuficiente. SEM.Estacionamiento no permitido");
		}
	  }

	public boolean seEncuentraEnLaZonaEstacionamiento() {
		//return this.getUbicacionGPS().seEncuentraEnZonaDeEstacionamiento();
		return this.getUbicacionGPS().seEncuentraEnZonaDeEstacionamiento();
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
			this.getPantalla().mostrar("Hora de iniciacion: " + this.getHoraInicioDeEstacionamiento().getHours() + ", Hora de finalizacion: " + this.horaActual().getHours() + 
					", Horas totales de estacionamiento: " +  (this.horaActual().getHours() - this.getHoraInicioDeEstacionamiento().getHours() ) +
					", Costo de estacionamiento: " + this.costeTotalDeEstacionamiento());
			/*System.out.println(this.getHoraInicioDeEstacionamiento());
			System.out.println(this.horaActual());
			System.out.println(this.horaActual().getHours() - this.getHoraInicioDeEstacionamiento().getHours());
			System.out.println(this.costeTotalDeEstacionamiento());
			*/
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

	private Ubicacion getUbicacionGPS() {
		return ubicacionGPS;
	}
	
	/* Este metodo es solo para testear en realidad la aplicacion le pedira a su gps la ubicacion*/
	public void entroAZonaDeEstacionamiento(ZonaDeEstacionamiento zona) {
		this.ubicacionGPS = new UbicacionDentroDeZona(zona);
	}
	
	public void salioDeZonaDeEstacionamiento() {
		this.ubicacionGPS = new NullUbicacion();
	}
	
	private Date getHoraInicioDeEstacionamiento() {
		return horaDeinicioDeEstacionamiento;
	}
	
	private void setHoraDeInicioDeEstacionamiento(Date date) {
		this.horaDeinicioDeEstacionamiento = date;
	}

	public EstadoDeMovimiento getEstadoDeMovimiento() {
		// TODO Auto-generated method stub
		return this.estado;
	}

	public void cambioDeConducirACaminar() {
		this.setEstado(new EstadoCaminando());
		this.getModo().conductorCambioDeConducirACaminar(this);
	}

	public void cambioDeCaminarAConducir() {
		this.setEstado(new EstadoConduciendo());
		this.getModo().conductorCambioDeCaminarAConducir(this);
	}

	public void posibleAlertaDeFinalizacionDeEstacionamiento() {
		if(this.isSeInicioEstacionamiento()) {
			this.mostrarAlertaDeFinalizacionDeEstacionamiento();
		}
	}

	public void posibleAlertaDeInicioDeEstacionamiento() {
		if(!this.isSeInicioEstacionamiento() && this.seEncuentraEnLaZonaEstacionamiento() ) {
			this.mostrarAlertaDeIncioDeEstacionamiento();
		}
		
	}
	
	

	
}
