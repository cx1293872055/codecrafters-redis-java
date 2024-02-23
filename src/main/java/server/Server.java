package server;

import client.Client;
import config.RedisConfig;
import request.Request;

import java.util.Collection;

/**
 * @author chenxin
 * @since 2024/2/19 星期一 18:02
 */
public interface Server {

    static Server loadRedis(String[] args) {
        RedisConfig.loadConfig(args);
        return RedisConfig.isMaster ? new Master() : new Slave();
    }

    void handleClient(Runnable runnable);

    Client getMasterClient();

    void setReplica(Client replica);

    Collection<Client> getReplicas();

    void propagation(Request request);

    void initial();

    void start();
}
