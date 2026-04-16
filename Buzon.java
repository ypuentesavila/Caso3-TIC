import java.util.LinkedList;
import java.util.Queue;

// Módulo 3 - punto 2 y 3: Exclusión mutua + Señalamiento
// Implementa el patrón productor-consumidor
public class Buzon {
    private Queue<Evento> cola = new LinkedList<>();
    private int capacidad; // -1 = ilimitado

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
    }

    //  synchronized garantiza exclusión mutua
    // wait() espera pasiva si está lleno
    public synchronized void depositar(Evento e) throws InterruptedException {
        while (capacidad != -1 && cola.size() >= capacidad) {
            wait(); // espera pasiva hasta que haya espacio
        }
        cola.add(e);
        notifyAll(); // despierta a hilos esperando para retirar
    }

    // wait() espera pasiva si está vacío
    public synchronized Evento retirar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); // espera pasiva hasta que haya eventos
        }
        Evento e = cola.poll();
        notifyAll(); // despierta a hilos esperando para depositar
        return e;
    }

    public synchronized int size() {
        return cola.size();
    }
}