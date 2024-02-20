package client;

import java.io.Closeable;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:25
 */
public interface Client extends Closeable {

    void ping();

    void replConf();
}
