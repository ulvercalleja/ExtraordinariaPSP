package claseRecuperacion.java.ejercicio2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClienteSensor {
    private static final String SERVER_ADDRESS = "10.0.2.15";
    private static final int SERVER_PORT = 9876;
    private static int sensor_id; 

    public ClienteSensor (int sensor_id){
        this.sensor_id = sensor_id;
    }
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ipAddress = InetAddress.getByName(SERVER_ADDRESS);

            while (true) {
                // Simulación de la distancia a un obstáculo
                double distancia = Math.random() * 100;

                // Obtener la hora actual
                String horaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                
                String mensaje = "SensorID:" + sensor_id + " Distancia:" + distancia + " Hora:" + horaActual;

                byte[] buffer = mensaje.getBytes(); 
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, ipAddress, SERVER_PORT);
                System.out.println("Sensor enviando datos en el puerto: " + SERVER_PORT + "...");
                socket.send(paquete);
                System.out.println("Mensaje enviado: " + mensaje);

                int espera = 5000 + (int)(Math.random() * 5000); //Simular la espera, para que asi no este enviando constantemente datos
                Thread.sleep(espera);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
