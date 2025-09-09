package escuelaing.edu.co.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Juan David
 */

 /** Chat application server implementation */

public class ChatImpl extends UnicastRemoteObject implements Chat {
    
    private String name;

    public ChatImpl(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}

