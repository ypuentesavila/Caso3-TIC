Caso 3 - Sistema IoT Concurrente
Descripción
Este programa simula un sistema IoT donde varios sensores generan eventos que pasan por diferentes etapas hasta ser procesados por servidores.
Flujo del sistema:
Sensores → Broker → Administrador → Clasificadores → Servidores
Cada componente se implementa como un hilo y se comunican usando buzones compartidos.
Concurrencia
Se usaron:
Threads para cada componente
synchronized para proteger los buzones
wait y notifyAll para coordinar productores y consumidores
La terminación se maneja con eventos de tipo FIN.
Cómo ejecutar
Compilar:
javac *.java
Crear archivo de configuración:
echo "2 2 2 2 3 3" > config.txt
Ejecutar:
java Main config.txt
Formato del archivo:
ni base nc ns tam1 tam2
