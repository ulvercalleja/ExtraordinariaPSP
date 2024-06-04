/*Escribe un programa que reciba por parámetro un número, este número indicará el número de procesos a crear.

Los hijos buscarán entre los números del 10000 al 99999 los números capicúas. Cada hijo buscará más o menos un 
número parecido de números.

Cuando encuentre un número capicúa escribirá:
hijo <x>: <y> (Siendo x el número de hijo e y el número capicúa)

El padre espera a que los hijos finalicen. Codigo necesario: 
sprintf is returning the bytes and adds a null byte as well:
# include <stdio.h>
# include <string.h>
int main() {
    char buf[1024];
    int n = sprintf( buf, "%d", 2415);
    printf("%s %d\n", buf, n);
}
Output:
2415 4*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

#define MIN_HIJOS 0
#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1
#define UNO 1
#define RANGO_MAXIMO 99999
#define RANGO_MINIMO 10000

int esCapicua(int num) {
    char buf[8];
    int n = sprintf( buf, "%d", num);

    for (int i = 0; i < n/2; i++) {
        if (buf[i] != buf[n - 1 - i]) {
            return 0;
        }
            
    }
    return 1;
}

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Uso: %s <num> \n", argv[0]);
        return 1;
    }

    int numProcesos = atoi(argv[1]);

    int RANGO_TOTAL = RANGO_MAXIMO - RANGO_MINIMO + UNO;
    int RANGO_POR_HIJO = RANGO_TOTAL / numProcesos;
    int RESTANTE = RANGO_TOTAL % numProcesos;

    for (int i = 0; i < numProcesos; i++) {
        pid_t hijo = fork();

        if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
        }

        else if (hijo == NUM_HIJO) { // Proceso hijo

            int inicio = RANGO_MINIMO + (i * RANGO_POR_HIJO);
            int fin = inicio + RANGO_POR_HIJO - UNO;
            
            if (i == numProcesos - UNO) {
                fin += RESTANTE;
            }

            for (int num = inicio; num <= fin; num++) {
                if (esCapicua(num)) {
                    printf("Hijo: %d: %d \n", i, num);
                }
                
            }
            exit(0);
        } 

    }

    // Esperar a que todos los hijos terminen
    for (int i = 0; i < numProcesos; i++) {
        wait(NULL);
    }

    return 0;
}