// Módulo 3 - punto 1: Thread consumidor/productor intermedio
// Filtra alertas y controla terminación de clasificadores
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
                //spera pasiva hasta recibir evento
                Evento e = buzonAlertas.retirar();

                // detecta evento de fin
                if (e.esFin()) {
                    // enviar nc eventos de fin a clasificadores
                    for (int i = 0; i < nc; i++) {
                        buzonClasificacion.depositar(new Evento());
                    }
                    System.out.println("[ADMIN] Envió " + nc + " FINes a clasificadores. Terminó");
                    break;
                }

                // Simular inspección profunda
                int random = (int)(Math.random() * 21); 
                if (random % 4 == 0) {
                    buzonClasificacion.depositar(e);
                    System.out.println("[ADMIN] Evento inofensivo → clasificación: " + e);
                } else {
                    System.out.println("[ADMIN] Evento malicioso descartado: " + e);
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}