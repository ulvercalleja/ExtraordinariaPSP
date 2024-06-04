/* Desarrolla un programa en C que cree varios procesos hijo para ejecutar tareas de determinación de primalidad, usando el estado de finalización para controlar el éxito o fallo de cada proceso hijo basado en si el número generado es primo o no.

Especificaciones:

El programa debe aceptar un número N como parámetro de línea de comandos, que especificará el número de procesos hijo a crear.
Cada proceso hijo generará un número aleatorio entre 1 y 100 y determinará si este número es primo.
Un proceso hijo que determine que su número es primo terminará con exit(1) para indicar fallo, y si no es primo, con exit(0) para éxito.
Detalles adicionales:

El proceso padre debe esperar a que todos los hijos finalicen y luego debe revisar los códigos de estado de cada uno para determinar cuántos procesos determinaron un número primo (fallidos) y cuántos no (exitosos).
El padre debe imprimir cuántos procesos resultaron en números primos y cuántos en números no primos.
Uso del estado de finalización:

El padre analizará el valor devuelto por cada waitpid usando macros como WIFEXITED y WEXITSTATUS para determinar si el proceso hijo terminó normalmente y cuál fue su código de salida.
Estructura del programa:

Creación de Procesos: Crea N procesos hijo que cada uno realizará la tarea de determinar la primalidad de un número generado aleatoriamente.
Determinación de Primalidad en los Hijos: Cada hijo genera un número aleatorio, comprueba si es primo, y termina con un código de estado adecuado.
Recopilación de Resultados en el Padre: El padre recoge y analiza los estados de finalización para contar cuántos números eran primos y cuántos no.
Reporte de Resultados: El padre imprime los resultados de cuántos números fueron determinados como primos y cuántos como no primos. */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/wait.h>

#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1
#define EXITO 0
#define FALLO 1
#define RANGO_MAXIMO 100
#define RANGO_MINIMO 1

// Función para verificar si un número es primo
int esPrimo(int num) {
    if (num <= 1)
        return 0;
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0)
            return 0;
    }
    return 1;
}

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Uso: %s <num> \n", argv[0]);
        return 1;
    }
    pid_t hijo;

    int numProcesos = atoi(argv[1]);

    int cantNumerosPrimos = 0;
    
    for (int i = 0; i < numProcesos; i++) {
        hijo = fork();

        if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
        }

        else if (hijo == NUM_HIJO) { // Proceso hijo
            srand(time(NULL) ^ (getpid() << 16));

            int numeroRandom = (rand() % RANGO_MAXIMO) + 1;

            printf("Hijo (%d) ha generado %d\n", getpid(), numeroRandom);
            if (esPrimo(numeroRandom)) {
                exit(FALLO);
            } else {
                exit(EXITO);
            }
            
        }
    }

    // Este es el proceso padre
    int status;

    // Esperar a que todos los hijos terminen
    for (int i = 0; i < numProcesos; i++) {
        wait(&status);

        if (WIFEXITED(status)) {
            int exit_status = WEXITSTATUS(status);
            if (exit_status == FALLO) {
                cantNumerosPrimos++;
            }
            printf("El proceso hijo terminó con el código de salida %d\n", exit_status);
        } 
        
    }

    printf("%d procesos son numeros primos \n", cantNumerosPrimos);
    printf("%d procesos NO son numeros primos", numProcesos-cantNumerosPrimos);

    return 0;
}