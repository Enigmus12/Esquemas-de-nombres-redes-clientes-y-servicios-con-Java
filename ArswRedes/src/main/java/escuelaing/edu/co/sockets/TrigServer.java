package escuelaing.edu.co.sockets;

import java.io.*;
import java.net.*;

/**
 *
 * @author Juan David
 */

 /** Simple TCP server that calculates trigonometric functions (sin, cos, tan) of received numbers. */

public class TrigServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor trigonométrico en el puerto 35000...");
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto: 35000.");
            System.exit(1);
        }

        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado.");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        );

        String inputLine;
        String currentFunction = "cos"; // por defecto coseno

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("fun:")) {
                currentFunction = inputLine.substring(4); // obtiene sin, cos o tan
                System.out.println("Función cambiada a: " + currentFunction);
                out.println("Función actual: " + currentFunction);
            } else {
                try {
                    double number = Double.parseDouble(inputLine);
                    double result = 0;

                    switch (currentFunction) {
                        case "sin": result = Math.sin(number); break;
                        case "cos": result = Math.cos(number); break;
                        case "tan": result = Math.tan(number); break;
                        default: result = Math.cos(number); break;
                    }

                    System.out.println("Número recibido: " + number + " → " + currentFunction + " = " + result);
                    out.println(result);

                } catch (NumberFormatException e) {
                    out.println("Error: ingrese un número válido.");
                }
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
