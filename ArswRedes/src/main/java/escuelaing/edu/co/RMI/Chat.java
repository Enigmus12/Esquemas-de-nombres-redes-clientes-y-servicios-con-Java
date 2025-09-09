package escuelaing.edu.co.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Juan David
 */

public interface Chat extends Remote {
    void sendMessage(String message) throws RemoteException;
}

