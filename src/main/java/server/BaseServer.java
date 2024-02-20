package server;

import cache.RedisCache;
import commands.CommandManager;
import handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:04
 */
public abstract class BaseServer implements Server {
    protected static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(5);

    protected int port;

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
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
        }
    }
}

