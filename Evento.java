// representa un evento del sistema IoT
public class Evento {
    private String id;
    private int tipo;
    private boolean esFin;

    public Evento(String id, int tipo) {
        this.id = id;
        this.tipo = tipo;
        this.esFin = false;
    }

    public Evento() {
        this.esFin = true;
        this.id = "FIN";
        this.tipo = -1;
    }

    public String getId() { return id; }
    public int getTipo() { return tipo; }
    public boolean esFin() { return esFin; }

    @Override
    public String toString() {
        return esFin ? "Evento[FIN]" : "Evento[" + id + ", tipo=" + tipo + "]";
    }
}