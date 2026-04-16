// Módulo 3 - punto 1: Thread consumidor/productor
// Enruta eventos al servidor de consolidación correspondiente
public class Clasificador extends Thread {
    private int id;
    private Buzon buzonClasificacion;
    private Buzon[] buzonesConsolidacion;
    private ContadorClasificadores contador;

    public Clasificador(int id, Buzon buzonClasificacion,
                        Buzon[] buzonesConsolidacion,
                        ContadorClasificadores contador) {
        this.id = id;
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
        this.contador = contador;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // espera pasiva hasta recibir evento
                Evento e = buzonClasificacion.retirar();

                // detecta evento de fin
                if (e.esFin()) {
                    System.out.println("[CLASIFICADOR-" + id + "] Recibió FIN");
                    // decrementar es synchronized
                    boolean esUltimo = contador.decrementar();
                    if (esUltimo) {
                        // El último envía fin a todos los servidores
                        for (Buzon b : buzonesConsolidacion) {
                            b.depositar(new Evento());
                        }
                        System.out.println("[CLASIFICADOR-" + id + "] Último → envió FINes a servidores");
                    }
                    break;
                }

                // Enrutar al servidor según tipo de evento
                int indexServidor = e.getTipo() - 1;
                buzonesConsolidacion[indexServidor].depositar(e);
                System.out.println("[CLASIFICADOR-" + id + "] → Servidor " + e.getTipo() + ": " + e);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}