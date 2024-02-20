package commands;

import reply.Reply;
import request.Request;

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


    default Reply execute(Request request) {
        return null;
    }

    default String name() {
        return null;
    }
}