package claseRecuperacion.java.ejercicio4;

public class Fabrica {
    private final LineaEnsamblaje[] lineas;

    public Fabrica(int numeroLineas) {
        lineas = new LineaEnsamblaje[numeroLineas];
        for (int i = 0; i < numeroLineas; i++) {
            lineas[i] = new LineaEnsamblaje(i, this);
        }
    }

    public LineaEnsamblaje[] getLineas() {
        return lineas;
    }

    public void iniciarFabrica() {
        for (LineaEnsamblaje linea : lineas) {
            linea.start();
        }
        new Inspector(this).start();
    }
}
