public class Administrador extends Thread {
    private int nc;
    private Buzon buzonAlertas;
    private Buzon buzonClasificacion;

    public Administrador(int nc, Buzon buzonAlertas, Buzon buzonClasificacion) {
        this.nc = nc;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Evento e = buzonAlertas.retirar();

                if (e.esFin()) {
                    System.out.println("[ADMIN] Recibió FIN del broker. Enviando " + nc + " FINes a clasificadores");
                    for (int i = 0; i < nc; i++) {
                        buzonClasificacion.depositar(Evento.crearFin());
                    }
                    System.out.println("[ADMIN] Terminó");
                    break;
                }

                int random = (int)(Math.random() * 21); 
                if (random % 4 == 0) {
                    buzonClasificacion.depositar(e);
                    System.out.println("[ADMIN] Inofensivo → clasificación: " + e);
                } else {
                    System.out.println("[ADMIN] Malicioso descartado: " + e);
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}