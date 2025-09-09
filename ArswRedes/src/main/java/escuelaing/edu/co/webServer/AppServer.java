package escuelaing.edu.co.webServer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

/**
 * Basic web server that handles HTTP GET requests.
 * - Serves static files from the "www" directory.
 * - Provides a simple REST endpoint at /hello.
 */


 /**
 *
 * @author Juan David
 */


public class AppServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Servidor iniciado en http://localhost:35000");

        while (true) { // múltiples solicitudes secuenciales (no concurrentes)
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);

            // Leer primera línea del request (ej: GET /index.html HTTP/1.1)
            String inputLine = in.readLine();
            if (inputLine == null) return;
            System.out.println("Solicitud: " + inputLine);

            String[] requestParts = inputLine.split(" ");
            String path = requestParts[1];

            // Endpoint REST: /hello o /app/hello
            if (path.startsWith("/hello") || path.startsWith("/app/hello")) {
                String name = "world";
                if (path.contains("name=")) {
                    name = URLDecoder.decode(path.split("name=")[1], StandardCharsets.UTF_8);
                }
                String response = "Hello " + name + "!";
                sendResponse(writer, "text/html", response);
            } else {
                // Archivos estáticos en www
                if (path.equals("/")) path = "/index.html";
                File file = new File("www" + path);

                if (file.exists() && !file.isDirectory()) {
                    String mimeType = Files.probeContentType(file.toPath());
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    sendFileResponse(out, writer, mimeType, fileContent);
                } else {
                    // 404
                    File notFound = new File("www/404.html");
                    String mimeType = "text/html";
                    byte[] fileContent = Files.readAllBytes(notFound.toPath());
                    sendFileResponse(out, writer, mimeType, fileContent);
                }
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(PrintWriter writer, String contentType, String response) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: " + contentType + "; charset=utf-8");
        writer.println("Content-Length: " + response.getBytes(StandardCharsets.UTF_8).length);
        writer.println();
        writer.println(response);
    }

    private static void sendFileResponse(OutputStream out, PrintWriter writer, String mimeType, byte[] fileContent) throws IOException {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: " + mimeType);
        writer.println("Content-Length: " + fileContent.length);
        writer.println();
        writer.flush();
        out.write(fileContent);
        out.flush();
    }
}
