import cache.RedisCache;
import commands.CommandManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        //  Uncomment this block to pass the first stage
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = getPort(args);
        try {
            serverSocket = new ServerSocket(port);

            CommandManager.loadCommand();
            RedisCache.initCache();

            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            while (true) {
                Socket accept = serverSocket.accept();
                EXECUTOR_SERVICE.submit(new ClientHandler(accept));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }

    public static int getPort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("--port".equalsIgnoreCase(args[i])) {
                return Integer.parseInt(args[++i]);
            }
        }
        return 6379;
    }
}
