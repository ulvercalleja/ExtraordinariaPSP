/* Escribe un programa en C que acepte un número N como parámetro de línea de comandos. Este número N representará 
la cantidad de procesos hijo que el proceso padre debe crear utilizando fork. Cada proceso hijo debe calcular la 
letra del DNI para un rango específico de números, dividiendo el espacio total (desde 0 hasta 99999999) en N partes iguales.

Especificaciones:

Cada hijo calculará la letra del DNI para su rango asignado de números y escribirá en un archivo único los resultados en el f
ormato "DNI - Letra", uno por línea. Los archivos serán nombrados dni_output_.txt, donde es el número del proceso hijo.
El proceso padre debe esperar a que todos los hijos terminen su ejecución antes de imprimir en la consola "Procesado completado 
para todos los hijos". Detalles del cálculo de la letra del DNI:
La letra del DNI en España se calcula dividiendo el número del DNI por 23 y el residuo determina la letra según una tabla predefinida 
de letras (por ejemplo, TRWAGMYFPDXBNJZSQVHLCKE).
Consideraciones:

Usa wait para asegurar que el padre espere por cada hijo.
Emplea fprintf para escribir en los archivos.
Utiliza sprintf para construir los strings que necesitas escribir en el archivo. */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

#define MIN_HIJOS 0
#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1
#define UNO 1
#define RANGO_MAXIMO 19999999
#define RANGO_MINIMO 19500000

int main(int argc, char *argv[]) {

    char* letras = "TRWAGMYFPDXBNJZSQVHLCKE";
    char cadena[25];
    FILE *file = fopen("dni_output_.txt", "w");

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

            for (int dni = inicio; dni <= fin; dni++) {
                int mod = dni % 23;
                char letra = letras[mod];

                sprintf(cadena, "%d - %c", dni, letra);

                fprintf(file, "%s\n", cadena);
            }
        }
    }

    // Esperar a que todos los hijos terminen
    for (int i = 0; i < numProcesos; i++) {
        wait(NULL);
    }

    printf("Procesado completado para todos los hijos");

    return 0;
}