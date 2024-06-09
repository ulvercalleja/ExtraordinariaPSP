package claseRecuperacion.java.ejercicio5;

import java.util.ArrayList;
import java.util.List;

public class Floristeria {
    private static final int CAPACIDAD_MAXIMA = 30;
    private List<Ramo> ramos = new ArrayList<>();

    public Floristeria() {
        this.ramos = ramos;
    }

    public synchronized void anadirRamo (Ramo ramo){
        try {
            while (ramos.size() == CAPACIDAD_MAXIMA) {
                wait();
            }

            ramos.add(ramo);
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void eliminarRamo (){
        try {
            while (ramos.isEmpty()) {
                wait();
            }

            ramos.remove(ramos.size() - 1);
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int obtenerCantidadRamos() {
        return ramos.size();
    }

    public synchronized boolean haySuficientesRamos(int cantidad) {
        return ramos.size() >= cantidad;
    }

    public synchronized void quitarRamos(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            ramos.remove(ramos.size() - 1);
        }
        notifyAll();
    }
}
