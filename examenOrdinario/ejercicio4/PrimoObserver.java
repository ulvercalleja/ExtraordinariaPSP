package examenOrdinario.ejercicio4;

import examenOrdinario.ejercicio4.ServerUDP.Observer;

public class PrimoObserver implements Observer {

    @Override
    public void actualizar(int numero) {
        if (esPrimo(numero)) {
            System.out.println(numero + " es numero primo.");
        } else {
            System.out.println(numero + " no es un numero primo.");
        }
    }
    
    private boolean esPrimo(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
