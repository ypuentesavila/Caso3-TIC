public class Servidor extends Thread {
    private int id;
    private Buzon buzonConsolidacion;

    public Servidor(int id, Buzon buzonConsolidacion) {
        this.id = id;
        this.buzonConsolidacion = buzonConsolidacion;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Evento e = buzonConsolidacion.retirar();

                if (e.esFin()) {
                    System.out.println("[SERVIDOR-" + id + "] Recibió FIN. Terminó");
                    break;
                }

                int tiempo = 100 + (int)(Math.random() * 901);
                Thread.sleep(tiempo);
                System.out.println("[SERVIDOR-" + id + "] Procesó " + e + " en " + tiempo + "ms");
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}