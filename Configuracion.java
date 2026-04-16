import java.io.*;
import java.util.Scanner;

public class Configuracion {
    public int ni;
    public int base;
    public int nc;
    public int ns;
    public int tam1;
    public int tam2;

    public Configuracion(String archivo) throws IOException {
        try (Scanner sc = new Scanner(new File(archivo))) {

            if (!sc.hasNextInt()) throw new IOException("Faltan valores en config");
            ni   = sc.nextInt();
            base = sc.nextInt();
            nc   = sc.nextInt();
            ns   = sc.nextInt();
            tam1 = sc.nextInt();
            tam2 = sc.nextInt();

            if (ni <= 0 || base <= 0 || nc <= 0 || ns <= 0 || tam1 <= 0 || tam2 <= 0)
                throw new IllegalArgumentException("Todos los valores deben ser positivos");
        }
    }
}