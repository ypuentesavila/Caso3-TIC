import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Evento> cola = new LinkedList<>();
    private int capacidad; // -1 = ilimitado

    public Buzon(int capacidad) {
        if (capacidad == 0 || capacidad < -1)
            throw new IllegalArgumentException("Capacidad inválida: " + capacidad);
        this.capacidad = capacidad;
    }

    public synchronized void depositar(Evento e) throws InterruptedException {
        while (capacidad != -1 && cola.size() >= capacidad) {
            wait();
        }
        cola.add(e);
        notifyAll();
    }

    public synchronized Evento retirar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        Evento e = cola.poll();
        notifyAll();
        return e;
    }

    public synchronized int size() { return cola.size(); }
}