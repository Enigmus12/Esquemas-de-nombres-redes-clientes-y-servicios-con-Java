package escuelaing.edu.co.datagrams;

import java.net.*;
import java.io.*;

/**
 *
 * @author Juan David
 */

/**
 * Simple UDP client that requests the current time from a UDP server every 5 seconds.
 * If the server does not respond within 3 seconds, it shows the last received time.
 */

public class TimeClientUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("127.0.0.1"); // localhost
            byte[] buffer;
            byte[] recvBuffer = new byte[256];
            String lastTime = "Sin datos todavía";

            while (true) {
                try {
                    // Enviar solicitud al servidor
                    String message = "hora?";
                    buffer = message.getBytes();
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 45000);
                    socket.send(request);

                    // Esperar respuesta con timeout de 3 segundos
                    socket.setSoTimeout(3000);
                    DatagramPacket response = new DatagramPacket(recvBuffer, recvBuffer.length);
                    socket.receive(response);

                    // Actualizar hora recibida
                    lastTime = new String(response.getData(), 0, response.getLength());
                } catch (SocketTimeoutException e) {
                    // Si no hay respuesta, se mantiene la última hora
                    System.out.println(" Servidor no responde. Mostrando última hora...");
                }

                // Mostrar hora actual (última recibida)
                System.out.println(" Hora actual: " + lastTime);

                // Esperar 5 segundos antes de preguntar otra vez
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

