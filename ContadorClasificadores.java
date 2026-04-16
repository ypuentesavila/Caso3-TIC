// Exclusión mutua para variable compartida
// Permite saber cual es el último clasificador en terminar
public class ContadorClasificadores {
    private int count;

    public ContadorClasificadores(int nc) {
        this.count = nc;
    }

    // synchronized protege el contador de condiciones de carrera
    public synchronized boolean decrementar() {
        count--;
        return count == 0; // true si es el último
    }
}