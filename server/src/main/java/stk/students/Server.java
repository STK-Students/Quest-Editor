package stk.students;

import stk.students.service.QuestService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static String DEFAULT_IP = "127.0.0.1";
    public static int DEFAULT_PORT = 1099;
    public static String DEFAULT_SERVICE_NAME = "Quest_Service";

    public Server() {
        host(DEFAULT_IP, DEFAULT_PORT, DEFAULT_SERVICE_NAME);
    }

    public Server(final String ip) {
        host(ip, DEFAULT_PORT, DEFAULT_SERVICE_NAME);
    }

    public Server(final String ip, final int port) {
        host(ip, port, DEFAULT_SERVICE_NAME);
    }

    public Server(final String ip, final int port, final String service) {
        host(ip, port, service);
    }

    public void host(final String ipAddress, final int port, final String serviceName) {
        try {
            QuestService stub = (QuestService) UnicastRemoteObject.exportObject(new QuestServiceImpl(), port);

            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(serviceName, stub);

        } catch (AlreadyBoundException | RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Serving on '" + ipAddress + ":" + port + "/" + serviceName + "'.");
    }
}

