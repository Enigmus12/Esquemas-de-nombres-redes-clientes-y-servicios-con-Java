package escuelaing.edu.co.sockets;

import java.io.*;
import java.net.*;

/**
 *
 * @author Juan David
 */
    /** Simple TCP client that connects to a server, sends numbers or function commands, and displays the server's response. */

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Cliente conectado al servidor.");
        System.out.println("Puedes enviar números para obtener el resultado según el servidor:");
        System.out.println("- Si está conectado al servidor de cuadrados → devuelve el cuadrado.");
        System.out.println("- Si está conectado al servidor trigonométrico → devuelve sin, cos o tan.");
        System.out.println("Para cambiar la función trigonométrica, escribe: fun:sin | fun:cos | fun:tan");
        System.out.println("Escribe un número (CTRL+C para salir):");

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);  // Enviar al servidor
            String response = in.readLine();
            if (response == null) break; // Si el servidor cerró
            System.out.println("Respuesta del servidor: " + response);
        }

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
