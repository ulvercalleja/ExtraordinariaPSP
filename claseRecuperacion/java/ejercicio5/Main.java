package claseRecuperacion.java.ejercicio5;

public class Main {
    public static void main(String[] args) {
        Floristeria floristeria = new Floristeria();
        
        // Crear y ejecutar threads de empleados
        for (int i = 0; i < 5; i++) { // Puedes cambiar el número de empleados
            new Thread(new Empleado(floristeria)).start();
        }
        
        // Crear y ejecutar el servidor UDP
        new Thread(new ServidorUDP(floristeria)).start();
    }
}
