/* Retrete para 5 con control de acceso.
En un baño pueden entrar 5 personas como máximo. Una vez que entre una
persona (Thread), escribirá “Entra thread ”, estará un tiempo aleatorio entre 10
seg y 60 seg esperando y escribirá “Terminé”. Cuando ya hay 5 personas en el
baño si una más quiere entrar se quedará esperando. Cuando una persona sale
avisa por si alguien más está esperando.
Crear las clases necesarias y un programa main que generé 20 threads que quieren
entrar al baño.
Clases:
• Bano: con región crítica, conteo de gente y métodos entrar y salir
• Persona: con acceso a Bano, un número identificativo. En su run hace:
entrar, sleep (simula el usar el baño) y salir
• Principal: Crea 20 personas y comienza sus threads
 */
package examenOrdinario.ejercicio2;

//Funciona pero a veces entran las mismas personas que salen.

public class Principal {
    public static void main(String[] args) {

        Bano bano = new Bano(5);

        for (int i = 1; i <= 20; i++) {
            Persona persona = new Persona(bano, i);
            persona.start();
        }
    }
}
