import java.util.Random;

public class Usuario extends Thread{
    private static final long MIN_TIEMPO_DESCANSO = 5000;
    private static final long MAX_TIEMPO_DESCANSO = 10000;

    int numProducto = 1;
    private CarritoCompras carrito;
    private final Random random = new Random();

    Productos producto;

    public Usuario(CarritoCompras carrito) {
        this.carrito = carrito;
    }

    @Override
    public void run() {
        try {
            
            for (int i = 0; i < 2; i++) { //Como dice el enunciado, los productos se eliminaran 2 veces
                int prueba = random.nextInt(100);
                Productos producto = new Productos("Pantalones Vaqueros");
                Productos producto2 = new Productos("Cinturon");
                Productos producto3 = new Productos("AF1");
                Productos producto4 = new Productos("Vapormax");
                Productos producto5 = new Productos("Gorra DSQ2");
                Productos producto6 = new Productos("Riñonera Burberry");
                Productos producto7 = new Productos("Sudadera CK");
                Productos producto8 = new Productos("Carhartt");
                Productos producto9 = new Productos("Camiseta Scuffers");
                Productos producto10 = new Productos("Jersey Lacoste");
                Productos producto11 = new Productos("Polo Ralph Lauren");
                Productos producto12 = new Productos("Vaqueros Rotos");

                if (100 > prueba && prueba > 50) {
                    carrito.anadirProducto(producto);
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.eliminarProducto();
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.anadirProducto(producto2);
                    Thread.sleep(random.nextInt(1000) + 500);
                } else if (prueba < 25 && prueba > 10) {
                    carrito.anadirProducto(producto3);
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.eliminarProducto();
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.anadirProducto(producto4);
                    Thread.sleep(random.nextInt(1000) + 500);
                } else if (prueba < 10 && prueba > 0) {
                    carrito.anadirProducto(producto5);
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.eliminarProducto();
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.anadirProducto(producto6);
                    Thread.sleep(random.nextInt(1000) + 500);
                } else if (prueba < 25 && prueba > 10) {
                    carrito.anadirProducto(producto7);
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.eliminarProducto();
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.anadirProducto(producto8);
                    Thread.sleep(random.nextInt(1000) + 500);
                } else if (prueba < 100 && prueba > 75) {
                    carrito.anadirProducto(producto9);
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.eliminarProducto();
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.anadirProducto(producto10);
                    Thread.sleep(random.nextInt(1000) + 500);
                } else {
                    carrito.anadirProducto(producto11);
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.eliminarProducto();
                    Thread.sleep(random.nextInt(1000) + 500);
                    carrito.anadirProducto(producto12);
                    Thread.sleep(random.nextInt(1000) + 500);
                }

            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
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
