// Módulo 3 - punto 1: Thread consumidor/productor intermedio
// Consume del buzón de entrada, produce en alertas o clasificación
public class Broker extends Thread {
    private int totalEventos;
    private Buzon buzonEntrada;
    private Buzon buzonAlertas;
    private Buzon buzonClasificacion;

    public Broker(int totalEventos, Buzon buzonEntrada,
                  Buzon buzonAlertas, Buzon buzonClasificacion) {
        this.totalEventos = totalEventos;
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalEventos; i++) {
                // retirar usa espera pasiva (wait)
                Evento e = buzonEntrada.retirar();

                // Simula detección de anomalías
                int random = (int)(Math.random() * 201); // 0 a 200
                if (random % 8 == 0) {
                    buzonAlertas.depositar(e);
                    System.out.println("[BROKER] ALERTA → " + e);
                } else {
                    buzonClasificacion.depositar(e);
                    System.out.println("[BROKER] Normal → " + e);
                }
            }
            // evento de fin para el administrador
            buzonAlertas.depositar(new Evento());
            System.out.println("[BROKER] Envió FIN al administrador. Terminó");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}