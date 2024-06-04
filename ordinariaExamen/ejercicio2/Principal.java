package ordinariaExamen.ejercicio2;

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
