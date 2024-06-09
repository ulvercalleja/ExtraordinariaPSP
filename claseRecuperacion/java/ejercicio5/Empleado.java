package claseRecuperacion.java.ejercicio5;

import java.util.Random;

public class Empleado extends Thread{
    private static final long MIN_TIEMPO_DESCANSO = 3000;
    private static final long MAX_TIEMPO_DESCANSO = 4000;

    Floristeria floristeria;
    private Random random = new Random();

    public Empleado(Floristeria floristeria){
        this.floristeria = floristeria;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep((long) (Math.random()* (MAX_TIEMPO_DESCANSO - MIN_TIEMPO_DESCANSO + 1) + MIN_TIEMPO_DESCANSO));
                
                Ramo ramo = new Ramo();
                floristeria.anadirRamo(ramo);
                System.out.println("Ramo generado y agregado al almacenamiento.");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
