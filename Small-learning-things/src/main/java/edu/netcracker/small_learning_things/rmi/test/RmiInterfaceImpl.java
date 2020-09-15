package edu.netcracker.small_learning_things.rmi.test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author svku0919
 * @version 02.09.2020
 */
public class RmiInterfaceImpl extends UnicastRemoteObject implements RmiInterface {

    public RmiInterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public String doSomething(String incomingStr) {
        System.out.println("Came str: " + incomingStr);
        return incomingStr.toUpperCase();
    }
}
