import java.util.ArrayList;
import java.util.List;

public class CarritoCompras {

    private final List<Productos> productos;
    private int  capacidadMaxima;

    public CarritoCompras(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.productos = new ArrayList<>();
    }

    public synchronized void anadirProducto (Productos producto){
        try {
            while (productos.size() == capacidadMaxima) {
                wait();
            }

            productos.add(producto);
            System.out.println("Producto agregado: " + producto.getNombre());
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void eliminarProducto (Productos producto){
        try {
            while (productos.isEmpty()) {
                wait();
            }

            productos.remove(producto);
            System.out.println("Producto eliminado: " + producto.getNombre());
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getTamano() {
        return productos.size();
    }
}
