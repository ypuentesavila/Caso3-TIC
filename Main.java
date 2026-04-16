public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java Main <archivo_config>");
            return;
        }

        Configuracion config = new Configuracion(args[0]);

        int totalEventos = config.base * config.ni * (config.ni + 1) / 2;
        System.out.println("=== Sistema IoT ===");
        System.out.println("Sensores: " + config.ni + " | Base: " + config.base);
        System.out.println("Clasificadores: " + config.nc + " | Servidores: " + config.ns);
        System.out.println("Total eventos esperados: " + totalEventos);
        System.out.println("==================");

        Buzon buzonEntrada       = new Buzon(-1);
        Buzon buzonAlertas       = new Buzon(-1);
        Buzon buzonClasificacion = new Buzon(config.tam1);
        Buzon[] buzonesConsolidacion = new Buzon[config.ns];
        for (int i = 0; i < config.ns; i++) {
            buzonesConsolidacion[i] = new Buzon(config.tam2);
        }

        ContadorClasificadores contador = new ContadorClasificadores(config.nc);

        Servidor[] servidores = new Servidor[config.ns];
        for (int i = 0; i < config.ns; i++) {
            servidores[i] = new Servidor(i + 1, buzonesConsolidacion[i]);
            servidores[i].start();
        }

        Clasificador[] clasificadores = new Clasificador[config.nc];
        for (int i = 0; i < config.nc; i++) {
            clasificadores[i] = new Clasificador(i + 1, buzonClasificacion,
                                                 buzonesConsolidacion, contador);
            clasificadores[i].start();
        }

        Administrador admin = new Administrador(config.nc, buzonAlertas, buzonClasificacion);
        admin.start();

        Broker broker = new Broker(totalEventos, buzonEntrada, buzonAlertas, buzonClasificacion);
        broker.start();

        Sensor[] sensores = new Sensor[config.ni];
        for (int i = 0; i < config.ni; i++) {
            sensores[i] = new Sensor(i + 1, config.base, config.ns, buzonEntrada);
            sensores[i].start();
        }

        // espera que terminen  todos los hilos
        for (Sensor s : sensores) s.join();
        broker.join();
        admin.join();
        for (Clasificador c : clasificadores) c.join();
        for (Servidor s : servidores) s.join();

        // todos los buzones deben quedar en 0
        System.out.println("\n=== Verificación Final ===");
        System.out.println("Buzón entrada:       " + buzonEntrada.size());
        System.out.println("Buzón alertas:       " + buzonAlertas.size());
        System.out.println("Buzón clasificación: " + buzonClasificacion.size());
        for (int i = 0; i < config.ns; i++) {
            System.out.println("Buzón consolidación " + (i+1) + ": " + buzonesConsolidacion[i].size());
        }
        System.out.println("✓ Sistema terminado correctamente");
    }
}