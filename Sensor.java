public class Sensor extends Thread {
    private int id;
    private int numEventos; // exactamente base * id
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
                int tipo = (int)(Math.random() * ns) + 1;
                Evento e = new Evento("S" + id + "-E" + i, tipo);
                buzonEntrada.depositar(e);
                System.out.println("[SENSOR-" + id + "] Depositó " + e);
            }
            System.out.println("[SENSOR-" + id + "] Terminó. Total: " + numEventos + " eventos");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}