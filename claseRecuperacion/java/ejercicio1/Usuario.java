public class Usuario extends Thread{
    private static final long MIN_TIEMPO_DESCANSO = 5000;
    private static final long MAX_TIEMPO_DESCANSO = 10000;

    int numProducto = 1;
    private CarritoCompras carrito;
    Productos producto;

    public Usuario(CarritoCompras carrito) {
        this.carrito = carrito;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) { //Como dice el enunciado, los productos se añadiran 4 veces
            producto = new Productos("Producto" + numProducto);
            numProducto++;
            carrito.anadirProducto(producto);
            simularEspera();
        }

        for (int i = 0; i < 2; i++) { //Como dice el enunciado, los productos se eliminaran 2 veces
            carrito.eliminarProducto(producto);
            simularEspera();
        }
    }

    public void simularEspera(){
        
        System.out.printf("Persona esta esperando... \n");
        try {
            sleep((long) (Math.random()* (MAX_TIEMPO_DESCANSO - MIN_TIEMPO_DESCANSO + 1) + MIN_TIEMPO_DESCANSO));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         
    }
}
