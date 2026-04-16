public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java Main <archivo_config>");
            return;
        }

        Configuracion config = new Configuracion(args[0]);

        // Total de eventos: suma de base*1 + base*2 + ... + base*ni
        int totalEventos = config.base * config.ni * (config.ni + 1) / 2;
        System.out.println("Total eventos esperados: " + totalEventos);

        Buzon buzonEntrada       = new Buzon(-1);          // ilimitado
        Buzon buzonAlertas       = new Buzon(-1);          // ilimitado
        Buzon buzonClasificacion = new Buzon(config.tam1); // limitado
        Buzon[] buzonesConsolidacion = new Buzon[config.ns];
        for (int i = 0; i < config.ns; i++) {
            buzonesConsolidacion[i] = new Buzon(config.tam2); // limitado
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

        // join() espera a que todos los hilos terminen
        for (Sensor s : sensores) s.join();
        broker.join();
        admin.join();
        for (Clasificador c : clasificadores) c.join();
        for (Servidor s : servidores) s.join();

        // todos los buzones deben quedar vacíos
        System.out.println("\n✓ Sistema terminado correctamente");
        System.out.println("Buzón entrada:       " + buzonEntrada.size()       + " eventos");
        System.out.println("Buzón alertas:       " + buzonAlertas.size()       + " eventos");
        System.out.println("Buzón clasificación: " + buzonClasificacion.size() + " eventos");
        for (int i = 0; i < config.ns; i++) {
            System.out.println("Buzón consolidación " + (i + 1) + ": "
                               + buzonesConsolidacion[i].size() + " eventos");
        }
    }
}