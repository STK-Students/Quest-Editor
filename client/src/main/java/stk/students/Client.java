package stk.students;

import lombok.Getter;
import lombok.Setter;
import stk.students.data.User;
import stk.students.interaction.LoginProcess;
import stk.students.service.QuestService;
import stk.students.utils.Color;
import stk.students.utils.ConfigManager;
import stk.students.utils.PrintUtils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_PORT = "1099";
    private static final String DEFAULT_SERVICE_NAME = "Quest_Service";

    @Getter
    public static Client instance;
    @Getter
    private final ConfigManager config = new ConfigManager("messages.yml");
    @Getter
    private QuestService server;
    @Getter
    @Setter
    private User currentUser;

    public Client() {
        instance = this;
        connectToServer(DEFAULT_IP_ADDRESS, DEFAULT_PORT, DEFAULT_SERVICE_NAME);
    }

    public Client(final String ipAdress) {
        instance = this;
        connectToServer(ipAdress, DEFAULT_PORT, DEFAULT_SERVICE_NAME);
    }

    public Client(final String ipAdress, final String port) {
        instance = this;
        connectToServer(ipAdress, port, DEFAULT_SERVICE_NAME);
    }

    public Client(final String ipAddress, final String port, final String serviceName) {
        instance = this;
        connectToServer(ipAddress, port, serviceName);
    }

    private void connectToServer(final String ipAddress, final String port, final String serviceName) {
        String RMI_URL = "rmi://" + ipAddress + ":" + port + "/" + serviceName;
        try {
            server = (QuestService) Naming.lookup(RMI_URL);
            new LoginProcess();
        } catch (RemoteException e) {
            PrintUtils.print("Zu '" + RMI_URL + "' konnte keine Verbindung aufgebaut werden.", Color.RED);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
