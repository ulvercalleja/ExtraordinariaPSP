package claseRecuperacion.java.ejercicio4;

import java.util.Random;

public class LineaEnsamblaje extends Thread{
    private final int id;
    private final Fabrica fabrica;
    private boolean cocheTerminado = false;

    public LineaEnsamblaje(int id, Fabrica fabrica) {
        this.id = id;
        this.fabrica = fabrica;
    }

     @Override
    public void run() {
        Random rand = new Random();

        while (true) {
            try { 
                System.out.println("Linea " + id + ": Comienza ensamblaje de coche");
                Thread.sleep(2000 + rand.nextInt(1000));  // Espera entre 2 y 3 segundos

                synchronized (this) {
                    cocheTerminado = true;
                    System.out.println("Linea " + id + ": Finaliza ensamblado de coche");
                    notify();  // Notifica al inspector que el coche está listo
                }

                synchronized (this) {
                    while (cocheTerminado) {
                        wait();  // Espera hasta que el coche sea inspeccionado
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void inspeccionarCoche() throws InterruptedException {
        while (!cocheTerminado) {
            wait();  // Espera hasta que el coche esté terminado
        }
        System.out.println("Inspector: Inspeccionando coche de la linea " + id);
        cocheTerminado = false;
        notify();  // Notifica a la línea de ensamblaje que el coche ha sido inspeccionado
    }
}
