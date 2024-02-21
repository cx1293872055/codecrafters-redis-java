import server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = Server.loadRedis(args);
        server.initial();
        server.start();
    }

}
