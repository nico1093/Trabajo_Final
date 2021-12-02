package SEM.Aplication;

import Controllers.EstadoDeMovimiento;
import Controllers.IPantalla;
import Controllers.ModoDeApp;
import Controllers.MovementSensor;
import EstadosModos.Automatico;
import EstadosModos.EstadoCaminando;
import EstadosModos.EstadoConduciendo;
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
	private boolean seEncuentraEnZona;
	private ZonaDeEstacionamiento ubicacionGPS;
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
		this.seEncuentraEnZona = false;
		this.pantalla = pantalla;
		/* Se asume que el estado del conductor al iniciar la app es camninado 
		   y que la app se inicia en modo manual*/
	}

	public IPantalla getPantalla() {
		return pantalla;
	}

	@Override
	public void driving() {
		this.getEstadoDeMovimiento().driving(this);
		
	}
	
	@Override
	public void walking() {
		this.getEstadoDeMovimiento().walking(this);
		
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
			sem.iniciarEstacionamientoVirtual(this.getPatente(), this.getUbicacionGPS(), this.getNumero());
			this.getPantalla().mostrar("Hora de inicializacion: " + this.getHoraInicioDeEstacionamiento().getHours() + ", Hora de finalizacion: " + this.horaMaximaDeEstacionamiento().getHours() );
		}
		else if(!sem.tieneSaldoSuficiente(numero)) {
			this.getPantalla().mostrar("saldo insuficiente. SEM.Estacionamiento no permitido");
		}
	  }

	public boolean seEncuentraEnLaZonaEstacionamiento() {
		//return this.getUbicacionGPS().seEncuentraEnZonaDeEstacionamiento();
		return this.seEncuentraEnZona;
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

	private ZonaDeEstacionamiento getUbicacionGPS() {
		return ubicacionGPS;
	}
	
	/* Este metodo es solo para testear en realidad la aplicacion le pedira a su gps la ubicacion*/
	public void entroAZonaDeEstacionamiento(ZonaDeEstacionamiento ubicacionGPS) {
		this.seEncuentraEnZona = true;
		this.ubicacionGPS = ubicacionGPS;
	}
	
	public void salioDeZonaDeEstacionamiento() {
		this.seEncuentraEnZona = false;
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

	public void posibleInicioDeEstacionamiento() {
			this.inicarEstacionamiento();
		
	}

	public void posibleFinalizacionDeEstacionamiento() {
		if(this.isSeInicioEstacionamiento()) {
			this.finalizarEstacionamiento();
		}
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
