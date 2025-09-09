package escuelaing.edu.co.sockets;

import java.io.*;
import java.net.*;

/**
 *
 * @author Juan David
 */

 /** Simple TCP server that calculates the square of received numbers. */

public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor iniciado en el puerto 35000...");
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");
        } catch (IOException e) {
            System.err.println("Error al aceptar conexión.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        );

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            try {
                double number = Double.parseDouble(inputLine); // convierte a número
                double squared = number * number;
                System.out.println("Número recibido: " + number + " → cuadrado: " + squared);
                out.println(squared);
            } catch (NumberFormatException e) {
                out.println("Error: ingrese un número válido.");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}

