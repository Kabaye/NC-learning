package edu.netcracker.small_learning_things.rmi.test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

import static edu.netcracker.small_learning_things.rmi.test.RmiInterface.LOOK_UP_NAME;

/**
 * @author svku0919
 * @version 02.09.2020
 */
public class RmiServer {
    protected RmiServer() {
    }

    public static void main(String[] args) throws NamingException {
//        System.setProperty("java.security.policy","file://C:/Program Files/Java/jdk-14.0.1/lib/security.policy");
        Context context = new InitialContext();
        String hostname = "192.168.1.124"/*"10.236.129.183"*/;
        int port = 1099;
        String bindLocation = "rmi://" + hostname + ":" + port + "/" + LOOK_UP_NAME;
        try {
            context.rebind(bindLocation, new RmiInterfaceImpl());
            System.out.println("Rmi Server is ready at:" + bindLocation);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
