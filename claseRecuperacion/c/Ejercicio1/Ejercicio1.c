/* Crea un programa que acepte un número como parámetro de línea de comandos. 
El número recibido indicará la cantidad de números aleatorios generados.

El programa creará otro proceso con la llamada al sistema fork. El proceso 
padre generará n números aleatorios y se los enviará al proceso hijo a través 
de un pipe. El padre esperará a que el hijo termine (padre usa wait).

El hijo recibirá los números por el pipe e irá procesando el menor y el mayor. 
Cuando termine de recibir números el hijo escribirá por pantalla el número menor y 
el número mayor.*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1
#define READ 0
#define WRITE 1

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Uso: %s <num> \n", argv[0]);
        return 1;
    }

    int num = atoi(argv[1]);

    int pipe1[2];
    if (pipe(pipe1) == -1) {
        perror("Error al crear el pipe");
        exit(EXIT_FAILURE);
    }

    pid_t hijo = fork();

    if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
        perror("Error al crear el hijo");
        exit(EXIT_FAILURE);
    } else if (hijo == NUM_HIJO) { // Proceso hijo
        close(pipe1[WRITE]);
        int numMayor = 0;
        int numMenor = 100;
        int num_generado;
        while (read(pipe1[READ], &num_generado, sizeof(int))) {
            if (num_generado > numMayor){
                numMayor = num_generado;
            }

            if (num_generado < numMenor){
                numMenor = num_generado;
            }
        }
        printf("Soy el hijo, el numero menor es: %d y el mayor es: %d\n", numMenor, numMayor);
        close(pipe1[READ]);
        exit(0);
    } else {
        close(pipe1[READ]);
        srand((unsigned int) getpid());
        for (int i = 0; i < num; i++) {
            int num_generado = rand() % 100;
            printf("Soy el padre y he generado %d \n", num_generado);
            write(pipe1[WRITE], &num_generado, sizeof(int));
        }
        close(pipe1[WRITE]);
        wait(NULL);
    }
    
    return 0;

}