package examenOrdinario.ejercicio4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerUDP {
    
    private static final int MEMORIA_BUFFER = 1024;
    private int puerto;

    public ServerUDP(int puerto){
        this.puerto = puerto;
    }

    public interface Observer {
        void actualizar(int numero);
    }

     private List<Observer> observadores = new ArrayList<>();

    public void añadirObservador(Observer observador){
        observadores.add(observador);
    }

    public void eliminarObservador(Observer observador){
        observadores.remove(observador);
    }

    public void notificarObservador(int numero){
        for (Observer observador : observadores) {
            observador.actualizar(numero);
        }
    }
    
    public void Receptor() {
        try {
            DatagramSocket socket = new DatagramSocket(puerto);
            byte[] recibirBuffer = new byte[MEMORIA_BUFFER];
            DatagramPacket recibirPaquete = new DatagramPacket(recibirBuffer, recibirBuffer.length);
            
            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                socket.receive(recibirPaquete);

                // Extraer el mensaje
                String message = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                System.out.println(message);
                int cantidadSolicitada = Integer.parseInt(message);
                if (cantidadSolicitada == -1) {
                    System.out.println("Apagando...");
                    return;
                }
                notificarObservador(cantidadSolicitada);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
