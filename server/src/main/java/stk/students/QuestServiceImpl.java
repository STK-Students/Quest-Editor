package stk.students;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class QuestServiceImpl implements QuestService {


    @Override
    public String sendMessage(final String clientMessage) throws RemoteException {
        System.out.println("[Client]: " + clientMessage);
        return "Auch Hallo!";

    }

    @Override
    public List<String> sort(final ArrayList<String> unsorted) throws RemoteException {
        String pos1 = unsorted.get(0);
        String pos2 = unsorted.get(1);
        unsorted.set(0, pos2);
        unsorted.set(1, pos1);
        return unsorted;
    }
}
