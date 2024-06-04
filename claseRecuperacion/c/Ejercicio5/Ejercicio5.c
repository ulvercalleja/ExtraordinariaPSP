/* Crea un programa en C que simule un simple sistema de cálculo con un proceso padre que interactúa 
con el usuario y un proceso hijo que realiza los cálculos. El padre mostrará un menú para que el usuario
 seleccione entre sumar, restar o salir, y enviará los detalles de la operación al hijo a través de una tubería. 
 El hijo procesará estos datos y enviará el resultado de vuelta al padre a través de otra tubería.

Especificaciones:

El programa debe mostrar un menú continuamente hasta que el usuario seleccione la opción de "salir".
El menú ofrecerá las opciones:
Sumar dos números.
Restar dos números.
Salir.
El proceso padre recoge la elección del usuario. Si es una operación, también solicita dos números y los envía, 
junto con la operación, al hijo a través de una tubería.
El proceso hijo recibe los datos, realiza el cálculo y envía el resultado de vuelta al padre a través de otra tubería.
Si el usuario selecciona "salir", el padre enviará una señal de terminación (SIGKILL, es decir, señal -9) al hijo para 
terminar su ejecución de manera inmediata y luego el padre terminará su propia ejecución.
Detalles adicionales:

Se deben usar dos tuberías: una para enviar datos del padre al hijo y otra para enviar el resultado de vuelta al padre.
El padre debe manejar adecuadamente la apertura y cierre de los extremos de las tuberías para evitar bloqueos y fugas de recursos.
Implementa una gestión adecuada de errores para la creación de procesos y tuberías.
Asegúrate de que el padre envía la señal SIGKILL al hijo solo cuando el usuario elija salir, lo que garantiza una terminación limpia 
del hijo antes de que el padre también termine.
Estructura del programa:

Menú interactivo: El padre muestra el menú y recoge la elección del usuario.
Comunicación padre-hijo: El padre envía la operación y los números al hijo a través de una tubería, y recibe los resultados del cálculo del hijo a través de otra tubería.
Terminación del hijo: Cuando el usuario elige salir, el padre envía una señal SIGKILL al hijo para terminar su proceso de manera inmediata. */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>

#define MIN_HIJOS 0
#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1
#define READ 0
#define WRITE 1

void mostrarMenu() {
    printf("\n");
    printf("1. Sumar\n");
    printf("2. Restar\n");
    printf("3. Salir\n");
    printf("\n");
}

int main() {

    int num1, num2, opcion, resultado;

    int pipe1[2];
    if (pipe(pipe1) == -1) {
        perror("Error al crear el pipe");
        exit(EXIT_FAILURE);
    }

    int pipe2[2];
    if (pipe(pipe2) == -1) {
        perror("Error al crear el pipe");
        exit(EXIT_FAILURE);
    }

    pid_t hijo = fork();

    if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
        perror("Error al crear el hijo");
        exit(EXIT_FAILURE);
    }

    else if (hijo == NUM_HIJO) { // Proceso hijo
        close(pipe1[WRITE]); // Cerrar el extremo de lectura del pipe1

        while (read(pipe1[READ], &num1, sizeof(int)) > 0 && read(pipe1[READ], &num2, sizeof(int)) > 0 && read(pipe1[READ], &opcion, sizeof(int)) > 0) {
           if (opcion == 1) {
                printf("Hijo (%d) calculando suma...\n", getpid());
                resultado = num1 + num2;
            } else if (opcion == 2) {
                printf("Hijo (%d) calculando resta...\n", getpid());
                resultado = num1 - num2;
            }
        }
        
        close(pipe1[READ]);
        write(pipe2[WRITE], &resultado, sizeof(int));
        close(pipe2[WRITE]);
    }  
    
    close(pipe1[READ]); // Cerrar el extremo de lectura del pipe1

    do {
        
        mostrarMenu();
        printf("Seleccione una opción: ");
        scanf("%d", &opcion);

        if (opcion == 1 || opcion == 2) {
                printf("Ingrese el primer número: ");
                    scanf("%d", &num1);
                printf("Ingrese el segundo número: ");
                    scanf("%d", &num2);

                write(pipe1[WRITE], &num1, sizeof(int));
                write(pipe1[WRITE], &num2, sizeof(int));
                write(pipe1[WRITE], &opcion, sizeof(int));

                close(pipe1[WRITE]); // Cerrar el extremo de escritura del pipe
                close(pipe2[WRITE]);

                while (read(pipe2[READ], &resultado, sizeof(int)) > 0 ){
                    printf("El resultado es %d \n", resultado);
                }

                close(pipe1[READ]);
                close(pipe2[READ]);
        } else if (opcion == 3) {
                printf("Saliendo...\n");
                kill(hijo, SIGKILL);
        } else {
                printf("Opción no válida. Inténtelo de nuevo.\n");
        }

    } while(opcion != 3);
    
    wait(NULL);
    return 0;

}