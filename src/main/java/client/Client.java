package client;

import reply.Reply;
import request.Request;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
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

    OutputStream getOutputStream();

    void sendRequest(Reply reply);

    void propagation(Request request);
}
