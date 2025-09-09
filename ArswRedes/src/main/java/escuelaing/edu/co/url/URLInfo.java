package escuelaing.edu.co.url;

import java.net.*;

/**
 * Class for getting and displaying the different components of a URL.
 */

 /**
 *
 * @author Juan David
 */

public class URLInfo {
    public static void main(String[] args) {
        try {
            // Creamos un objeto URL
            URL myURL = new URL("https://www.ejemplo.com:8080/ruta/archivo.html?id=10&name=juan#seccion2");

            // Imprimir los diferentes componentes
            System.out.println("Protocolo: " + myURL.getProtocol());
            System.out.println("Autoridad: " + myURL.getAuthority());
            System.out.println("Host: " + myURL.getHost());
            System.out.println("Puerto: " + myURL.getPort());
            System.out.println("Path: " + myURL.getPath());
            System.out.println("Query: " + myURL.getQuery());
            System.out.println("File: " + myURL.getFile());
            System.out.println("Ref: " + myURL.getRef());

        } catch (MalformedURLException e) {
            System.out.println("La URL no es válida: " + e.getMessage());
        }
    }
}

