import java.util.HashMap;
import java.util.List;

public class SEM {
    private static SEM instancia;
    private List<Infraccion> infracciones;
    /**
      El string(patente) es la forma univoca de identificar un vehiculo estacionado en esa zona.
      Y el mismo vehiculo no se podra visualizar en dos zonas de estacionamiento simultaneamente.
     */
    private HashMap<String,ZonaDeEstacionamiento> estacionamientos = new HashMap<String,ZonaDeEstacionamiento>();
    /**
     * El key int es la relacion de un numero telefonico y el value es el saldo que corresponde al
     * numero telefonico.
     * */
    private HashMap<Integer, Double> registroSaldo = new HashMap<Integer, Double>();

    private SEM(){}

    public static SEM getInstance(){
        if(instancia == null){
            instancia = new SEM();
        }
        return instancia;
    }

    public HashMap<Integer, Double> getRegistroSaldo() {
        return registroSaldo;
    }

    public boolean esValidoElEstacionamiento(String patente){
        return true;
    }

    public void iniciarEstacionamientoPorCompra(String patente, ZonaDeEstacionamiento zona,int horas){this.estacionamientos.put(patente, zona);}

    public void iniciarEstacionamientoVirtual(String patente, ZonaDeEstacionamiento zona){
        this.estacionamientos.put(patente, zona);
    }

    public void generarPagoVirtual(int number, double monto) {
        /**
          El usuario debe estar registrado con un mondo.
         */
        this.registroSaldo.put(number, registroSaldo.get(number) - monto);
    }

    public void cargarSaldo(int numero, double saldo){
        if(this.registroSaldo.containsKey(numero)){
            this.registroSaldo.put(numero, registroSaldo.get(numero) + saldo);
        }else{
            this.registroSaldo.put(numero, saldo);
        }
    }

    public void finalizarEstacionamientoVirtual(String patente){
        this.estacionamientos.remove(patente);
    }

    public EstacionamientoVirtual comprobanteDeEstacionamiento(int numero){return null;}
    public void finalizarEstacionamientos(){}

    public void generarInfraccion(String patente){
        this.infracciones.add(new Infraccion(patente, 0));
    }
}
