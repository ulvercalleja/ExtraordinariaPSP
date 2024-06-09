package examenOrdinario.ejercicio4;

import examenOrdinario.ejercicio4.ServerUDP.Observer;

public class RomboObserver implements Observer {

    @Override
    public void actualizar(int numero) {
        System.out.println("Generando rombo para numero: " + numero);
        for (int i = 1; i <= numero; i++) {
            for (int j = numero; j > i; j--) {
                System.out.print(" ");
            }
            for (int k = 1; k <= (2 * i - 1); k++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i = numero - 1; i > 0; i--) {
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
