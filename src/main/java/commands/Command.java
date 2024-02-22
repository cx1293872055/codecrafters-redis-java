package commands;

import client.Client;
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
    String OK = "OK"; String ERROR = "ERROR";

    String REPLCONF = "replconf";
    String PSYNC = "psync";

    default Reply execute(Request request) {
        return null;
    }

    default void postExecute(Server server, Client client, Request request) {
        if (setReplica()) {
            server.setReplica(client);
            System.out.println("Connected to slave");
        }
    }

    default void afterExecute(Server server, Client client, Request request) {
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

    default String name() {
        return null;
    }
}
