import java.io.*;
import java.util.Scanner;

public class Configuracion {
    public int ni;   // número de sensores
    public int base; // número base de eventos
    public int nc;   // número de clasificadores
    public int ns;   // número de servidores
    public int tam1; // capacidad buzón clasificación
    public int tam2; // capacidad buzón consolidación

    public Configuracion(String archivo) throws IOException {
        Scanner sc = new Scanner(new File(archivo));
        ni   = sc.nextInt();
        base = sc.nextInt();
        nc   = sc.nextInt();
        ns   = sc.nextInt();
        tam1 = sc.nextInt();
        tam2 = sc.nextInt();
        sc.close();
    }
}