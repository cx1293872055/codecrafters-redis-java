package server;

import cache.RedisCache;
import client.Client;
import client.SlaveClient;
import commands.CommandManager;
import config.RedisConfig;
import request.Request;
import utils.Sleeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:04
 */
public abstract class BaseServer implements Server {
    protected static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(10);
    protected final Set<Client> replicas = new HashSet<>();
    protected final Map<Client, Integer> replicasOffSetMap = new HashMap<>();
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
                handleClient(new ClientHandler(this, new SlaveClient(accept)));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Override
    public void handleClient(Runnable runnable) {
        EXECUTOR_SERVICE.submit(runnable);
    }

    @Override
    public void setReplica(Client replica) {
        replicas.add(replica);
        replicasOffSetMap.put(replica, 0);
    }

    @Override
    public Collection<Client> getReplicas() {
        return this.replicas;
    }

    @Override
    public void propagation(Request request) {
        Iterator<Client> iterator = this.replicas.iterator();
        // Sleeper.sleep(30L);

        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getSocket().isClosed()) {
                iterator.remove();
                replicasOffSetMap.remove(client);
            } else {
                client.propagation(request);
                // CompletableFuture.runAsync(() -> client.propagation(request));
            }
        }
    }

    @Override
    public void setReplicaOffSet(Client client, int offSet) {
        // if this is slave
        // update itself offSet
        if (this instanceof Slave) {
            RedisConfig.offSet = offSet;
            return;
        }

        // master: update corresponding replca offset
        Integer currentOffSet = replicasOffSetMap.get(client);
        if (offSet >= currentOffSet) {
            client.receivedPropagatedReply();
        }
        replicasOffSetMap.put(client, offSet);
    }

    static abstract class BaseHandler implements Runnable {

        protected final Server server;
        protected final Client client;

        public BaseHandler(Server server, Client client) {
            this.server = server;
            this.client = client;
        }
    }

    static class MasterHandler extends BaseHandler {

        public MasterHandler(Server server, Client client) {
            super(server, client);
        }

        @Override
        public void run() {

            try (BufferedReader reader = client.getReader()) {
                int ch;
                StringBuilder start = new StringBuilder();
                while ((ch = reader.read()) != -1) {
                    if (ch == '*') {
                        System.out.println(start);
                        start = new StringBuilder();

                        StringBuilder raw = new StringBuilder();
                        int nxtChar = reader.read() - 48;
                        int arrayLength = nxtChar * 2;
                        ArrayList<String> commandArray = new ArrayList<>();
                        raw.append("*").append(nxtChar).append('\r').append('\n');

                        // \r\n
                        reader.read();
                        reader.read();

                        // read commands and args
                        while (arrayLength > 0) {
                            StringBuilder sb = new StringBuilder();
                            while ((ch = reader.read()) != -1) {
                                if (ch == '\r') {
                                    break;
                                }
                                sb.append((char) ch);
                            }
                            reader.read();
                            raw.append(sb).append('\r').append('\n');
                            commandArray.add(sb.toString());
                            arrayLength--;
                        }
                        Request request = Request.commonRequest(raw.toString(), commandArray);
                        request.printRaw("receive master");
                        CommandManager.ofInput(request.commandName())
                                      .doMasterExecute(server, client, request);
                    } else if (ch == '+') {
                        System.out.println(start);
                        start = new StringBuilder();

                        StringBuilder sb = new StringBuilder();
                        sb.append('+');
                        int temp;
                        while ((temp = reader.read()) != -1) {
                            if (temp == '\r') {
                                reader.read();
                                break;
                            }

                            sb.append((char) temp);
                        }

                        System.out.println("receive master reply --------");
                        System.out.println(sb);
                    } else {
                        start.append((char) ch);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ClientHandler extends BaseHandler {

        public ClientHandler(Server server, Client client) {
            super(server, client);
        }

        @Override
        public void run() {
            Sleeper.sleep(30L);
            try (BufferedReader in = client.getReader()) {

                String clientInput;
                while ((clientInput = in.readLine()) != null) {
                    StringBuilder sb = new StringBuilder();
                    if (clientInput.startsWith("*")) {
                        sb.append(clientInput);
                        sb.append("\r\n");
                        int numberOfItems = Integer.parseInt(clientInput.substring(1));
                        List<String> commands = new ArrayList<>(numberOfItems * 2);
                        for (int i = 0; i < numberOfItems * 2; i++) {
                            String line = in.readLine();
                            commands.add(line);
                            sb.append(line);
                            sb.append("\r\n");
                        }
                        Request request = Request.commonRequest(sb.toString(), commands);
                        request.printRaw("receive client");
                        CommandManager.ofInput(request.commandName())
                                      .doClientExecute(server, client, request);
                    } else {
                        System.out.println("receive client reply --------");
                        System.out.println(clientInput);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

