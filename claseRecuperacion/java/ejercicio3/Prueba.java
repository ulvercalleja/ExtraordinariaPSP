/* El servidor escucha en el puerto 8765 y espera conexiones de clientes. Cuando un 
cliente se conecta, el servidor lee la solicitud HTTP del cliente, extrae la información
 sobre el recurso solicitado, genera una página HTML correspondiente y la envía de vuelta 
 al cliente. */
 
package claseRecuperacion.java.ejercicio3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Prueba {
    private static final int DEFAULT_PORT = 8765;
    private static final int RESOURCE_POSITION = 1;
    private static final String DIRECCION = "/var/www/html/";

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(DEFAULT_PORT); //Escucha el puerto

        while (true) {
            try {
                Socket connCliente = server.accept(); //Servidor acepta conexiones
                BufferedReader reader = new BufferedReader(new InputStreamReader(connCliente.getInputStream())); //Servidor lee la línea de solicitud HTTP
                String header = reader.readLine();
                System.out.println(header);

                // GET ________ HTTP/1.1
                String info = extraeInformacion(header);
                String html = generaPagina(info);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connCliente.getOutputStream()));
                // Escribir cabecera
                writer.write("HTTP/1.1 200 OK\n");
                // writer.write("Content-Type: application/json; charset=utf-8\n");
                writer.write("\n");
                writer.write(html);
                writer.flush();

                reader.close();
                writer.close();
                connCliente.close();
            } catch (SocketException e) {
                System.out.println("Error garrafal.");
            }

        }
    }

    private static String generaPagina(String info) throws IOException {
        String web = DIRECCION + info;
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(web));

            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fin de lectura.");
        }
        return builder.toString();
    }

    private static String extraeInformacion(String header) { //Divide la línea de solicitud HTTP en partes usando el espacio como delimitador.
        return header.split(" ")[RESOURCE_POSITION]; //Devuelve la parte que corresponde al recurso solicitado (posición 1).

        /* Si el usuario hace GET /index.html HTTP/1.1 
           ["GET", "/index.html", "HTTP/1.1"] - POSICION 1
           por lo tanto guardaria: "/index.html"
        */
    }
}

/*  Metodo 1 Navegador:
    http://localhost:8765/index.html

    Metodo 2 CMD:
    telnet localhost 8765 
    GET /index.html HTTP/1.1
*/