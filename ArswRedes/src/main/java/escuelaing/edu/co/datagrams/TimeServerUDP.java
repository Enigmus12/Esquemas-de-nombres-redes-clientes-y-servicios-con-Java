package escuelaing.edu.co.datagrams;

import java.net.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Juan David
 */

/**
 * Simple UDP server that responds with the current time when it receives a request.
 */

public class TimeServerUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(45000);
            byte[] buffer = new byte[256];
            System.out.println("Servidor UDP iniciado en el puerto 45000...");

            while (true) {
                // Recibe el datagrama del cliente
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Mensaje recibido: " + received);

                // Genera la hora actual
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                byte[] response = time.getBytes();

                // Responde al cliente con la hora
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                DatagramPacket responsePacket = new DatagramPacket(response, response.length, address, port);
                socket.send(responsePacket);

                System.out.println("Hora enviada: " + time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

