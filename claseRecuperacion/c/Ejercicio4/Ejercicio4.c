/* Escribe un programa en C que implemente un sistema simple de notificaciones usando señales entre procesos. 
El programa tendrá un proceso padre que actúa como un monitor de eventos y varios procesos hijo que simulan eventos aleatorios.

Especificaciones:

Al iniciar, el programa debe aceptar un número M como parámetro de línea de comandos, indicando la cantidad de procesos
 hijo a crear.
Cada proceso hijo simulará la ocurrencia de un evento aleatorio (por ejemplo: temperatura alta, presión baja, etc. Son 
ejemplos, no hay que programar el tipo de señal. ) después de un tiempo aleatorio entre 1 y 5 segundos.
Cuando un hijo detecta un evento, debe enviar una señal (puedes usar SIGUSR1) al proceso padre.
El proceso padre, al recibir la señal de cualquier hijo, debe imprimir un mensaje que incluya el PID del hijo y un mensaje
 descriptivo del tipo de evento (el mensaje puede ser genérico, como "Evento detectado por [PID]").
Detalles adicionales:

Los hijos deben dormir (sleep) un tiempo aleatorio antes de enviar la señal para simular el retraso en la detección de eventos.
El proceso padre debe manejar las señales para interceptar SIGUSR1 y responder adecuadamente.
Una vez que un proceso hijo envía su señal, debe terminar su ejecución.
El padre espera que se ejecuten todos los hijos. */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>

#define MIN_HIJOS 0
#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1


// Manejador de señales para SIGUSR1
void signal_handler(int signum) {
    printf(" Padre: evento detectado por proceso hijo con PID: %d\n", getpid());
}

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Uso: %s <num> \n", argv[0]);
        return 1;
    }

    int numProcesos = atoi(argv[1]);

    for (int i = 0; i < numProcesos; i++) {
        pid_t hijo = fork();

        if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
        }

        else if (hijo == NUM_HIJO) { // Proceso hijo
            // Código del hijo
            srand(time(NULL) ^ (getpid() << 16)); //Importante para que cada hijo duerma y muestre diferentes resultados en sus nums aleatorios
            int sleep_time = (rand() % 5) + 1;
            sleep(sleep_time);

            int n_señal = rand() % 3 + 1;
            if (n_señal == 1) {

                printf("Hijo (%d) temperatura perfecta \n", getpid());
                kill(getpid(), SIGUSR1);
                exit(0);

            } else if (n_señal == 2){
                
                printf("Hijo (%d) tiene frio \n", getpid());
                kill(getpid(), SIGUSR1);
                exit(0);
            } else {
                
                printf("Hijo (%d) tiene calor \n", getpid());
                kill(getpid(), SIGUSR1);
                exit(0);
            }
            
        }
    }

    // Esperar a que todos los hijos terminen
    for (int i = 0; i < numProcesos; i++) {
        wait(NULL);
    }

    return 0;
}
