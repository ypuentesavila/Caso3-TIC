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
                Evento e = buzonClasificacion.retirar();

                if (e.esFin()) {
                    System.out.println("[CLASIFICADOR-" + id + "] Recibió FIN");

                    // El ultimo clasificador en terminar manda FIN a cada servidor
                    boolean esUltimo = contador.decrementar();
                    if (esUltimo) {
                        System.out.println("[CLASIFICADOR-" + id + "] Es el último → enviando FIN a " 
                                           + buzonesConsolidacion.length + " servidores");
                        for (Buzon b : buzonesConsolidacion) {
                            b.depositar(Evento.crearFin());
                        }
                    }
                    break; // termina sin procesar nada más
                }

                // valida que el índice del servidor sea valid
                int indexServidor = e.getTipo() - 1; // getTipo() entre 1 y ns
                if (indexServidor < 0 || indexServidor >= buzonesConsolidacion.length) {
                    System.out.println("[CLASIFICADOR-" + id + "] Índice inválido para: " + e);
                    continue;
                }

                buzonesConsolidacion[indexServidor].depositar(e);
                System.out.println("[CLASIFICADOR-" + id + "] → Servidor " + e.getTipo() + ": " + e);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}