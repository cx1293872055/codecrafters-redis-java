package client;

import codec.Encoding;
import config.RedisConfig;
import reply.Reply;
import request.Request;

import java.io.*;
import java.net.Socket;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:25
 */
public interface Client extends Closeable {

    default void handshake() {
    }

    default void ping() {
        sendRequest(Reply.multiReply(Reply.length("PING")));
    }

    default void replConf() {

        sendRequest(Reply.multiReply(Reply.length("REPLCONF"),
                                     Reply.length("listening-port"),
                                     Reply.length(Encoding.numToBytes(RedisConfig.port))));

        sendRequest(Reply.multiReply(Reply.length("REPLCONF"),
                                     Reply.length("capa"),
                                     Reply.length("eof"),
                                     Reply.length("capa"),
                                     Reply.length("psync2")));
    }

    default void psync() {
        sendRequest(Reply.multiReply(Reply.length("PSYNC"),
                                     Reply.length("?"),
                                     Reply.length(Encoding.numToBytes(-1))));
    }

    default void getAck() {
        sendRequest(Reply.multiReply(Reply.length("REPLCONF"),
                                     Reply.length("GETACK"),
                                     Reply.length("*")));
    }

    Socket getSocket();

    InputStream getInputStream();

    BufferedReader getReader();

    OutputStream getOutputStream();

    BufferedWriter getWriter();

    void sendRequest(Reply reply);

    void propagation(Request request);

    void receivedPropagatedReply();

    boolean isReceivedPropagatedReply();
}

