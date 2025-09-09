package escuelaing.edu.co.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author Juan David
 */

 /** Chat application client */

public class ChatApp {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            // 1. Preguntar el nombre y puerto donde me publico
            System.out.print("Tu nombre: ");
            String name = sc.nextLine();

            System.out.print("Puerto local para publicar: ");
            int myPort = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            ChatImpl chat = new ChatImpl(name);
            Registry myRegistry = LocateRegistry.createRegistry(myPort);
            myRegistry.rebind("ChatService", chat);
            System.out.println("Chat listo en puerto " + myPort);

            // 2. Preguntar IP y puerto del otro participante
            System.out.print("IP del otro usuario: ");
            String ip = sc.nextLine();

            System.out.print("Puerto remoto: ");
            int remotePort = sc.nextInt();
            sc.nextLine();

            Registry remoteRegistry = LocateRegistry.getRegistry(ip, remotePort);
            Chat remoteChat = (Chat) remoteRegistry.lookup("ChatService");

            // 3. Loop para enviar mensajes
            System.out.println("Escribe mensajes, 'exit' para salir:");
            while (true) {
                String msg = sc.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;

                remoteChat.sendMessage(name + ": " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

