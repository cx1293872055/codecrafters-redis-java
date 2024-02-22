package client;

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
        ping();
        replConf();
        psync();
    }

    default void ping() {

    }

    default void replConf() {

    }

    default void psync() {

    }

    Socket getSocket();

    InputStream getInputStream();

    BufferedReader getReader();

    OutputStream getOutputStream();

    PrintWriter getWriter();

    void sendRequest(Reply reply);

    void propagation(Request request);
}
