package server;

import cache.RedisCache;
import client.Client;
import client.SlaveClient;
import commands.Command;
import commands.CommandManager;
import request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:04
 */
public abstract class BaseServer implements Server {
    protected static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(5);
    protected final Set<Client> replicas = new HashSet<>();

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
                EXECUTOR_SERVICE.submit(new ClientHandler(this, accept));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Override
    public void setReplica(Client replica) {
        replicas.add(replica);
    }

    @Override
    public void propagation(Request request) {
        this.replicas.forEach(client -> client.propagation(request));
    }

    static class ClientHandler implements Runnable {

        private final Server server;
        private final Socket clientSocket;
        private final Client client;

        public ClientHandler(Server server, Socket clientSocket) {
            this.server = server;
            this.clientSocket = clientSocket;
            this.client = new SlaveClient(clientSocket);
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String clientInput;
                while ((clientInput = in.readLine()) != null) {
                    if (clientInput.startsWith("*")) {
                        int numberOfItems = Integer.parseInt(clientInput.substring(1));
                        List<String> commands = new ArrayList<>(numberOfItems * 2);
                        for (int i = 0; i < numberOfItems * 2; i++) {
                            commands.add(in.readLine());
                        }
                        Request request = Request.commonRequest(clientInput, commands);
                        Command command = CommandManager.ofInput(request.commandName());
                        command.postExecute(server, client, request);
                        client.sendRequest(command.execute(request));
                        command.afterExecute(server, client, request);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

