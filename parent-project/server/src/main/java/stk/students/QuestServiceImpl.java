package stk.students;

import java.rmi.RemoteException;


public class QuestServiceImpl implements QuestService {


    @Override
    public String sendMessage(final String clientMessage) throws RemoteException {
        return "Hallo vom Server!";
    }
}
