// Thread productor
// Genera eventos y los deposita en el buzón de entrada
public class Sensor extends Thread {
    private int id;
    private int numEventos;
    private int ns;
    private Buzon buzonEntrada;

    public Sensor(int id, int base, int ns, Buzon buzonEntrada) {
        this.id = id;
        this.numEventos = base * id; // sensor i genera base*i eventos
        this.ns = ns;
        this.buzonEntrada = buzonEntrada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= numEventos; i++) {
                // tipo indica el servidor destino (1 hasta ns)
                int tipo = (int)(Math.random() * ns) + 1;
                String eventoId = "S" + id + "-E" + i;
                Evento e = new Evento(eventoId, tipo);
                // depositar puede bloquear (espera pasiva)
                buzonEntrada.depositar(e);
                System.out.println("[SENSOR-" + id + "] Depositó " + e);
            }
            System.out.println("[SENSOR-" + id + "] Terminó. Generó " + numEventos + " eventos");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}