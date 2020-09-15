package edu.netcracker.small_learning_things.rmi.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author svku0919
 * @version 02.09.2020
 */
public interface RmiInterface extends Remote {
    String LOOK_UP_NAME = "RMIInterface";

    String doSomething(String incomingStr) throws RemoteException;
}
