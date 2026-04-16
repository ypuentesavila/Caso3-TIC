public class ContadorClasificadores {
    private int count;

    public ContadorClasificadores(int nc) {
        this.count = nc;
    }

    public synchronized boolean decrementar() {
        count--;
        return count == 0;
    }
}