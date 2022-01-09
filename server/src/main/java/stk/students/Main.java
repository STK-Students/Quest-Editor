package stk.students;

public class Main {

    /**
     * Entry point of this application.
     * Either no arguments or IP_ADDRESS, PORT and SERVICE_NAME can be passed to override the default values.
     * @param args ip_address, port and service_name
     */
    public static void main(String[] args) {
        if (args.length == 3) {
            new Server(args[0], Integer.parseInt(args[1]), args[2]);
        } else if (args.length == 2) {
            new Server(args[0], Integer.parseInt(args[1]));
        } else if (args.length == 1) {
            new Server(args[0]);
        } else {
            new Server();
        }
    }
}
