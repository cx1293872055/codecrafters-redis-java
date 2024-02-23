package commands;

import client.Client;
import config.RedisConfig;
import reply.Reply;
import request.Request;
import server.Server;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 15:27
 */
public interface Command {

    String PING = "ping";
    String ECHO = "echo";
    String GET = "get";
    String SET = "set";
    String PX = "px";

    String INFO = "info";
    String OK = "OK";
    String ERROR = "ERROR";

    String REPLCONF = "replconf";
    String listeningPort = "listening-port";
    String getAck = "getack";

    String PSYNC = "psync";
    String wait = "wait";

    default void doMasterExecute(Server server, Client client, Request request) {
        masterPostExecute(server, client, request);
        Reply reply = execute(server, client, request);
        if (this instanceof ReplConf) {
            client.sendRequest(reply);
        }
        masterAfterExecute(server, client, request);
    }

    default void doClientExecute(Server server, Client client, Request request) {
        clientPostExecute(server, client, request);
        client.sendRequest(execute(server, client, request));
        clientAfterExecute(server, client, request);
    }

    default Reply execute(Server server, Client client, Request request) {
        return null;
    }

    default void masterPostExecute(Server server, Client client, Request request) {
        if (offSet()) {
            RedisConfig.increaseOffSet(request);
        }
    }

    default void masterAfterExecute(Server server, Client client, Request request) {
    }

    default void clientPostExecute(Server server, Client client, Request request) {
        if (setReplica()) {
            server.setReplica(client);
            System.out.println("Connected to slave");
        }
    }

    default void clientAfterExecute(Server server, Client client, Request request) {
        if (propagation()) {
            server.propagation(request);
        }
    }

    default boolean setReplica() {
        return false;
    }

    /**
     * ready
     *
     * @return boolean
     */
    default boolean propagation() {
        return false;
    }

    default boolean offSet() {
        return true;
    }

    default String name() {
        return null;
    }
}
