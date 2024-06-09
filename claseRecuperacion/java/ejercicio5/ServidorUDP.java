package claseRecuperacion.java.ejercicio5;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP extends Thread{

    private Floristeria floristeria;
    private static final int PUERTO = 9876;

    public ServidorUDP(Floristeria floristeria) {
        this.floristeria = floristeria;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)){
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket recibirPaquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(recibirPaquete);

                String mensaje = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                int cantidadSolicitada = Integer.parseInt(mensaje);
                String respuesta;

                synchronized (floristeria) {
                    if (floristeria.haySuficientesRamos(cantidadSolicitada)) {
                        floristeria.eliminarRamo();
                        respuesta = Integer.toString(cantidadSolicitada);
                    } else {
                        respuesta = "-1";
                    }
                }

                byte[] bufferRespuesta = respuesta.getBytes();
                InetAddress direccionCliente = recibirPaquete.getAddress();
                int puertoCliente = recibirPaquete.getPort();
                DatagramPacket enviarPaquete = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, direccionCliente, puertoCliente);
                socket.send(enviarPaquete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
