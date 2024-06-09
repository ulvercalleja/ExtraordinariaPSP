package claseRecuperacion.java.ejercicio2;

import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerProcesador {
    private static final int SERVER_PORT = 9876;
    private static final int MEMORIA_BUFFER = 1024;
    private static final String RUTA_ARCHIVO = "log.txt";
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(SERVER_PORT);
            byte[] recibirBuffer = new byte[MEMORIA_BUFFER];
            DatagramPacket recibirPaquete = new DatagramPacket(recibirBuffer, recibirBuffer.length);
            
            System.out.println("Servidor escuchando en el puerto " + SERVER_PORT);

            while (true) {
                socket.receive(recibirPaquete);

                // Extraer el mensaje
                String message = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                System.out.println(message);
                
                FileWriter escritor = new FileWriter(RUTA_ARCHIVO, true);
                escritor.write(message + "\n");
                escritor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
