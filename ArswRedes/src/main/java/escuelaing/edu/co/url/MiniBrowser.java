package escuelaing.edu.co.url;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * MiniBrowser is a simple application that prompts the user for a URL,
 * downloads the HTML content of that URL and saves it to a local file
 * called resultado.html.
 *
 * steps:
 * 1. Prompt the user for a URL.
 * 2. Create a URL object.
 * 3. Open a stream to read from the URL.
 * 4. Create an output file called resultado.html.
 * 5. Read line by line and write to the file.
 * 6. Close streams.
 * 
 * @author Juan David
 */

public class MiniBrowser {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // 1. Pedir la URL al usuario
            System.out.print("Ingrese una URL (ejemplo: http://www.google.com): ");
            String urlString = sc.nextLine();

            // 2. Crear el objeto URL
            URL url = new URL(urlString);

            // 3. Abrir un flujo de lectura desde la URL
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            // 4. Crear un archivo de salida resultado.html
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("resultado.html")
            );

            // 5. Leer línea por línea y escribir en el archivo
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            // 6. Cerrar streams
            reader.close();
            writer.close();

            System.out.println("El contenido fue guardado en resultado.html");
            System.out.println("Ahora ábrelo en tu navegador para ver la página.");

        } catch (MalformedURLException e) {
            System.out.println("La URL ingresada no es válida.");
        } catch (IOException e) {
            System.out.println("Error al leer/escribir datos: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}

