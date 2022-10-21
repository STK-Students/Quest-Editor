package stk.students;

public class Main {

    /**
     * Entry point of this application.
     * Either no arguments or IP_ADDRESS, PORT and SERVICE_NAME can be passed to override the default values.
     * @param args ip_address, port and service_name
     */
    public static void main(String[] args) {
        switch (args.length) {
            case 3 -> new Client(args[0], args[1], args[2]);
            case 2 -> new Client(args[0], args[1]);
            case 1 -> new Client(args[0]);
            default -> new Client();
        }
    }
}
