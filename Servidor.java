// Módulo 3 - punto 1: Thread consumidor final
// Consolida y despliega eventos de su buzón de consolidación
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
                // espera pasiva hasta recibir evento
                Evento e = buzonConsolidacion.retirar();

                // detecta evento de fin
                if (e.esFin()) {
                    System.out.println("[SERVIDOR-" + id + "] Recibió FIN. Terminó");
                    break;
                }

                // simula procesamiento entre 100ms y 1000ms
                int tiempo = 100 + (int)(Math.random() * 901);
                Thread.sleep(tiempo);
                System.out.println("[SERVIDOR-" + id + "] Procesó " + e + " en " + tiempo + "ms");
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}