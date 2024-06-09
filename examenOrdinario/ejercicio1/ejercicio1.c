/* La letra de un DNI es un carácter de control de errores, puede ser calculado con
el número de DNI, la operación módulo y el siguiente array.
String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D",
"X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
Por ejemplo 00000046 daría la ‘T’.
Por ejemplo 00000047 daría la ‘R’.
printf("%08d", dni); // Para pintar ceros a la izquierda.
Crea un programa en C que reciba por parámetro una letra de DNI y un número.
El programa escribirá todos los DNIs con esa letra utilizando n procesos. Siendo n
el segundo número pasado por parámetro. Además cuando un DNI sea un número
primo escribirá un asterisco al lado del DNI
El programa principal esperará a sus hijos.
Pista: La mejor forma de distribuir el trabajo es con el módulo y el
número de proceso dentro del bucle.
*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

#define MIN_HIJOS 0
#define NUM_HIJO 0
#define NUM_ERROR_HIJO -1

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

// Función para imprimir un DNI con su letra y verificar si es primo
void imprimirDNI(int dni, char letra) {
    printf("%08d%c", dni, letra);
    if (esPrimo(dni))
        printf("*");
    printf("\n");
}

int main(int argc, char *argv[]) {

    char* letras = "TRWAGMYFPDXBNJZSQVHLCKE";

    if (argc != 3) {
        printf("Uso: %s <letra_DNI> <num_procesos>\n", argv[0]);
        return 1;
    }

    char letra = argv[1][0];
    int numProcesos = atoi(argv[2]);
    
    for (int i = 0; i < numProcesos; i++) {
        pid_t hijo = fork();

        if (hijo == NUM_ERROR_HIJO) { // Comprobar si se ha creado hijo correctamente
            perror("Error al crear el hijo");
            exit(EXIT_FAILURE);
        }

        else if (hijo == NUM_HIJO) { // Proceso hijo
            for (int dni = 0; dni < 100000000; dni++) { /*NOTA: no se como no tener los 0 delante en cuenta, ya que no calcula el resto con el 0 delante*/

                int mod = dni % 23;
                if (letra == letras[mod]) {
                    imprimirDNI(dni, letra); 
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