package ordinariaExamen.ejercicio2;

public class Bano {

    private int cantGente, capacidadMaxima;

    public Bano(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.cantGente = 0;
    }

    public synchronized void entrar (int idPersona){
        try {
            while (cantGente == capacidadMaxima) {
                wait();
            }

            cantGente++;
            System.out.printf("Entro al baño, soy: %d\n" , idPersona);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void salir (int idPersona){
        cantGente--;
        System.out.printf("He acabado ya!!!! soy: %d\n" , idPersona);

        notify();
    }
}