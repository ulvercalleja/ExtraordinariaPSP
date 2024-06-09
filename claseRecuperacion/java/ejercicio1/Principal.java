/* Programa de Sincronización de Threads para una Tienda de Productos Online:
Desarrolla un sistema en Java para una tienda de productos online donde múltiples usuarios pueden 
agregar o quitar productos de su carrito de compras simultáneamente. Para evitar inconsistencias de 
datos debido a accesos concurrentes, implementa un mecanismo de sincronización de threads.

Crea una clase CarritoCompras que maneje una lista de Productos. Utiliza métodos sincronizados para añadir 
y eliminar productos del carrito. Implementa una simulación donde varios threads representan diferentes usuarios
 que modifican el carrito al mismo tiempo. Asegúrate de que los cambios en el carrito de compras reflejen 
 correctamente la suma y eliminación concurrente de productos sin errores de sincronización.

Ten en cuenta que en esta tienda especial varios usuarios comparten el carrito. El carrito tendrá capacidad para 
10 elementos. Los usuario añaden 4 elementos y eliminan 2, estas operaciones están entremezcladas de forma aleatoria. 
De esta forma el número total de productos que cada usuario aporta es de 2. Cuando un usuario quiere añadir un elemento
 y el carrito está lleno, el usuario se bloquea hasta que se libere un hueco. Entre acción cada usuario espera un tiempo
  aleatorio entre 500 y 1500 milisegundos.

Crea un programa principal con un carrito para 10 elementos y 5 usuarios. NOTA: Al finalizar el programa el carrito 
debe estar lleno. */

public class Principal {
  public static void main(String[] args) {

    CarritoCompras carro = new CarritoCompras(10);

    Thread[] usuarios = new Thread[5];

    for (int i = 0; i < usuarios.length; i++) {
      usuarios[i] = new Thread(new Usuario(carro), "Usuario-" + (i+1));
      usuarios[i].start();
    }

    for (Thread usuario : usuarios) {
      try {
          usuario.join();
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }
    }

    System.out.println("El carrito de compras tiene " + carro.getTamano() + " productos al finalizar el programa.");
  }
}