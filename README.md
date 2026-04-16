# Caso 3 - Sistema IoT Concurrente

## Descripción
Este programa simula un sistema IoT donde varios sensores generan eventos que pasan por diferentes etapas hasta ser procesados por servidores. El flujo del sistema es el siguiente:

**Sensores → Broker → Administrador → Clasificadores → Servidores**

Cada componente se implementa como un hilo independiente y la comunicación se realiza mediante buzones compartidos.

## Concurrencia
La gestión de hilos y sincronización se basa en los siguientes principios:
* **Threads:** Implementación de hilos para cada componente.
* **Sincronización:** Uso de `synchronized` para proteger los buzones compartidos.
* **Coordinación:** Uso de `wait()` y `notifyAll()` para coordinar productores y consumidores.
* **Terminación:** Manejo de cierre del sistema mediante eventos de tipo `FIN`.

## Configuración del archivo config.txt
El archivo de configuración debe contener una sola línea con 6 valores enteros:
`ni base nc ns tam1 tam2`

* **ni**: Número de sensores.
* **base**: Eventos por sensor.
* **nc**: Número de clasificadores.
* **ns**: Número de servidores.
* **tam1**: Capacidad del buzón del Broker.
* **tam2**: Capacidad de los buzones de los Clasificadores.

## Compilación y Ejecución
Para poner en marcha el sistema, ejecute la siguiente secuencia de comandos en la terminal:

```bash
# 1. Compilar el proyecto
javac *.java

# 2. Crear archivo de configuración (Ejemplo: 2 sensores, 2 eventos, 2 clasif, 2 serv, tam 3, tam 3)
echo "2 2 2 2 3 3" > config.txt

# 3. Ejecutar el programa
java Main config.txt
