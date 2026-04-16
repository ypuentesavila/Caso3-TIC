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
                Evento e = buzonEntrada.retirar();

                int random = (int)(Math.random() * 201); // 0 a 200
                if (random % 8 == 0) {
                    buzonAlertas.depositar(e);
                    System.out.println("[BROKER] Anómalo → alertas: " + e);
                } else {
                    buzonClasificacion.depositar(e);
                    System.out.println("[BROKER] Normal → clasificación: " + e);
                }
            }

            buzonAlertas.depositar(Evento.crearFin());
            System.out.println("[BROKER] Todos los eventos procesados. FIN enviado al administrador");

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}