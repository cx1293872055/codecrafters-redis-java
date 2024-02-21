package client;

import reply.Reply;
import request.Request;

import java.io.Closeable;

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

    void ping();

    void replConf();

    void psync();

    void sendRequest(Reply reply);

    void propagation(Request request);
}
