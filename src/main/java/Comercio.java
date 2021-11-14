public class Comercio {
    private String id;
    private final SEM sem = SEM.getInstance();
    private ZonaDeEstacionamiento zona;

    public Comercio(String id, ZonaDeEstacionamiento zona){
        this.id = id;
        this.zona = zona;
    }

    public void recargarAplicativo(int number, double monto){
        sem.cargarSaldo(number, monto);
    }

    public void generarEstacionamiento(String patente, int hours){
        sem.iniciarEstacionamientoPorCompra(patente, null, hours);
    }

    public static class estadoConduciendo implements EstadoDeMovimiento {

        @Override
        public void llegoMensajeDriving(App app) {
            // No hace nada

        }

        @Override
        public void llegoMensajeWalking(App app) {
            app.setEstado(new EstadoCaminando());
            app.getModo().conductorCambioDeConducirACaminar(app);
        }

    }
}
