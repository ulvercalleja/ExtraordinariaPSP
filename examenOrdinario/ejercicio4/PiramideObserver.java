package examenOrdinario.ejercicio4;

import examenOrdinario.ejercicio4.ServerUDP.Observer;

public class PiramideObserver implements Observer {

    @Override
    public void actualizar(int numero) {
        System.out.println("Generando piramide para el número: " + numero);
        for (int i = 1; i <= numero; i++) {
            for (int j = numero; j > i; j--) {
                System.out.print(" ");
            }
            for (int k = 1; k <= (2 * i - 1); k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
}
