package claseRecuperacion.java.ejercicio4;

public class Inspector extends Thread{
    private final Fabrica fabrica;

    public Inspector(Fabrica fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public void run() {
        while (true) {
            for (LineaEnsamblaje linea : fabrica.getLineas()) {
                try {
                    linea.inspeccionarCoche();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
